package com.riskcare.app.ui.payroll
import com.riskcare.app.AndroidMain

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.riskcare.app.R
import com.riskcare.app.data.api.RetrofitClient
import com.riskcare.app.data.models.*
import com.riskcare.app.utils.Roles
import com.riskcare.app.utils.SessionManager
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.util.*

class ITDeclarationFragment : Fragment() {

    private val api get() = RetrofitClient.instance
    private val fmtMoney = NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply { maximumFractionDigits = 0 }
    private fun money(v: Double) = fmtMoney.format(v)

    private var currentDeclId: Int? = null
    private var currentFY = "2026-27"
    private var currentStatus = "draft"

    private val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    private val fyOptions: List<String> by lazy {
        (2021..currentYear + 5).map { "${it}-${(it + 1).toString().takeLast(2)}" }
    }

    private lateinit var session: SessionManager
    private lateinit var userRole: String
    private val isHROrAccounts get() = userRole in setOf(Roles.HR, Roles.ACCOUNTS, "admin", "super_admin")

    private var pendingUploadSection      = ""
    private var pendingUploadSectionLabel = ""
    private var pendingUploadDocType      = ""

    // PROOF_DEFS matching web frontend exactly
    private val PROOF_DEFS = linkedMapOf(
        "HRA"      to listOf("rent_rcpt" to "Rent Receipt", "rent_agmt" to "Rent Agreement", "landlord_pan_card" to "Landlord PAN Card"),
        "80C"      to listOf("lic_rcpt" to "LIC Premium Receipt", "elss_stmt" to "ELSS Statement", "ppf_stmt" to "PPF Statement", "epf_stmt" to "EPF Statement", "fd_cert" to "FD Certificate", "tuition_rcpt" to "Tuition Fee Receipt", "nsc_cert" to "NSC Certificate", "home_loan_stmt" to "Home Loan Statement"),
        "80CCD"    to listOf("nps_stmt" to "NPS Statement", "nps_tier1" to "NPS Tier-1 Contribution"),
        "80D"      to listOf("health_ins" to "Health Insurance Premium Receipt", "med_bills" to "Medical Bills (Senior Parent)"),
        "HP"       to listOf("home_loan_int" to "Home Loan Interest Certificate", "possession_cert" to "Possession Certificate"),
        "80E"      to listOf("edu_loan_cert" to "Education Loan Certificate"),
        "80G"      to listOf("donation_rcpt" to "Donation Receipt", "80g_cert" to "80G Certificate"),
        "80DD"     to listOf("disability_cert" to "Disability Certificate", "med_bills_dd" to "Medical Bills"),
        "80U"      to listOf("disability_cert_u" to "Disability Certificate (Self)"),
        "80DDB"    to listOf("specialist_cert" to "Specialist Certificate", "med_bills_ddb" to "Medical Bills"),
        "LTA"      to listOf("travel_tickets" to "Travel Tickets", "boarding_pass" to "Boarding Pass / Journey Proof"),
        "PREV_EMP" to listOf("form16_prev" to "Form 16 from Previous Employer", "salary_slips_prev" to "Salary Slips")
    )

    private var uploadedProofs: Map<String, List<ITProofDoc>> = emptyMap()

    private val pickFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri -> uploadProofFile(uri) }
        }
    }

    private var hrDeclarations: List<ITDeclarationSummary> = emptyList()

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View =
        i.inflate(R.layout.fragment_it_declaration, c, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session  = SessionManager(requireContext())
        userRole = session.getRole()

        val spinnerFY = view.findViewById<Spinner>(R.id.spinnerITFY)
        val btnSave   = view.findViewById<Button>(R.id.btnSaveDraft)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmitDecl)
        val tabLayout = view.findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabsITDecl)

        // Dynamic FY spinner
        val defaultFY = run {
            val now = Calendar.getInstance()
            val m   = now.get(Calendar.MONTH) + 1
            val y   = now.get(Calendar.YEAR)
            if (m >= 4) "$y-${(y + 1).toString().takeLast(2)}"
            else "${y - 1}-${y.toString().takeLast(2)}"
        }
        spinnerFY.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, fyOptions)
        spinnerFY.setSelection(fyOptions.indexOf(defaultFY).coerceAtLeast(0))
        currentFY = defaultFY
        spinnerFY.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p: AdapterView<*>?) {}
            override fun onItemSelected(p: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                currentFY = fyOptions[pos]; loadDeclaration()
            }
        }

        tabLayout?.removeAllTabs()
        tabLayout?.addTab(tabLayout.newTab().setText("📋 Declaration"))
        tabLayout?.addTab(tabLayout.newTab().setText("📎 Proofs"))
        tabLayout?.addTab(tabLayout.newTab().setText("📊 Tax Preview"))
        if (isHROrAccounts) tabLayout?.addTab(tabLayout.newTab().setText("🧑‍💼 HR Review"))

        tabLayout?.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showPane(view, "declaration")
                    1 -> { showPane(view, "proof"); loadProofs() }
                    2 -> { showPane(view, "tax"); loadTaxPreview() }
                    3 -> { showPane(view, "hr"); loadHRReview() }
                }
            }
            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}
            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}
        })

        btnSave?.setOnClickListener   { saveDeclaration("save") }
        btnSubmit?.setOnClickListener { saveDeclaration("submit") }

        // Regime toggle — hide/show old-regime sections
        view.findViewById<RadioGroup>(R.id.rgRegime)?.setOnCheckedChangeListener { _, _ ->
            updateRegimeVisibility(view)
        }
        updateRegimeVisibility(view)
        view.findViewById<Button>(R.id.btnRefreshHR)?.setOnClickListener { loadHRReview() }

        view.findViewById<Spinner>(R.id.spinnerHRStatus)?.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,
                listOf("All", "submitted", "approved", "rejected", "draft"))
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p: AdapterView<*>?) {}
                override fun onItemSelected(p: AdapterView<*>?, v: View?, pos: Int, id: Long) { loadHRReview() }
            }
        }

        loadDeclaration()
    }

    // ── Pane switching ────────────────────────────────────────────────────────
    private fun showPane(view: View, pane: String) {
        view.findViewById<View>(R.id.paneDeclaration)?.visibility = if (pane == "declaration") View.VISIBLE else View.GONE
        view.findViewById<View>(R.id.paneProof)?.visibility       = if (pane == "proof") View.VISIBLE else View.GONE
        view.findViewById<View>(R.id.paneTax)?.visibility         = if (pane == "tax") View.VISIBLE else View.GONE
        view.findViewById<View>(R.id.paneHR)?.visibility          = if (pane == "hr") View.VISIBLE else View.GONE
    }

    // ── New vs Old Regime — hide inapplicable sections ───────────────────────
    private fun updateRegimeVisibility(view: View) {
        val isNew = view.findViewById<RadioButton>(R.id.rbNew)?.isChecked == true
        // In New Regime: no deductions allowed (HRA, 80C, 80D, 80CCD, 80E, 80G, 80DD, 80U, 80DDB, LTA all gone)
        // Previous Employment stays visible in both regimes (TDS credit still applies)
        val oldRegimeOnly = listOf(
            R.id.cardHra, R.id.card80C, R.id.card80D, R.id.card80CCD,
            R.id.card80DD, R.id.card80U, R.id.card80DDB, R.id.cardLta
        )
        val vis = if (isNew) View.GONE else View.VISIBLE
        oldRegimeOnly.forEach { id -> view.findViewById<View>(id)?.visibility = vis }

        // Show/hide info banner
        val tvRegimeInfo = view.findViewById<TextView>(R.id.tvRegimeInfo)
        if (isNew) {
            tvRegimeInfo?.visibility = View.VISIBLE
            tvRegimeInfo?.text = "ℹ️ New Tax Regime: Standard deduction of ₹75,000 applies. No HRA/80C/80D deductions allowed."
            tvRegimeInfo?.setBackgroundColor(Color.parseColor("#FEF9C3"))
            tvRegimeInfo?.setTextColor(Color.parseColor("#92400E"))
        } else {
            tvRegimeInfo?.visibility = View.GONE
        }
    }

    // ── Load declaration ──────────────────────────────────────────────────────
    private fun loadDeclaration() {
        lifecycleScope.launch {
            val resp = try { api.getITDeclaration(currentFY) } catch (e: Exception) { null }
            val d = resp?.body()?.data
            if (d != null) {
                currentDeclId  = d.id
                currentStatus  = d.status
                populateForm(d)
                applyLockState(d.status, d.hrComment)
            } else {
                currentDeclId = null; currentStatus = "draft"
                applyLockState("draft", null)
            }
        }
    }

    private fun applyLockState(status: String, hrComment: String?) {
        val v       = view ?: return
        val locked  = status in listOf("submitted", "approved", "verified")
        val notice  = v.findViewById<TextView>(R.id.tvDeclStatus)

        notice?.visibility = View.VISIBLE
        when (status) {
            "submitted" -> {
                notice?.text = "📤 Submitted to HR — you cannot edit until HR sends it back."
                notice?.setBackgroundColor(Color.parseColor("#E3F2FD"))
                notice?.setTextColor(Color.parseColor("#1565C0"))
            }
            "approved", "verified" -> {
                notice?.text = "✅ Declaration approved. No further changes allowed."
                notice?.setBackgroundColor(Color.parseColor("#FFEBEE"))
                notice?.setTextColor(Color.parseColor("#B71C1C"))
            }
            "rejected" -> {
                notice?.text = "❌ Rejected by HR. ${if (!hrComment.isNullOrBlank()) "Reason: $hrComment" else "Please revise and re-submit."}"
                notice?.setBackgroundColor(Color.parseColor("#FFEBEE"))
                notice?.setTextColor(Color.parseColor("#C62828"))
            }
            else -> notice?.visibility = View.GONE
        }

        v.findViewWithTag<View>("declFormScroll")?.let { }
        listOf(R.id.etRent, R.id.etLandlord, R.id.etLandlordPan, R.id.etHraCity,
               R.id.et80cPf, R.id.et80cPpf, R.id.et80cLic, R.id.et80cElss, R.id.et80cNsc,
               R.id.et80cFd, R.id.et80cHomeLoan, R.id.et80cTuition, R.id.et80cOther,
               R.id.et80ccdNps, R.id.et80dSelf, R.id.et80dParents, R.id.et80dSeniorParent,
               R.id.et24bHomeLoan, R.id.etHomeLoanProvider, R.id.et80eEduLoan,
               R.id.et80gDonation, R.id.et80gInstitution, R.id.et80gPan,
               R.id.et80ddAmount, R.id.et80ddDependent, R.id.et80ddRelation,
               R.id.et80uAmount, R.id.et80uPct,
               R.id.et80ddbDisease, R.id.et80ddbPatient, R.id.et80ddbRelation, R.id.et80ddbAmount,
               R.id.etLtaAmount, R.id.etLtaDestination, R.id.etLtaTravelPeriod,
               R.id.etPrevEmployer, R.id.etPrevTan, R.id.etPrevPeriod,
               R.id.etPrevGross, R.id.etPrevTaxable, R.id.etPrevTds, R.id.etPrevPf,
               R.id.etOtherSavingsInt, R.id.etOtherFdInt, R.id.etOtherDividend,
               R.id.etOtherCapitalGains, R.id.etOtherMisc
        ).forEach { id -> v.findViewById<EditText>(id)?.isEnabled = !locked }

        v.findViewById<EditText>(R.id.etHraCity)?.isEnabled = !locked
        v.findViewById<RadioGroup>(R.id.rgRegime)?.let { rg ->
            for (i in 0 until rg.childCount) rg.getChildAt(i).isEnabled = !locked
        }

        v.findViewById<Button>(R.id.btnSaveDraft)?.visibility  = if (locked && status != "rejected") View.GONE else View.VISIBLE
        v.findViewById<Button>(R.id.btnSubmitDecl)?.visibility = if (locked && status != "rejected") View.GONE else View.VISIBLE
    }

    private fun populateForm(d: ITDeclaration) {
        val v = view ?: return
        setNum(v, R.id.etRent,           d.rentPaidMonthly)
        setTxt(v, R.id.etLandlord,       d.landlordName ?: "")
        setTxt(v, R.id.etLandlordPan,    d.landlordPan ?: "")
        // HRA city — free text input
        setTxt(v, R.id.etHraCity, d.hraCityType ?: "")

        setNum(v, R.id.et80cPf,          d.sec80cPf)
        setNum(v, R.id.et80cPpf,         d.sec80cPpf)
        setNum(v, R.id.et80cLic,         d.sec80cLic)
        setNum(v, R.id.et80cElss,        d.sec80cElss)
        setNum(v, R.id.et80cNsc,         d.sec80cNsc)
        setNum(v, R.id.et80cFd,          d.sec80cFd)
        setNum(v, R.id.et80cHomeLoan,    d.sec80cHomeLoan)
        setNum(v, R.id.et80cTuition,     d.sec80cTuition)
        setNum(v, R.id.et80cOther,       d.sec80cOther)
        setNum(v, R.id.et80ccdNps,       d.sec80ccdNps)
        setNum(v, R.id.et80dSelf,        d.sec80dSelf)
        setNum(v, R.id.et80dParents,     d.sec80dParents)
        setNum(v, R.id.et80dSeniorParent,d.sec80dSeniorParent)
        setNum(v, R.id.et24bHomeLoan,    d.sec24bHomeLoan)
        setTxt(v, R.id.etHomeLoanProvider, d.homeLoanProvider ?: "")
        setNum(v, R.id.et80eEduLoan,     d.sec80eEduLoan)
        setNum(v, R.id.et80gDonation,    d.sec80gDonation)
        setTxt(v, R.id.et80gInstitution, d.sec80gInstitution ?: "")
        setTxt(v, R.id.et80gPan,         d.sec80gPan ?: "")
        setNum(v, R.id.et80ddAmount,     d.sec80ddAmount)
        setTxt(v, R.id.et80ddDependent,  d.sec80ddDependent ?: "")
        setTxt(v, R.id.et80ddRelation,   d.sec80ddRelation ?: "")
        setNum(v, R.id.et80uAmount,      d.sec80uAmount)
        setNum(v, R.id.et80uPct,         d.sec80uPct)
        setTxt(v, R.id.et80ddbDisease,   d.sec80ddbDisease ?: "")
        setTxt(v, R.id.et80ddbPatient,   d.sec80ddbPatient ?: "")
        setTxt(v, R.id.et80ddbRelation,  d.sec80ddbRelation ?: "")
        setNum(v, R.id.et80ddbAmount,    d.sec80ddbAmount)
        setNum(v, R.id.etLtaAmount,      d.ltaAmount)
        setTxt(v, R.id.etLtaDestination, d.ltaDestination ?: "")
        setTxt(v, R.id.etLtaTravelPeriod,d.ltaTravelPeriod ?: "")
        setTxt(v, R.id.etPrevEmployer,   d.prevEmployer ?: "")
        setTxt(v, R.id.etPrevTan,        d.prevEmployerTan ?: "")
        setTxt(v, R.id.etPrevPeriod,     d.prevPeriod ?: "")
        setNum(v, R.id.etPrevGross,      d.prevGrossSalary)
        setNum(v, R.id.etPrevTaxable,    d.prevTaxableIncome)
        setNum(v, R.id.etPrevTds,        d.prevTds)
        setNum(v, R.id.etPrevPf,         d.prevPf)

        setNum(v, R.id.etEmployerNps,       d.employerNps)
        setNum(v, R.id.etOtherSavingsInt,   d.otherSavingsInt)
        setNum(v, R.id.etOtherFdInt,        d.otherFdInt)
        setNum(v, R.id.etOtherDividend,     d.otherDividend)
        setNum(v, R.id.etOtherCapitalGains, d.otherCapitalGains)
        setNum(v, R.id.etOtherMisc,         d.otherMisc)

        val rg = v.findViewById<RadioGroup>(R.id.rgRegime)
        if (d.regime == "new") rg?.check(R.id.rbNew) else rg?.check(R.id.rbOld)
        updateSummary()
        view?.let { updateRegimeVisibility(it) }
    }

    private fun setNum(view: View, id: Int, value: Double) {
        view.findViewById<EditText>(id)?.setText(if (value > 0) value.toInt().toString() else "")
    }
    private fun setTxt(view: View, id: Int, value: String) {
        view.findViewById<EditText>(id)?.setText(value)
    }
    private fun getDouble(id: Int) = view?.findViewById<EditText>(id)?.text?.toString()?.toDoubleOrNull() ?: 0.0
    private fun getStr(id: Int)    = view?.findViewById<EditText>(id)?.text?.toString()?.trim() ?: ""

    private fun updateSummary() {
        val c80c = minOf(getDouble(R.id.et80cPf) + getDouble(R.id.et80cPpf) + getDouble(R.id.et80cLic) +
            getDouble(R.id.et80cElss) + getDouble(R.id.et80cNsc) + getDouble(R.id.et80cFd) +
            getDouble(R.id.et80cHomeLoan) + getDouble(R.id.et80cTuition) + getDouble(R.id.et80cOther), 150000.0)
        val other = minOf(getDouble(R.id.et80dSelf), 25000.0) +
            minOf(getDouble(R.id.et80dParents) + getDouble(R.id.et80dSeniorParent), 50000.0) +
            getDouble(R.id.et80eEduLoan) +
            minOf(getDouble(R.id.et24bHomeLoan), 200000.0) +
            getDouble(R.id.et80gDonation) +
            minOf(getDouble(R.id.et80ccdNps), 50000.0) +
            minOf(getDouble(R.id.et80ddAmount), 75000.0) +
            minOf(getDouble(R.id.et80uAmount), 75000.0) +
            minOf(getDouble(R.id.et80ddbAmount), 40000.0) +
            getDouble(R.id.etLtaAmount)
        view?.findViewById<TextView>(R.id.tvTotal80c)?.text          = "80C: ${money(c80c)} / ₹1.5L"
        view?.findViewById<TextView>(R.id.tvTotalDeductions)?.text   = "Total Deductions: ${money(c80c + other)}"
    }

    private fun saveDeclaration(action: String) {
        val v      = view ?: return
        val regime = if (v.findViewById<RadioButton>(R.id.rbNew)?.isChecked == true) "new" else "old"
        val cityType = getStr(R.id.etHraCity)
        val body: Map<String, Any?> = mapOf(
            "financial_year"      to currentFY,
            "regime"              to regime,
            "action"              to action,
            "rent_paid_monthly"   to getDouble(R.id.etRent),
            "landlord_name"       to getStr(R.id.etLandlord),
            "landlord_pan"        to getStr(R.id.etLandlordPan).uppercase(),
            "hra_city_type"       to cityType,
            "sec80c_pf"           to getDouble(R.id.et80cPf),
            "sec80c_ppf"          to getDouble(R.id.et80cPpf),
            "sec80c_lic"          to getDouble(R.id.et80cLic),
            "sec80c_elss"         to getDouble(R.id.et80cElss),
            "sec80c_nsc"          to getDouble(R.id.et80cNsc),
            "sec80c_fd"           to getDouble(R.id.et80cFd),
            "sec80c_home_loan"    to getDouble(R.id.et80cHomeLoan),
            "sec80c_tuition"      to getDouble(R.id.et80cTuition),
            "sec80c_other"        to getDouble(R.id.et80cOther),
            "sec80ccd_nps"        to getDouble(R.id.et80ccdNps),
            "sec80d_self"         to getDouble(R.id.et80dSelf),
            "sec80d_parents"      to getDouble(R.id.et80dParents),
            "sec80d_senior_parent" to getDouble(R.id.et80dSeniorParent),
            "sec24b_home_loan"    to getDouble(R.id.et24bHomeLoan),
            "homeloan_provider"   to getStr(R.id.etHomeLoanProvider),
            "sec80e_edu_loan"     to getDouble(R.id.et80eEduLoan),
            "sec80g_donation"     to getDouble(R.id.et80gDonation),
            "sec80g_institution"  to getStr(R.id.et80gInstitution),
            "sec80g_pan"          to getStr(R.id.et80gPan).uppercase(),
            "sec80dd_amount"      to getDouble(R.id.et80ddAmount),
            "sec80dd_dependent"   to getStr(R.id.et80ddDependent),
            "sec80dd_relation"    to getStr(R.id.et80ddRelation),
            "sec80u_amount"       to getDouble(R.id.et80uAmount),
            "sec80u_pct"          to getDouble(R.id.et80uPct),
            "sec80ddb_disease"    to getStr(R.id.et80ddbDisease),
            "sec80ddb_patient"    to getStr(R.id.et80ddbPatient),
            "sec80ddb_relation"   to getStr(R.id.et80ddbRelation),
            "sec80ddb_amount"     to getDouble(R.id.et80ddbAmount),
            "lta_amount"          to getDouble(R.id.etLtaAmount),
            "lta_destination"     to getStr(R.id.etLtaDestination),
            "lta_travel_period"   to getStr(R.id.etLtaTravelPeriod),
            "prev_employer"       to getStr(R.id.etPrevEmployer),
            "prev_employer_tan"   to getStr(R.id.etPrevTan).uppercase(),
            "prev_period"         to getStr(R.id.etPrevPeriod),
            "prev_gross_salary"    to getDouble(R.id.etPrevGross),
            "prev_taxable_income"  to getDouble(R.id.etPrevTaxable),
            "prev_tds"             to getDouble(R.id.etPrevTds),
            "prev_pf"              to getDouble(R.id.etPrevPf),
            "employer_nps"         to getDouble(R.id.etEmployerNps),
            "other_savings_int"    to getDouble(R.id.etOtherSavingsInt),
            "other_fd_int"         to getDouble(R.id.etOtherFdInt),
            "other_dividend"       to getDouble(R.id.etOtherDividend),
            "other_capital_gains"  to getDouble(R.id.etOtherCapitalGains),
            "other_misc"           to getDouble(R.id.etOtherMisc)
        )
        lifecycleScope.launch {
            val resp = try { api.saveITDeclaration(body) } catch (e: Exception) { null }
            val msg  = resp?.body()?.message ?: "Failed to save"
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            if (resp?.body()?.success == true) {
                currentDeclId = resp.body()?.data?.id
                val newStatus = resp.body()?.data?.status ?: "draft"
                currentStatus = newStatus
                applyLockState(newStatus, null)
            }
        }
    }

    // ── Tax Preview ───────────────────────────────────────────────────────────
    private fun loadTaxPreview() {
        val tvOld   = view?.findViewById<TextView>(R.id.tvOldRegimeTax)   ?: return
        val tvNew   = view?.findViewById<TextView>(R.id.tvNewRegimeTax)   ?: return
        val tvRec   = view?.findViewById<TextView>(R.id.tvRecommended)
        val tvBreak = view?.findViewById<TextView>(R.id.tvTaxBreakdown)
        tvOld.text = "Calculating…"; tvNew.text = "Calculating…"
        lifecycleScope.launch {
            val resp = try { api.getTaxPreview(currentFY) } catch (e: Exception) { null }
            val d = resp?.body()?.data
            if (d == null) { tvOld.text = "N/A"; tvNew.text = "N/A"; return@launch }
            tvOld.text = "${money(d.oldRegime.tax)}\n(${money(d.oldRegime.monthlyTds)}/mo TDS)"
            tvNew.text = "${money(d.newRegime.tax)}\n(${money(d.newRegime.monthlyTds)}/mo TDS)"
            val saving = Math.abs(d.oldRegime.tax - d.newRegime.tax)
            tvRec?.text = "✅ ${d.recommended.uppercase()} REGIME saves you ${money(saving)}"
            tvRec?.setTextColor(Color.parseColor("#B71C1C"))
            tvBreak?.text = buildString {
                appendLine("Annual Gross:          ${money(d.annualGross)}")
                appendLine("Std Deduction:       - ${money(d.stdDeduction)}")
                appendLine("HRA Exemption:       - ${money(d.hraExemption)}")
                appendLine("VI-A Deductions:     - ${money(d.totalViA)}")
                appendLine("─────────────────────────────")
                appendLine("Old Taxable Income:    ${money(d.oldRegime.taxableIncome)}")
                appendLine("Old Tax (+ 4% cess):   ${money(d.oldRegime.tax)}")
                appendLine("─────────────────────────────")
                appendLine("New Taxable Income:    ${money(d.newRegime.taxableIncome)}")
                append(    "New Tax (+ 4% cess):   ${money(d.newRegime.tax)}")
            }
        }
    }

    // ── Proof Upload ──────────────────────────────────────────────────────────
    private fun loadProofs() {
        val declId = currentDeclId ?: run {
            Toast.makeText(context, "Save your declaration first before uploading proofs.", Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch {
            val resp = try { api.getProofsByDeclaration(declId) } catch (e: Exception) { null }
            val list = resp?.body()?.data ?: emptyList()
            uploadedProofs = list.groupBy { it.section }
            renderProofSections()
        }
    }

    private fun renderProofSections() {
        val container = view?.findViewById<LinearLayout>(R.id.llProofContainer) ?: return
        container.removeAllViews()
        val ctx = context ?: return

        for ((sec, defs) in PROOF_DEFS) {
            val secProofs  = uploadedProofs[sec] ?: emptyList()
            val totalDocs  = defs.size
            val uploadedCount = defs.count { def -> secProofs.any { it.docType == def.first } }
            val allDone    = uploadedCount == totalDocs && totalDocs > 0

            // Section card
            val cardContent = LinearLayout(ctx).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(28, 20, 28, 16)
                val bg = if (allDone) "#FFF5F5" else "#FFFFFF"
                setBackgroundColor(Color.parseColor(bg))
            }

            // Section header row
            val headerRow = LinearLayout(ctx).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = android.view.Gravity.CENTER_VERTICAL
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { bottomMargin = 8 }
            }
            val secLabel = when (sec) {
                "HRA"      -> "🏠 HRA — House Rent Allowance"
                "80C"      -> "🔥 Section 80C — Investments"
                "80CCD"    -> "📊 Section 80CCD(1B) — NPS"
                "80D"      -> "🏥 Section 80D — Medical Insurance"
                "HP"       -> "🏦 House Property & Sec 24(b)"
                "80E"      -> "📚 Section 80E — Education Loan"
                "80G"      -> "🤝 Section 80G — Donations"
                "80DD"     -> "♿ Section 80DD — Dependent Disability"
                "80U"      -> "✏️ Section 80U — Self Disability"
                "80DDB"    -> "🩺 Section 80DDB — Medical Treatment"
                "LTA"      -> "✈️ LTA — Leave Travel Allowance"
                "PREV_EMP" -> "🏢 Previous Employment"
                else -> sec
            }
            val tvSecTitle = TextView(ctx).apply {
                text = secLabel
                textSize = 13f
                val color = if (allDone) "#991B1B" else "#B71C1C"
                setTextColor(Color.parseColor(color))
                setTypeface(typeface, android.graphics.Typeface.BOLD)
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }
            headerRow.addView(tvSecTitle)

            // Badge
            if (allDone) {
                headerRow.addView(TextView(ctx).apply {
                    text = "✅ Complete"
                    textSize = 11f
                    setTextColor(Color.parseColor("#991B1B"))
                    setBackgroundColor(Color.parseColor("#FEE2E2"))
                    setPadding(14, 6, 14, 6)
                })
            } else if (uploadedCount > 0) {
                headerRow.addView(TextView(ctx).apply {
                    text = "$uploadedCount/$totalDocs uploaded"
                    textSize = 11f
                    setTextColor(Color.parseColor("#6B7280"))
                    setBackgroundColor(Color.parseColor("#F3F4F6"))
                    setPadding(12, 6, 12, 6)
                })
            }
            cardContent.addView(headerRow)

            // Each doc type row
            for ((docKey, docLabel) in defs) {
                val existingProofs = secProofs.filter { it.docType == docKey }
                addDocTypeRow(cardContent, sec, secLabel, docKey, docLabel, existingProofs)
            }

            val card = androidx.cardview.widget.CardView(ctx).apply {
                radius = 20f; cardElevation = if (allDone) 6f else 3f
                val strokeColor = if (allDone) "#86EFAC" else "#E5E7EB"
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { bottomMargin = 16 }
                addView(cardContent)
            }
            container.addView(card)
        }
    }

    private fun addDocTypeRow(parent: LinearLayout, sec: String, secLabel: String, docKey: String, docLabel: String, proofs: List<ITProofDoc>) {
        val ctx   = context ?: return
        val isDone = proofs.isNotEmpty()

        val row = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 8, 0, 8)
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }

        val topRow = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER_VERTICAL
        }

        val tvLabel = TextView(ctx).apply {
            text = if (isDone) "✅ $docLabel" else "⬜ $docLabel"
            textSize = 12f
            setTextColor(if (isDone) Color.parseColor("#991B1B") else Color.parseColor("#374151"))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val btnUpload = Button(ctx).apply {
            text = if (isDone) "Replace" else "+ Upload"
            textSize = 10f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                if (isDone) Color.parseColor("#6B7280") else Color.parseColor("#B71C1C")
            )
            setPadding(16, 4, 16, 4)
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            setOnClickListener {
                pendingUploadSection      = sec
                pendingUploadSectionLabel = secLabel
                pendingUploadDocType      = docKey
                val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "*/*"
                    putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/pdf", "image/jpeg", "image/png"))
                }
                pickFileLauncher.launch(intent)
            }
        }
        topRow.addView(tvLabel)
        topRow.addView(btnUpload)
        row.addView(topRow)

        // Show uploaded files
        for (proof in proofs) {
            val fileRow = LinearLayout(ctx).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = android.view.Gravity.CENTER_VERTICAL
                setPadding(0, 4, 0, 2)
            }
            val icon = TextView(ctx).apply {
                text = if (proof.mimeType?.contains("pdf") == true) "📄" else "🖼️"
                textSize = 14f
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { marginEnd = 6 }
            }
            val tvName = TextView(ctx).apply {
                text = proof.originalName ?: proof.docType ?: "file"
                textSize = 11f
                setTextColor(Color.parseColor("#374151"))
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }
            val (sBg, sFg) = when (proof.status) {
                "approved" -> "#FEE2E2" to "#991B1B"
                "rejected" -> "#FEE2E2" to "#991B1B"
                else       -> "#FEF9C3" to "#92400E"
            }
            val tvStatus = TextView(ctx).apply {
                text = proof.status.uppercase()
                textSize = 9f
                setTextColor(Color.parseColor(sFg))
                setBackgroundColor(Color.parseColor(sBg))
                setPadding(8, 4, 8, 4)
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { marginStart = 6 }
            }
            val btnView = Button(ctx).apply {
                text = "👁"
                textSize = 11f
                setTextColor(Color.parseColor("#1565C0"))
                backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#DBEAFE"))
                setPadding(12, 2, 12, 2)
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { marginStart = 6 }
                setOnClickListener { openProofDocument(proof) }
            }
            fileRow.addView(icon); fileRow.addView(tvName); fileRow.addView(tvStatus); fileRow.addView(btnView)
            row.addView(fileRow)

            if (!proof.hrComment.isNullOrBlank()) {
                row.addView(TextView(ctx).apply {
                    text = "HR: ${proof.hrComment}"
                    textSize = 10f
                    setTextColor(Color.parseColor("#C62828"))
                })
            }
        }

        // Divider
        row.addView(View(ctx).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1).apply { topMargin = 8 }
            setBackgroundColor(Color.parseColor("#F3F4F6"))
        })
        parent.addView(row)
    }

    private fun openProofDocument(proof: ITProofDoc) {
        val ctx   = context ?: return
        val token = session.getToken() ?: return
        Toast.makeText(ctx, "Opening…", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            try {
                val client = okhttp3.OkHttpClient.Builder()
                    .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                    .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS).build()
                val request = okhttp3.Request.Builder()
                    .url("${AndroidMain.BASE_URL.trimEnd('/')}/it-declaration/proof/${proof.id}")
                    .addHeader("Authorization", "Bearer $token").build()
                val response = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) { client.newCall(request).execute() }
                if (!response.isSuccessful) { Toast.makeText(ctx, "Error ${response.code}", Toast.LENGTH_SHORT).show(); return@launch }
                val bytes = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) { response.body?.bytes() } ?: return@launch
                val safeFileName = (proof.originalName ?: "document").replace(Regex("[^a-zA-Z0-9._-]"), "_")
                val cacheFile = java.io.File(ctx.cacheDir, "proof_${proof.id}_$safeFileName")
                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) { cacheFile.writeBytes(bytes) }
                val contentUri = androidx.core.content.FileProvider.getUriForFile(ctx, "${ctx.packageName}.fileprovider", cacheFile)
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(contentUri, proof.mimeType ?: "application/octet-stream")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                ctx.startActivity(Intent.createChooser(intent, "Open with"))
            } catch (e: Exception) {
                Toast.makeText(ctx, "Could not open: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun uploadProofFile(uri: Uri) {
        val ctx    = context ?: return
        val declId = currentDeclId ?: run { Toast.makeText(ctx, "Save declaration first", Toast.LENGTH_SHORT).show(); return }
        val fileName = getFileName(uri) ?: "document"
        val mimeType = ctx.contentResolver.getType(uri) ?: "application/octet-stream"
        val tempFile = File(ctx.cacheDir, "upload_${System.currentTimeMillis()}_$fileName")
        try { ctx.contentResolver.openInputStream(uri)?.use { input -> FileOutputStream(tempFile).use { out -> input.copyTo(out) } } }
        catch (e: Exception) { Toast.makeText(ctx, "Failed to read file", Toast.LENGTH_SHORT).show(); return }

        Toast.makeText(ctx, "Uploading $fileName…", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            try {
                val filePart   = MultipartBody.Part.createFormData("proof_file", fileName, tempFile.asRequestBody(mimeType.toMediaTypeOrNull()))
                val idBody     = declId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val secBody    = pendingUploadSection.toRequestBody("text/plain".toMediaTypeOrNull())
                val secLblBody = pendingUploadSectionLabel.toRequestBody("text/plain".toMediaTypeOrNull())
                val docBody    = pendingUploadDocType.toRequestBody("text/plain".toMediaTypeOrNull())
                val resp       = api.uploadITProof(filePart, idBody, secBody, secLblBody, docBody)
                Toast.makeText(ctx, resp.body()?.message ?: if (resp.isSuccessful) "Uploaded!" else "Upload failed", Toast.LENGTH_SHORT).show()
                if (resp.isSuccessful) loadProofs()
            } catch (e: Exception) {
                Toast.makeText(ctx, "Upload error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally { tempFile.delete() }
        }
    }

    private fun getFileName(uri: Uri): String? {
        var name: String? = null
        context?.contentResolver?.query(uri, null, null, null, null)?.use {
            if (it.moveToFirst()) { val idx = it.getColumnIndex(OpenableColumns.DISPLAY_NAME); if (idx != -1) name = it.getString(idx) }
        }
        return name ?: uri.lastPathSegment
    }

    // ── HR Review ─────────────────────────────────────────────────────────────
    private fun loadHRReview() {
        if (!isHROrAccounts) return
        val container = view?.findViewById<LinearLayout>(R.id.llHRContainer) ?: return
        container.removeAllViews()
        val statusFilter = view?.findViewById<Spinner>(R.id.spinnerHRStatus)?.selectedItem?.toString()?.takeIf { it != "All" }?.lowercase()
        lifecycleScope.launch {
            val resp = try { api.getAllITDeclarations(currentFY, statusFilter) } catch (e: Exception) { null }
            val list = resp?.body()?.data ?: emptyList()
            hrDeclarations = list
            if (list.isEmpty()) {
                container.addView(TextView(requireContext()).apply {
                    text = "No declarations found"; textSize = 13f
                    setTextColor(Color.parseColor("#9E9E9E"))
                    gravity = android.view.Gravity.CENTER; setPadding(0, 32, 0, 0)
                })
                return@launch
            }
            for (decl in list) addHRDeclCard(container, decl)
        }
    }

    private fun addHRDeclCard(container: LinearLayout, decl: ITDeclarationSummary) {
        val ctx = context ?: return
        val cardLayout = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding(28, 20, 28, 20); setBackgroundColor(Color.WHITE) }

        val headerRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; gravity = android.view.Gravity.CENTER_VERTICAL }
        val tvName = TextView(ctx).apply {
            text = "👤 ${decl.employeeName ?: "Employee #${decl.employeeId}"}"
            textSize = 14f; setTypeface(typeface, android.graphics.Typeface.BOLD)
            setTextColor(Color.parseColor("#212121"))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        val (bgC, fgC) = when (decl.status) {
            "approved"  -> "#FEE2E2" to "#991B1B"
            "submitted" -> "#DBEAFE" to "#1E40AF"
            "rejected"  -> "#FEE2E2" to "#991B1B"
            else        -> "#FEF9C3" to "#92400E"
        }
        val tvStatus = TextView(ctx).apply {
            text = decl.status.uppercase(); textSize = 10f
            setTextColor(Color.parseColor(fgC)); setBackgroundColor(Color.parseColor(bgC)); setPadding(10, 4, 10, 4)
        }
        headerRow.addView(tvName); headerRow.addView(tvStatus)
        cardLayout.addView(headerRow)

        cardLayout.addView(TextView(ctx).apply {
            text = "FY: ${decl.financialYear}  |  Deductions: ${money(decl.totalDeductions ?: 0.0)}  |  ${decl.regime?.uppercase() ?: "-"}"
            textSize = 11f; setTextColor(Color.parseColor("#757575"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { topMargin = 4; bottomMargin = 8 }
        })
        cardLayout.addView(TextView(ctx).apply {
            text = "📎 ${decl.proofCount ?: 0} proofs  |  Submitted: ${decl.submittedAt?.take(10) ?: "—"}"
            textSize = 11f; setTextColor(Color.parseColor("#1565C0"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { bottomMargin = 12 }
        })

        if (decl.status == "submitted") {
            val btnRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; gravity = android.view.Gravity.END }
            val btnReject = Button(ctx).apply {
                text = "❌ Reject"; textSize = 12f
                setTextColor(Color.parseColor("#C62828"))
                backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#FEE2E2"))
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { marginEnd = 10 }
                setOnClickListener { showReviewDialog(decl, "reject") }
            }
            val btnApprove = Button(ctx).apply {
                text = "✅ Approve"; textSize = 12f; setTextColor(Color.WHITE)
                backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#B71C1C"))
                setOnClickListener { showReviewDialog(decl, "approve") }
            }
            btnRow.addView(btnReject); btnRow.addView(btnApprove); cardLayout.addView(btnRow)
        }

        cardLayout.addView(Button(ctx).apply {
            text = "👁 View Declaration"; textSize = 12f
            setTextColor(Color.parseColor("#1565C0"))
            backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#DBEAFE"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { topMargin = 8 }
            setOnClickListener { previewDeclaration(decl) }
        })

        container.addView(androidx.cardview.widget.CardView(ctx).apply {
            radius = 24f; cardElevation = 4f
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { bottomMargin = 16 }
            addView(cardLayout)
        })
    }

    private fun showReviewDialog(decl: ITDeclarationSummary, action: String) {
        val ctx = context ?: return
        val etComment = EditText(ctx).apply { hint = if (action == "reject") "Reason (required)" else "Comment (optional)"; minLines = 2 }
        val dlg = android.app.AlertDialog.Builder(ctx)
            .setTitle(if (action == "approve") "Approve Declaration" else "Reject Declaration")
            .setMessage("${decl.employeeName ?: "ID ${decl.employeeId}"}  |  FY: ${decl.financialYear}")
            .setView(etComment)
            .setPositiveButton(if (action == "approve") "Approve" else "Reject") { _, _ ->
                val comment = etComment.text.toString().trim()
                if (action == "reject" && comment.isBlank()) { Toast.makeText(ctx, "Rejection reason required", Toast.LENGTH_SHORT).show(); return@setPositiveButton }
                submitHRReview(decl.id, action, comment)
            }
            .setNegativeButton("Cancel", null).create()
        dlg.show()
        dlg.getButton(android.app.AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.parseColor(if (action == "approve") "#B71C1C" else "#C62828"))
    }

    private fun submitHRReview(declId: Int, action: String, comment: String) {
        lifecycleScope.launch {
            val resp = try { api.reviewITDeclaration(declId, mapOf("action" to action, "comment" to comment.ifBlank { null })) } catch (e: Exception) { null }
            Toast.makeText(context, resp?.body()?.message ?: if (resp?.isSuccessful == true) "Done!" else "Failed", Toast.LENGTH_SHORT).show()
            if (resp?.isSuccessful == true) loadHRReview()
        }
    }

    private fun previewDeclaration(decl: ITDeclarationSummary) {
        val ctx = context ?: return
        lifecycleScope.launch {
            val dResp  = try { api.getITDeclarationById(decl.id) } catch (e: Exception) { null }
            val pResp  = try { api.getProofsByDeclaration(decl.id) } catch (e: Exception) { null }
            val d      = dResp?.body()?.data ?: run { Toast.makeText(ctx, "Could not load", Toast.LENGTH_SHORT).show(); return@launch }
            val proofs = pResp?.body()?.data ?: d.proofDocuments ?: emptyList()

            val scroll = android.widget.ScrollView(ctx)
            val root   = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding(48, 24, 48, 24) }
            scroll.addView(root)

            fun row(label: String, value: String, color: String = "#212121") {
                root.addView(LinearLayout(ctx).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { bottomMargin = 6 }
                    addView(TextView(ctx).apply { text = label; textSize = 12f; setTextColor(Color.parseColor("#757575")); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f) })
                    addView(TextView(ctx).apply { text = value; textSize = 12f; setTextColor(Color.parseColor(color)); setTypeface(typeface, android.graphics.Typeface.BOLD); gravity = android.view.Gravity.END })
                })
            }
            fun div() = root.addView(View(ctx).apply { layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1).apply { topMargin = 10; bottomMargin = 10 }; setBackgroundColor(Color.parseColor("#E5E7EB")) })
            fun sec(title: String) = root.addView(TextView(ctx).apply { text = title; textSize = 12f; setTextColor(Color.parseColor("#B71C1C")); setTypeface(typeface, android.graphics.Typeface.BOLD); layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { topMargin = 8; bottomMargin = 4 } })

            row("Employee", decl.employeeName ?: "ID ${decl.employeeId}")
            row("FY / Regime", "${d.financialYear}  |  ${d.regime.uppercase()}")
            val (sFg) = when (d.status) { "approved" -> listOf("#991B1B"); "submitted" -> listOf("#1E40AF"); "rejected" -> listOf("#991B1B"); else -> listOf("#92400E") }
            row("Status", d.status.uppercase(), sFg); div()
            sec("🏠 HRA"); row("Rent/Month", money(d.rentPaidMonthly)); if (!d.landlordName.isNullOrBlank()) row("Landlord", d.landlordName); div()
            sec("💰 Section 80C")
            row("EPF", money(d.sec80cPf)); row("PPF", money(d.sec80cPpf)); row("LIC", money(d.sec80cLic)); row("ELSS", money(d.sec80cElss))
            row("NSC", money(d.sec80cNsc)); row("FD", money(d.sec80cFd)); row("Home Loan", money(d.sec80cHomeLoan)); row("Tuition", money(d.sec80cTuition)); row("Other", money(d.sec80cOther)); div()
            sec("📊 Other Deductions")
            row("80CCD NPS", money(d.sec80ccdNps)); row("80D Self", money(d.sec80dSelf)); row("80D Parents", money(d.sec80dParents))
            row("24b Home Loan", money(d.sec24bHomeLoan)); row("80E Edu Loan", money(d.sec80eEduLoan)); row("80G Donation", money(d.sec80gDonation))
            row("80DD Disability", money(d.sec80ddAmount)); row("80U Self Disability", money(d.sec80uAmount))
            if (!d.sec80ddbDisease.isNullOrBlank()) { sec("🩺 80DDB Medical"); row("Disease", d.sec80ddbDisease); row("Patient", d.sec80ddbPatient ?: "—"); row("Amount", money(d.sec80ddbAmount)) }
            if (d.ltaAmount > 0) { sec("✈️ LTA"); row("Amount", money(d.ltaAmount)); row("Destination", d.ltaDestination ?: "—") }
            div(); row("Total 80C", money(d.total80c), "#B71C1C"); row("Total Deductions", money(d.totalDeductions), "#B71C1C")
            if (!d.hrComment.isNullOrBlank()) { div(); root.addView(TextView(ctx).apply { text = "💬 HR: ${d.hrComment}"; textSize = 12f; setTextColor(Color.parseColor("#C62828")) }) }

            div(); sec("📎 Proof Documents (${proofs.size})")
            if (proofs.isEmpty()) root.addView(TextView(ctx).apply { text = "No proofs uploaded"; textSize = 12f; setTextColor(Color.parseColor("#9E9E9E")) })
            else for (proof in proofs) {
                val pRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; gravity = android.view.Gravity.CENTER_VERTICAL; setPadding(12, 10, 12, 10); setBackgroundColor(Color.parseColor("#F9FAFB")); layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { bottomMargin = 6 } }
                pRow.addView(TextView(ctx).apply { text = if (proof.mimeType?.contains("pdf") == true) "📄" else "🖼️"; textSize = 16f; layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { marginEnd = 8 } })
                val infoCol = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f) }
                infoCol.addView(TextView(ctx).apply { text = proof.sectionLabel ?: proof.section; textSize = 11f; setTypeface(typeface, android.graphics.Typeface.BOLD); setTextColor(Color.parseColor("#212121")) })
                infoCol.addView(TextView(ctx).apply { text = proof.originalName ?: "—"; textSize = 10f; setTextColor(Color.parseColor("#6B7280")) })
                pRow.addView(infoCol)
                pRow.addView(Button(ctx).apply {
                    text = "👁"; textSize = 10f; setTextColor(Color.parseColor("#1565C0"))
                    backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#DBEAFE"))
                    setPadding(12, 2, 12, 2); setOnClickListener { openProofDocument(proof) }
                })
                root.addView(pRow)
            }

            android.app.AlertDialog.Builder(ctx).setTitle("IT Declaration Preview").setView(scroll).setPositiveButton("Close", null).show()
        }
    }
}
