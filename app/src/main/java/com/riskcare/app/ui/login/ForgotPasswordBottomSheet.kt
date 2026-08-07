package com.riskcare.app.ui.login

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.riskcare.app.R
import com.riskcare.app.data.api.RetrofitClient
import com.riskcare.app.data.models.*
import com.riskcare.app.utils.toast
import kotlinx.coroutines.launch
import java.util.Calendar

class ForgotPasswordBottomSheet : BottomSheetDialogFragment() {

    private var employeeId: Int = -1
    private var resetToken: String = ""

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View =
        i.inflate(R.layout.bottom_sheet_forgot_password, c, false)

    override fun onViewCreated(view: View, s: Bundle?) {
        // Hide step 2 & 3 initially
        view.findViewById<View>(R.id.stepTwo)?.visibility = View.GONE
        view.findViewById<View>(R.id.stepThree)?.visibility = View.GONE

        val etDob = view.findViewById<android.widget.EditText>(R.id.etForgotDob)
        etDob?.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, year, month, day ->
                etDob.setText(String.format("%04d-%02d-%02d", year, month + 1, day))
            }, cal.get(Calendar.YEAR) - 25, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Step 1 — verify identity
        view.findViewById<android.widget.Button>(R.id.btnStep1)?.setOnClickListener {
            val email = view.findViewById<android.widget.EditText>(R.id.etForgotEmail)?.text.toString().trim()
            val dob   = etDob?.text.toString().trim()
            if (email.isEmpty() || dob.isEmpty()) { toast("Fill all fields"); return@setOnClickListener }
            lifecycleScope.launch {
                try {
                    val res = RetrofitClient.instance.forgotVerify(ForgotVerifyRequest(email, dob))
                    if (res.isSuccessful && res.body()?.success == true) {
                        employeeId = res.body()!!.data!!.employeeId
                        toast("Identity verified ✅")
                        view.findViewById<View>(R.id.stepOne)?.visibility = View.GONE
                        view.findViewById<View>(R.id.stepTwo)?.visibility = View.VISIBLE
                    } else toast(res.body()?.message ?: "Verification failed")
                } catch (_: Exception) { toast("Network error") }
            }
        }

        // Step 2 — verify Employee ID
        view.findViewById<android.widget.Button>(R.id.btnStep2)?.setOnClickListener {
            val empCode = view.findViewById<android.widget.EditText>(R.id.etEmpCodeStep2)?.text.toString().trim()
            if (empCode.isEmpty()) { toast("Enter your Employee ID"); return@setOnClickListener }
            lifecycleScope.launch {
                try {
                    val res = RetrofitClient.instance.forgotVerifyEmployeeId(ForgotVerifyEmployeeIdRequest(employeeId, empCode))
                    if (res.isSuccessful && res.body()?.success == true) {
                        resetToken = res.body()!!.data!!.resetToken
                        view.findViewById<View>(R.id.stepTwo)?.visibility = View.GONE
                        view.findViewById<View>(R.id.stepThree)?.visibility = View.VISIBLE
                    } else toast(res.body()?.message ?: "Employee ID verification failed")
                } catch (_: Exception) { toast("Network error") }
            }
        }

        // Step 3 — reset password
        view.findViewById<android.widget.Button>(R.id.btnStep3)?.setOnClickListener {
            val newPass = view.findViewById<android.widget.EditText>(R.id.etNewPass)?.text.toString()
            val confirm = view.findViewById<android.widget.EditText>(R.id.etConfirmPass)?.text.toString()
            if (newPass.length < 8) { toast("Password must be at least 8 characters"); return@setOnClickListener }
            if (newPass != confirm) { toast("Passwords do not match"); return@setOnClickListener }
            lifecycleScope.launch {
                try {
                    val res = RetrofitClient.instance.forgotReset(ForgotResetRequest(resetToken, newPass))
                    if (res.isSuccessful && res.body()?.success == true) {
                        toast("Password reset successfully! Please login.")
                        dismiss()
                    } else toast(res.body()?.message ?: "Reset failed")
                } catch (_: Exception) { toast("Network error") }
            }
        }
    }
}
