package com.krishihr.app.permission
import com.krishihr.app.AndroidMain

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * PermissionManager — handles the full location permission flow.
 *
 * Android location permission flow (3 steps):
 *   Step 1: ACCESS_FINE_LOCATION + ACCESS_COARSE_LOCATION  (foreground)
 *   Step 2: ACCESS_BACKGROUND_LOCATION                     (Android 10+)
 *           Must be asked SEPARATELY after Step 1 is granted
 *   Step 3: SCHEDULE_EXACT_ALARM                           (Android 12+, optional)
 *           Needed for OD auto-stop precision
 *
 * Google Play requirement: background location must be asked separately with
 * a clear explanation of why it's needed. Bundling it with foreground permission
 * will cause Play Store rejection.
 *
 * Usage:
 *   val permManager = PermissionManager(fragment)
 *   permManager.checkAndRequestAll { granted -> if (granted) startTracking() }
 */
class PermissionManager(private val fragment: Fragment) {

    private val tag = "PermissionManager"
    private var onComplete: ((Boolean) -> Unit)? = null

    // Step 1: Foreground location
    private val foregroundLauncher: ActivityResultLauncher<Array<String>> =
        fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            val granted = results[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    results[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (!granted) {
                Log.w(tag, "Foreground location denied")
                showGoToSettingsDialog("Location permission is required for attendance tracking.")
                onComplete?.invoke(false)
                return@registerForActivityResult
            }

            // Step 2: Request background (Android 10+) — must be separate request
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                requestBackgroundPermission()
            } else {
                onComplete?.invoke(true)
            }
        }

    // Step 2: Background location
    private val backgroundLauncher: ActivityResultLauncher<String> =
        fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (!granted) {
                Log.w(tag, "Background location denied — tracking may stop when app is closed")
                // Don't block the user — foreground tracking still works
                // But warn them that background tracking won't work
                showBackgroundDeniedWarning()
            }
            // Proceed regardless — foreground tracking is still functional
            onComplete?.invoke(true)
        }

    /**
     * Main entry point.
     * Checks all required permissions and requests any that are missing.
     * [callback] is called with true when tracking can start (foreground at minimum).
     */
    fun checkAndRequestAll(callback: (Boolean) -> Unit) {
        this.onComplete = callback

        when {
            hasForegroundPermission() && hasBackgroundPermission() -> {
                Log.d(tag, "All permissions already granted")
                callback(true)
            }
            hasForegroundPermission() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                // Foreground OK, background missing
                requestBackgroundPermission()
            }
            !hasForegroundPermission() -> {
                requestForegroundPermission()
            }
            else -> callback(true)
        }
    }

    /** Step 1: Request foreground location with explanation */
    private fun requestForegroundPermission() {
        val ctx = fragment.requireContext()

        // Check if we should show rationale
        val shouldShowRationale =
            fragment.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)

        if (shouldShowRationale) {
            AlertDialog.Builder(ctx)
                .setTitle("Location Permission Required")
                .setMessage(
                    AndroidMain.PERMISSION_LOCATION_RATIONALE +
                            "• Record your attendance punch-in location\n" +
                            "• Track movement during working hours\n" +
                            "• Verify on-duty (OD) activities\n\n" +
                            "Your location is only recorded during your active work session."
                )
                .setPositiveButton("Allow") { _, _ ->
                    foregroundLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
                .setNegativeButton("Cancel") { _, _ -> onComplete?.invoke(false) }
                .setCancelable(false)
                .show()
        } else {
            foregroundLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    /** Step 2: Request background location with Google Play-compliant explanation */
    @androidx.annotation.RequiresApi(Build.VERSION_CODES.Q)
    private fun requestBackgroundPermission() {
        val ctx = fragment.requireContext()

        AlertDialog.Builder(ctx)
            .setTitle("Enable Background Location")
            .setMessage(
                "To track your location while the app is in the background:\n\n" +
                        "1. Tap 'Open Settings' below\n" +
                        "2. Tap 'Permissions' → 'Location'\n" +
                        "3. Select 'Allow all the time'\n\n" +
                        "This ensures your location is recorded even when you switch apps " +
                        "during working hours. Without this, tracking may stop."
            )
            .setPositiveButton("Open Settings") { _, _ ->
                backgroundLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
            .setNegativeButton("Skip") { _, _ ->
                // Allow punch without background — warn tracking may stop
                onComplete?.invoke(true)
            }
            .setCancelable(false)
            .show()
    }

    private fun showBackgroundDeniedWarning() {
        AlertDialog.Builder(fragment.requireContext())
            .setTitle("Limited Tracking")
            .setMessage(
                "Background location was not granted. Your location will only be tracked " +
                        "while the app is open.\n\n" +
                        AndroidMain.PERMISSION_BACKGROUND_SETTINGS_HINT
            )
            .setPositiveButton("Open Settings") { _, _ -> openAppSettings() }
            .setNegativeButton("Continue Anyway") { _, _ -> }
            .show()
    }

    private fun showGoToSettingsDialog(message: String) {
        AlertDialog.Builder(fragment.requireContext())
            .setTitle("Permission Required")
            .setMessage("$message\n\nPlease enable location permission in Settings.")
            .setPositiveButton("Open Settings") { _, _ -> openAppSettings() }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    private fun openAppSettings() {
        fragment.startActivity(
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${fragment.requireContext().packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }

    // ── Permission state checks ────────────────────────────────────────────────

    fun hasForegroundPermission(): Boolean {
        val ctx = fragment.requireContext()
        return ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }

    fun hasBackgroundPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return true
        return ContextCompat.checkSelfPermission(
            fragment.requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun hasAllPermissions(): Boolean = hasForegroundPermission() && hasBackgroundPermission()

    companion object {
        /** Static check — can be used from Service/Worker without Fragment */
        fun hasForegroundPermission(ctx: Context): Boolean =
            ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
    }
}