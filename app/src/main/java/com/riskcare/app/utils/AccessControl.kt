package com.riskcare.app.utils

import com.riskcare.app.data.api.RetrofitClient
import com.riskcare.app.data.models.EffectivePageAccess

// Mirrors app.js's loadEffectiveAccess()/buildSidebar() pattern on web: one fetch
// of the whole catalog's effective access (role default merged with any
// per-employee override set on the Access Control page), cached for this app
// session. Screens/tiles should check this FIRST for any page-key present in
// the catalog; only fall back to a hardcoded Roles.kt check for the handful of
// Android-only screens with no web equivalent.
object AccessControl {
    private var cache: Map<String, EffectivePageAccess>? = null
    private var loading = false

    suspend fun ensureLoaded() {
        if (cache != null || loading) return
        loading = true
        try {
            val res = RetrofitClient.instance.getMyEffectiveAccessAll()
            val data = res.body()?.data
            if (data != null) cache = data.associateBy { it.pageKey }
        } catch (_: Exception) {
            // Leave cache null — callers fall back to their own hardcoded check.
        } finally {
            loading = false
        }
    }

    fun invalidate() { cache = null }

    /**
     * hasAccess: true if this page's effective access level isn't "none".
     * If the page isn't in the catalog (or the fetch hasn't completed/failed),
     * falls back to [fallback] — the old Roles.kt-based decision — so a screen
     * never definition-of-fails-locked before the fetch resolves.
     */
    fun hasAccess(pageKey: String, fallback: Boolean): Boolean {
        val entry = cache?.get(pageKey) ?: return fallback
        return entry.accessLevel != "none"
    }

    fun levelFor(pageKey: String): String? = cache?.get(pageKey)?.accessLevel

    fun scopeFor(pageKey: String): String? = cache?.get(pageKey)?.dataScope

    // Android screen -> web page_key, so the same Access Control rule applies
    // to both. Only screens with a real web equivalent are listed; anything
    // else keeps using its existing Roles.kt-only check.
    object PageKeys {
        const val DASHBOARD          = "dashboard.html"
        const val ATTENDANCE         = "attendance.html"
        const val LEAVES             = "leaves.html"
        const val CHAT               = "chat.html"
        const val BOARD              = "board.html"
        const val TASKS              = "tasks.html"
        const val MY_WORK            = "my-work.html"
        const val WORK_TRACKER       = "work-tracker.html"
        const val DOCUMENTS          = "documents.html"
        const val FORM16             = "form16.html"
        const val IT_DECLARATION     = "it-declaration.html"
        const val PAYROLL            = "payroll.html"
        const val PAYSLIP            = "payslip.html"
        const val ADVANCE            = "advance.html"
        const val REIMBURSEMENT      = "reimbursement.html"
        const val PROVISION          = "provision.html"
        const val OFFER_LETTER       = "offer-letter.html"
        const val RELIEVING_LETTER   = "relieving-letter.html"
        const val SEPARATION         = "separation.html"
        const val EMPLOYEES          = "employees.html"
        const val ORG_CHART          = "org-chart.html"
        const val GEOFENCE           = "geofence.html"
    }
}
