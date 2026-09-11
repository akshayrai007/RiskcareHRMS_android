package com.riskcare.app.permission
import com.riskcare.app.AndroidMain

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * PermissionManager — foreground location only.
 *
 * Riskcare has no background/continuous location tracking — every employee
 * works from office, and location is used only as a one-time check at
 * punch-in/punch-out to confirm the employee is inside their assigned
 * geofence. No background permission is ever requested.
 *
 * Usage:
 *   val pm = PermissionManager(fragment)
 *   pm.checkAndRequestAll { granted -> proceedWithPunch() }
 */
class PermissionManager(private val fragment: Fragment) {

    private val tag = "PermissionManager"
    private var onComplete: ((Boolean) -> Unit)? = null

    private val foregroundLauncher: ActivityResultLauncher<Array<String>> =
        fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            val granted = results[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    results[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            if (!granted) {
                Log.w(tag, "Foreground location denied")
                showGoToSettingsDialog("Location permission is required to mark attendance.")
            }
            onComplete?.invoke(granted)
        }

    /** Main entry point — requests foreground location only. */
    fun checkAndRequestAll(callback: (Boolean) -> Unit) {
        this.onComplete = callback
        if (hasForegroundPermission()) {
            callback(true)
        } else {
            requestForegroundPermission()
        }
    }

    private fun requestForegroundPermission() {
        val ctx = fragment.requireContext()
        val shouldShowRationale =
            fragment.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)

        if (shouldShowRationale) {
            AlertDialog.Builder(ctx)
                .setTitle("Location Permission Required")
                .setMessage(
                    "${AndroidMain.APP_NAME} needs your location to confirm you're within your " +
                        "assigned location when you punch in/out. It's checked only at that moment — " +
                        "nothing is tracked in the background."
                )
                .setPositiveButton("Allow") { _, _ ->
                    foregroundLauncher.launch(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                    )
                }
                .setNegativeButton("Cancel") { _, _ -> onComplete?.invoke(false) }
                .setCancelable(false)
                .show()
        } else {
            foregroundLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
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

    fun hasForegroundPermission(): Boolean {
        val ctx = fragment.requireContext()
        return ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }

    companion object {
        fun hasForegroundPermission(ctx: Context): Boolean =
            ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
    }
}
