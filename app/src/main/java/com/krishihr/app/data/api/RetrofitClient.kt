package com.krishihr.app.data.api
import com.krishihr.app.AndroidMain

import android.content.Context
import android.content.Intent
import com.krishihr.app.ui.login.LoginActivity
import com.krishihr.app.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking

object RetrofitClient {

    val BASE_URL get() = AndroidMain.BASE_URL // ← edit in Android_main.kt

    private var sessionManager: SessionManager? = null
    private var appContext: Context? = null
    private var _instance: ApiService? = null

    fun init(sm: SessionManager, context: Context? = null) {
        sessionManager = sm
        appContext = context?.applicationContext
        _instance = null // reset so next call rebuilds
    }

    // ── 1. Attach Bearer token + Device-ID to every request ──────────────────
    private val authInterceptor = Interceptor { chain ->
        val token    = sessionManager?.getToken()
        val deviceId = sessionManager?.getDeviceId()
        val builder  = chain.request().newBuilder()
        if (!token.isNullOrEmpty())    builder.addHeader("Authorization", "Bearer $token")
        if (!deviceId.isNullOrEmpty()) builder.addHeader("X-Device-ID", deviceId)
        chain.proceed(builder.build())
    }

    // ── 2. On 401: check if it's a device-mismatch kick-out, else try refresh ─
    private val unauthorizedInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        if (response.code == 401) {
            response.close()

            val refreshToken = sessionManager?.getToken()
            var refreshed = false

            if (!refreshToken.isNullOrEmpty()) {
                try {
                    val refreshClient = OkHttpClient.Builder()
                        .connectTimeout(AndroidMain.TIMEOUT_CONNECT_SEC, TimeUnit.SECONDS)
                        .readTimeout(AndroidMain.TIMEOUT_READ_SEC, TimeUnit.SECONDS)
                        .build()
                    val refreshRetrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(refreshClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(ApiService::class.java)

                    val refreshRes = runBlocking {
                        refreshRetrofit.refreshToken(mapOf("token" to refreshToken))
                    }

                    if (refreshRes.isSuccessful) {
                        val newToken = refreshRes.body()?.data?.token
                        if (!newToken.isNullOrEmpty()) {
                            sessionManager?.updateToken(newToken)
                            val retryRequest = originalRequest.newBuilder()
                                .removeHeader("Authorization")
                                .addHeader("Authorization", "Bearer $newToken")
                                .build()
                            refreshed = true
                            return@Interceptor chain.proceed(retryRequest)
                        }
                    }
                } catch (_: Exception) {
                    // Refresh failed — fall through to logout
                }
            }

            if (!refreshed) {
                // Fix 3: DEVICE_MISMATCH or expired — clear session and redirect to login
                sessionManager?.clearSession()
                appContext?.let { ctx ->
                    val intent = Intent(ctx, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TOP
                        putExtra("session_expired", true)
                    }
                    ctx.startActivity(intent)
                }
            }

            chain.proceed(originalRequest)
        } else {
            response
        }
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    // Lenient Gson — won't crash on unexpected types (e.g. array where string expected)
    private val gson = GsonBuilder()
        .setLenient()
        .serializeNulls()
        .create()

    private fun buildClient() = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)           // attach token + device-id first
        .addInterceptor(unauthorizedInterceptor)   // then check 401 on response
        .addInterceptor(loggingInterceptor)
        .connectTimeout(AndroidMain.TIMEOUT_CONNECT_SEC, TimeUnit.SECONDS)
        .readTimeout(AndroidMain.TIMEOUT_READ_SEC, TimeUnit.SECONDS)         // longer for Render cold-start
        .writeTimeout(AndroidMain.TIMEOUT_WRITE_SEC, TimeUnit.SECONDS)       // longer for image uploads
        .build()

    val instance: ApiService
        get() {
            if (_instance == null) {
                _instance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(buildClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(ApiService::class.java)
            }
            return _instance!!
        }
}