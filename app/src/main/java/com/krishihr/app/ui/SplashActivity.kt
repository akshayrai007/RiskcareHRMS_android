package com.krishihr.app.ui
import com.krishihr.app.AndroidMain

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.krishihr.app.R
import com.krishihr.app.data.api.RetrofitClient
import com.krishihr.app.ui.login.LoginActivity
import com.krishihr.app.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val session = SessionManager(this)
        RetrofitClient.init(session)

        val tvStatus = findViewById<TextView>(R.id.tvStatus)

        lifecycleScope.launch {
            delay(AndroidMain.SPLASH_INITIAL_DELAY_MS) // Brief pause to show splash

            // Ping /health until server responds (handles Render cold start)
            tvStatus?.text = "Connecting..."
            val ready = wakeUpServer(tvStatus)

            if (!ready) {
                tvStatus?.text = "Taking longer than usual..."
                delay(AndroidMain.SPLASH_SLOW_MSG_DELAY_MS)
            }

            // Navigate
            val intent = if (session.isLoggedIn()) {
                tvStatus?.text = "Loading your workspace..."
                delay(AndroidMain.SPLASH_CONNECTED_DELAY_MS)
                Intent(this@SplashActivity, MainActivity::class.java).apply {
                    // Forward the original notification deep-link so MainActivity
                    // can navigate to the correct screen (e.g. ${AndroidMain.DEEP_LINK_SCHEME}://approvals?tab=1)
                    this@SplashActivity.intent?.data?.let { deepLink ->
                        data = deepLink
                        action = Intent.ACTION_VIEW
                    }
                }
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java)
            }

            startActivity(intent)
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private suspend fun wakeUpServer(tvStatus: TextView?): Boolean {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient.Builder()
                .connectTimeout(AndroidMain.HEALTH_PING_TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(AndroidMain.HEALTH_PING_TIMEOUT_SEC, TimeUnit.SECONDS)
                .build()

            val healthUrl = RetrofitClient.BASE_URL.replace("/api/", "") + "/health"
            var attempts  = 0

            // Max 18 attempts × 5s = 90 seconds — enough for Render cold-start
            while (attempts < AndroidMain.HEALTH_PING_MAX_ATTEMPTS) {
                attempts++
                try {
                    val resp = client.newCall(Request.Builder().url(healthUrl).build()).execute()
                    if (resp.isSuccessful) {
                        withContext(Dispatchers.Main) { tvStatus?.text = "✓ Connected" }
                        delay(AndroidMain.SPLASH_CONNECTED_DELAY_MS)
                        return@withContext true
                    }
                } catch (_: Exception) { /* server not ready yet */ }

                withContext(Dispatchers.Main) {
                    tvStatus?.text = when (attempts) {
                        1, 2    -> "Connecting..."
                        3, 4    -> "Waking up server..."
                        5, 6, 7 -> "Server is starting up..."
                        8, 9    -> "Almost there..."
                        else    -> "Still loading, please wait..."
                    }
                }
                delay(AndroidMain.HEALTH_PING_INTERVAL_MS)
            }
            false
        }
    }
}