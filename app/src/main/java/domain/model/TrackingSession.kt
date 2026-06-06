package com.krishihr.app.domain.model
import com.krishihr.app.AndroidMain

/**
 * Represents the full state of the tracking session.
 * Stored in SharedPrefs and used across Service, Worker, and UI.
 */
data class TrackingSession(
    val isTracking: Boolean = false,
    val isOd: Boolean = false,
    val punchInTime: Long = 0L,        // epoch ms
    val odStopTimeHour: Int = 18,      // default 6 PM
    val odStopTimeMinute: Int = 30     // default 6:30 PM
)

/** All keys used in KrishiHR_TrackPrefs SharedPreferences */
object TrackingPrefs {
    val PREFS_NAME get() = AndroidMain.PREFS_TRACK
    const val KEY_IS_TRACKING    = "is_tracking"
    const val KEY_IS_OD          = "is_od"
    const val KEY_PUNCH_IN_TIME  = "punch_in_time"
    const val KEY_OD_STOP_HOUR   = "od_stop_hour"
    const val KEY_OD_STOP_MINUTE = "od_stop_minute"

    // Punch-in cache (used to detect if user is punched in on boot/OD check)
    const val KEY_PUNCH_IN_VALUE = "punch_in"
    const val KEY_PUNCH_OUT_VALUE = "punch_out"
}

/** Tracking stop reasons — for logging and analytics */
enum class StopReason {
    PUNCH_OUT,           // User punched out manually
    OD_TIME_EXCEEDED,    // OD tracking auto-stopped at configured time
    SESSION_EXPIRED,     // Token expired / 401
    MANUAL              // Admin override
}