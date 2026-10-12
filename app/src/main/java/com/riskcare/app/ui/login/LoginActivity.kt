package com.riskcare.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.riskcare.app.BuildConfig
import com.riskcare.app.R
import com.riskcare.app.data.api.RetrofitClient
import com.riskcare.app.data.models.*
import com.riskcare.app.databinding.ActivityLoginBinding
import com.riskcare.app.ui.MainActivity
import com.riskcare.app.utils.SessionManager
import com.riskcare.app.utils.hideKeyboard
import com.riskcare.app.utils.toast
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        RetrofitClient.init(sessionManager, this)

        // Style logo name: Risk[Care]HR — "Care" in cyan #00AEEF matching web
        val logoText = SpannableString("RiskCare-HRMS")
        logoText.setSpan(
            ForegroundColorSpan(android.graphics.Color.parseColor("#00AEEF")),
            4, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE  // "Care" is chars 4–8
        )
        binding.tvLogoName.text = logoText

        // Show "Session expired" message if redirected from token expiry or device mismatch
        if (intent.getBooleanExtra("session_expired", false)) {
            showError("Session expired. Please sign in again.")
        }

        binding.btnLogin.setOnClickListener { attemptLogin() }
        binding.tvForgotPassword.setOnClickListener {
            ForgotPasswordBottomSheet().show(supportFragmentManager, "ForgotPassword")
        }
    }

    private fun attemptLogin() {
        val email    = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        if (email.isEmpty())    { binding.tilEmail.error    = "Email required"; return }
        if (password.isEmpty()) { binding.tilPassword.error = "Password required"; return }
        binding.tilEmail.error    = null
        binding.tilPassword.error = null

        setLoading(true)
        hideKeyboard()

        lifecycleScope.launch {
            try {
                // Fix 3 & 4: Send device_id, app_version, device_name with every login
                val deviceId   = sessionManager.getDeviceId()
                val appVersion = BuildConfig.VERSION_NAME
                val deviceName = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL

                val response = RetrofitClient.instance.login(
                    LoginRequest(
                        email      = email,
                        password   = password,
                        deviceId   = deviceId,
                        appVersion = appVersion,
                        deviceName = deviceName
                    )
                )
                if (response.isSuccessful && response.body()?.success == true) {
                    val data = response.body()!!.data!!
                    sessionManager.saveSession(data.token, data.employee)
                    try {
                        val fcmToken = com.google.firebase.messaging.FirebaseMessaging.getInstance().token.await()
                        RetrofitClient.instance.updateFcmToken(mapOf("fcm_token" to fcmToken))
                    } catch (_: Exception) { /* push notifications are best-effort */ }
                    if (data.requirePasswordReset) {
                        showForceResetDialog(data.token)
                    } else {
                        goToMain()
                    }
                } else {
                    showError(response.body()?.message ?: "Invalid credentials")
                }
            } catch (e: Exception) {
                showError("Network error. Please check your connection.")
            } finally {
                setLoading(false)
            }
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun showForceResetDialog(token: String) {
        val view = layoutInflater.inflate(android.R.layout.simple_list_item_2, null)
        // Build dialog programmatically — no custom layout needed
        val dialogView = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(64, 48, 64, 24)
        }
        val title = TextView(this).apply {
            text = "Set Your Password"
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(0, 0, 0, 8)
        }
        val subtitle = TextView(this).apply {
            text = "First login detected — please set a new password to continue."
            textSize = 13f
            setPadding(0, 0, 0, 24)
        }
        val etNew = EditText(this).apply {
            hint = "New Password (min 6 chars)"
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            setPadding(0, 16, 0, 16)
        }
        val etConfirm = EditText(this).apply {
            hint = "Confirm Password"
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            setPadding(0, 16, 0, 16)
        }
        val tvErr = TextView(this).apply {
            setTextColor(android.graphics.Color.RED)
            textSize = 12f
            visibility = View.GONE
        }
        dialogView.addView(title)
        dialogView.addView(subtitle)
        dialogView.addView(etNew)
        dialogView.addView(etConfirm)
        dialogView.addView(tvErr)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("Set Password", null)
            .create()

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val np = etNew.text.toString()
            val cp = etConfirm.text.toString()
            if (np.length < 6) { tvErr.text = "Password must be at least 6 characters"; tvErr.visibility = View.VISIBLE; return@setOnClickListener }
            if (np != cp)      { tvErr.text = "Passwords do not match"; tvErr.visibility = View.VISIBLE; return@setOnClickListener }
            tvErr.visibility = View.GONE
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).text = "Saving…"
            lifecycleScope.launch {
                try {
                    val resp = RetrofitClient.instance.firstLoginReset(mapOf("new_password" to np))
                    if (resp.isSuccessful && resp.body()?.success == true) {
                        dialog.dismiss()
                        toast("Password updated successfully!")
                        goToMain()
                    } else {
                        tvErr.text = resp.body()?.message ?: "Failed to update password"
                        tvErr.visibility = View.VISIBLE
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).text = "Set Password"
                    }
                } catch (e: Exception) {
                    tvErr.text = "Network error. Please try again."
                    tvErr.visibility = View.VISIBLE
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).text = "Set Password"
                }
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.btnLogin.isEnabled = !loading
        binding.btnLogin.text      = if (loading) "Signing in…" else "Login"
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.tvError.visibility = View.GONE
    }

    private fun showError(msg: String) {
        binding.tvError.text = msg
        binding.tvError.visibility = View.VISIBLE
    }
}