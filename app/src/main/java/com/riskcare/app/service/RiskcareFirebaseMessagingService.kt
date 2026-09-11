package com.riskcare.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.riskcare.app.R
import com.riskcare.app.data.api.RetrofitClient
import com.riskcare.app.ui.MainActivity
import com.riskcare.app.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * RiskcareFirebaseMessagingService
 *
 * Handles two FCM events:
 *  1. onNewToken        — device gets a new FCM token -> save to backend
 *  2. onMessageReceived — push arrives -> show a system notification
 */
class RiskcareFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "RiskcareFCM"
        const val CHANNEL_GENERAL = "riskcare_general"
        const val CHANNEL_ALERTS  = "riskcare_alerts"
        const val CHANNEL_CHAT    = "riskcare_chat"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "FCM token refreshed")
        val session = SessionManager(applicationContext)
        if (!session.isLoggedIn()) return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                RetrofitClient.init(session, applicationContext)
                RetrofitClient.instance.updateFcmToken(mapOf("fcm_token" to token))
                Log.d(TAG, "FCM token sent to backend")
            } catch (e: Exception) {
                Log.w(TAG, "Failed to send FCM token: ${e.message}")
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title = message.notification?.title ?: message.data["title"] ?: "Riskcare HR"
        val body  = message.notification?.body  ?: message.data["body"]  ?: return
        val channel = message.data["channel"] ?: CHANNEL_GENERAL
        val screen  = message.data["screen"]
        Log.d(TAG, "Push received — title=$title channel=$channel screen=$screen")
        ensureChannelsExist()
        showNotification(title, body, channel, screen)
    }

    private fun showNotification(title: String, body: String, channelId: String, screen: String?) {
        val manager = getSystemService(NotificationManager::class.java)
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            screen?.let { putExtra("navigate_to", it) }
        }
        val pendingIntent = PendingIntent.getActivity(
            this, System.currentTimeMillis().toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val priority = if (channelId == CHANNEL_ALERTS) NotificationCompat.PRIORITY_HIGH else NotificationCompat.PRIORITY_DEFAULT
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(priority)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        manager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun ensureChannelsExist() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = getSystemService(NotificationManager::class.java)
        if (manager.getNotificationChannel(CHANNEL_GENERAL) == null) {
            manager.createNotificationChannel(
                NotificationChannel(CHANNEL_GENERAL, "General", NotificationManager.IMPORTANCE_DEFAULT)
                    .apply { description = "Attendance, leaves, announcements" }
            )
        }
        if (manager.getNotificationChannel(CHANNEL_ALERTS) == null) {
            manager.createNotificationChannel(
                NotificationChannel(CHANNEL_ALERTS, "Alerts", NotificationManager.IMPORTANCE_HIGH)
                    .apply { description = "Urgent HR alerts" }
            )
        }
        if (manager.getNotificationChannel(CHANNEL_CHAT) == null) {
            manager.createNotificationChannel(
                NotificationChannel(CHANNEL_CHAT, "Chat", NotificationManager.IMPORTANCE_DEFAULT)
                    .apply { description = "Chat messages" }
            )
        }
    }
}
