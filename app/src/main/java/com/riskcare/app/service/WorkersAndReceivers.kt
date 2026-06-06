package com.riskcare.app.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.*
import com.riskcare.app.data.api.RetrofitClient
import com.riskcare.app.data.repository.LocationRepository
import com.riskcare.app.domain.model.StopReason
import com.riskcare.app.domain.usecase.TrackingManager
import com.riskcare.app.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

// ── LocationSyncWorker — Layer 3 safety net ────────────────────────────────────

/**
 * Runs every 15 minutes via WorkManager.
 * Uploads any location records stuck in the local offline queue.
 * Handles the case where the device had no internet during location pings.
 */
class LocationSyncWorker(
    private val ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    companion object {
        private const val TAG      = "LocationSyncWorker"
        private const val WORK_TAG = "location_sync"

        fun schedule(ctx: Context) {
            val request = PeriodicWorkRequestBuilder<LocationSyncWorker>(15, TimeUnit.MINUTES)
                .addTag(WORK_TAG)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED) // only sync when online
                        .build()
                )
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 5, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(ctx).enqueueUniquePeriodicWork(
                WORK_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
            Log.d(TAG, "Sync worker scheduled")
        }

        fun cancel(ctx: Context) {
            WorkManager.getInstance(ctx).cancelAllWorkByTag(WORK_TAG)
        }
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val session = SessionManager(ctx)
        if (!session.isLoggedIn()) return@withContext Result.success()

        RetrofitClient.init(session, ctx)

        val repository = LocationRepository(ctx)
        val pending = repository.getPendingCount()

        if (pending == 0) {
            Log.d(TAG, "No pending records — nothing to sync")
            return@withContext Result.success()
        }

        Log.d(TAG, "Syncing $pending pending location records")
        val result = repository.syncPending()
        Log.d(TAG, "Sync: uploaded=${result.uploaded}, failed=${result.failed}")

        return@withContext if (result.failed > 0) Result.retry() else Result.success()
    }
}

// ── OdStopReceiver — fires when OD stop time alarm triggers ───────────────────

/**
 * Receives the AlarmManager broadcast scheduled by LocationTrackingService
 * when OD tracking should stop (default 6:30 PM).
 */
class OdStopReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context, intent: Intent) {
        Log.d("OdStopReceiver", "OD auto-stop alarm fired")
        val manager = TrackingManager(ctx)
        if (manager.isTracking() && manager.isOd()) {
            LocationTrackingService.stop(ctx, StopReason.OD_TIME_EXCEEDED)
            Log.d("OdStopReceiver", "✅ OD tracking stopped automatically")
        }
    }
}

// ── BootReceiver — resume tracking after device restart ───────────────────────

/**
 * If the device restarts while the employee was punched in,
 * this resumes all tracking layers automatically.
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context, intent: Intent) {
        // Background tracking disabled — location only requested on punch-in.
        // BootReceiver kept as no-op to avoid build errors.
        Log.d("BootReceiver", "Boot received — background tracking disabled")
    }
}