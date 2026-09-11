package com.riskcare.app.ui.more

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.riskcare.app.data.api.RetrofitClient
import com.riskcare.app.data.models.*
import com.riskcare.app.utils.SessionManager
import com.riskcare.app.utils.toast
import com.riskcare.app.utils.toDisplayDate
import kotlinx.coroutines.launch

// ═══════════════════════════════════════════════════════════════════════════════
// ASSET ALLOCATION — programmatic UI (no layout file), ported from
// KrishiHR-Android. Tracks company assets (laptop, SIM, ID card, etc.) issued
// to employees.
//  • HR/Accounts/Admin/Super Admin: pick an employee, add an item (spinner +
//    Qty/Serial/Remark), allocate, and view/return/delete.
//  • Everyone else: read-only list of their own allocated assets.
// ═══════════════════════════════════════════════════════════════════════════════
class AssetAllocationFragment : Fragment() {

    private val manageRoles = setOf("hr", "accounts", "admin", "super_admin")
    private var isManager = false

    private var employees: List<AssetEmployeeItem> = emptyList()
    private var itemNames: List<String> = emptyList()
    private var selectedEmpId = 0
    private var selectedEmpLabel = ""

    private lateinit var listContainer: LinearLayout
    private lateinit var listTitle: TextView
    private var empInput: AutoCompleteTextView? = null
    private var itemSpinner: Spinner? = null
    private var otherInput: EditText? = null
    private var qtyInput: EditText? = null
    private var serialInput: EditText? = null
    private var remarkInput: EditText? = null
    private val staging = mutableListOf<AllocateAssetItem>()
    private var stageContainer: LinearLayout? = null

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun boxBg() = android.graphics.drawable.GradientDrawable().apply {
        cornerRadius = dp(10).toFloat(); setColor(Color.WHITE); setStroke(dp(1), Color.parseColor("#C7D9B4"))
    }
    private fun <T : View> box(v: T): T = v.apply { background = boxBg(); setPadding(dp(12), dp(12), dp(12), dp(12)) }
    private fun mp() = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply { topMargin = dp(4) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext()
        val emp = SessionManager(ctx).getEmployee()
        val role = emp?.role?.lowercase()?.trim() ?: ""
        isManager = manageRoles.contains(role)

        val scroll = ScrollView(ctx)
        val root = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(16), dp(16), dp(24))
        }
        scroll.addView(root)

        root.addView(TextView(ctx).apply {
            text = "💻  Asset Allocation"
            textSize = 21f; setTypeface(typeface, Typeface.BOLD)
            setTextColor(Color.parseColor("#1B5E20"))
            setPadding(0, 0, 0, dp(12))
        })

        if (isManager) buildAllocateUi(ctx, root)

        listTitle = TextView(ctx).apply {
            text = if (isManager) "Assets with the selected employee" else "My allocated assets"
            textSize = 15f; setTypeface(typeface, Typeface.BOLD)
            setPadding(0, dp(14), 0, dp(6))
        }
        root.addView(listTitle)

        listContainer = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL }
        root.addView(listContainer)

        return scroll
    }

    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)
        if (isManager) { loadItems(); loadEmployees() }
        else { loadMyAssets() }
    }

    // ── Allocate form (managers) ──────────────────────────────────────────────
    private fun buildAllocateUi(ctx: android.content.Context, root: LinearLayout) {
        root.addView(label(ctx, "Employee"))
        empInput = AutoCompleteTextView(ctx).apply {
            hint = "Type name or employee code…"; setSingleLine()
            setOnItemClickListener { parent, _, position, _ ->
                val txt = parent.getItemAtPosition(position) as String
                employees.firstOrNull { it.label == txt }?.let { selectedEmpId = it.id; selectedEmpLabel = it.label; loadEmployeeAssets() }
            }
        }
        root.addView(box(empInput!!), mp())

        root.addView(label(ctx, "Item"))
        itemSpinner = Spinner(ctx)
        val spinnerBox = LinearLayout(ctx).apply {
            background = boxBg(); setPadding(dp(6), dp(2), dp(6), dp(2))
            addView(itemSpinner, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }
        root.addView(spinnerBox, mp())
        otherInput = EditText(ctx).apply { hint = "Custom item name"; setSingleLine(); visibility = View.GONE }
        root.addView(box(otherInput!!), mp())
        itemSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                otherInput?.visibility = if ((p?.getItemAtPosition(pos) as? String) == "Other…") View.VISIBLE else View.GONE
            }
            override fun onNothingSelected(p: AdapterView<*>?) {}
        }

        root.addView(label(ctx, "Count & Serial no."))
        val rowQ = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL }
        qtyInput = EditText(ctx).apply { hint = "Qty"; setText("1"); inputType = InputType.TYPE_CLASS_NUMBER; gravity = Gravity.CENTER }
        serialInput = EditText(ctx).apply { hint = "Serial / tag (optional)"; setSingleLine() }
        rowQ.addView(box(qtyInput!!), LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f).apply { marginEnd = dp(8) })
        rowQ.addView(box(serialInput!!), LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2.2f))
        root.addView(rowQ, mp())

        root.addView(label(ctx, "Remark"))
        remarkInput = EditText(ctx).apply { hint = "Remark (optional)"; setSingleLine() }
        root.addView(box(remarkInput!!), mp())

        root.addView(Button(ctx).apply {
            text = "＋  Add item"; setTextColor(Color.parseColor("#1B5E20")); textSize = 13f
            setTypeface(typeface, Typeface.BOLD)
            backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#E7F2DC"))
            setOnClickListener { addToStage() }
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply { topMargin = dp(12) }
        })

        stageContainer = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL }
        root.addView(stageContainer)

        root.addView(Button(ctx).apply {
            text = "ALLOCATE"; setTextColor(Color.WHITE); textSize = 14f
            setTypeface(typeface, Typeface.BOLD)
            backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#2E7D32"))
            setOnClickListener { allocate() }
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply { topMargin = dp(8) }
        })
    }

    private fun addToStage() {
        var name = itemSpinner?.selectedItem as? String ?: ""
        if (name == "Other…") name = otherInput?.text?.toString()?.trim() ?: ""
        if (name.isBlank()) { requireContext().toast("Pick or type an item"); return }
        val q = qtyInput?.text?.toString()?.toIntOrNull()?.coerceAtLeast(1) ?: 1
        staging.add(AllocateAssetItem(
            itemName = name, quantity = q,
            serialNo = serialInput?.text?.toString()?.trim().takeUnless { it.isNullOrBlank() },
            remark = remarkInput?.text?.toString()?.trim().takeUnless { it.isNullOrBlank() }
        ))
        serialInput?.setText(""); remarkInput?.setText(""); qtyInput?.setText("1"); otherInput?.setText("")
        renderStage()
    }

    private fun renderStage() {
        val cont = stageContainer ?: return
        cont.removeAllViews()
        staging.forEachIndexed { i, it ->
            val rowV = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
                setPadding(dp(10), dp(8), dp(10), dp(8)); setBackgroundColor(Color.parseColor("#F1F7EA"))
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply { topMargin = dp(6) }
            }
            rowV.addView(TextView(requireContext()).apply {
                text = "📦 ${it.itemName} ×${it.quantity}" + (it.serialNo?.let { s -> "  ·  SN:$s" } ?: "")
                textSize = 12.5f; layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            })
            rowV.addView(TextView(requireContext()).apply {
                text = "✕"; textSize = 15f; setTextColor(Color.parseColor("#C0392B")); setPadding(dp(10), 0, dp(4), 0)
                setOnClickListener { staging.removeAt(i); renderStage() }
            })
            cont.addView(rowV)
        }
    }

    private fun label(ctx: android.content.Context, t: String) = TextView(ctx).apply {
        text = t; textSize = 12f; setTypeface(typeface, Typeface.BOLD)
        setTextColor(Color.parseColor("#5a7a42")); setPadding(0, dp(10), 0, dp(2))
    }

    // ── Loads ──────────────────────────────────────────────────────────────────
    private fun loadItems() = lifecycleScope.launch {
        try {
            val res = RetrofitClient.instance.getAssetItems()
            itemNames = res.body()?.data ?: emptyList()
            val opts = itemNames.toMutableList().apply { add("Other…") }
            itemSpinner?.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, opts)
        } catch (_: Exception) {}
    }

    private fun loadEmployees() = lifecycleScope.launch {
        try {
            val res = RetrofitClient.instance.getAssetEmployees()
            employees = res.body()?.data ?: emptyList()
            empInput?.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, employees.map { it.label }))
        } catch (_: Exception) {}
    }

    private fun loadEmployeeAssets() = lifecycleScope.launch {
        if (selectedEmpId == 0) return@launch
        listTitle.text = "Assets with $selectedEmpLabel"
        renderLoading()
        try {
            val res = RetrofitClient.instance.getEmployeeAssets(selectedEmpId)
            render(res.body()?.data ?: emptyList(), actions = true)
        } catch (e: Exception) { renderError(e) }
    }

    private fun loadMyAssets() = lifecycleScope.launch {
        renderLoading()
        try {
            val res = RetrofitClient.instance.getMyAssets()
            render(res.body()?.data ?: emptyList(), actions = false)
        } catch (e: Exception) { renderError(e) }
    }

    // ── Allocate ────────────────────────────────────────────────────────────────
    private fun allocate() {
        if (selectedEmpId == 0) { requireContext().toast("Select an employee first"); return }
        if (staging.isEmpty()) addToStage()
        if (staging.isEmpty()) { requireContext().toast("Add at least one item"); return }
        val items = staging.toList()
        lifecycleScope.launch {
            try {
                val res = RetrofitClient.instance.allocateAssets(AllocateAssetRequest(selectedEmpId, items))
                if (res.isSuccessful && res.body()?.success == true) {
                    requireContext().toast("Allocated ✅ (${items.size} item${if (items.size > 1) "s" else ""})")
                    staging.clear(); renderStage()
                    loadEmployeeAssets()
                } else requireContext().toast(res.body()?.message ?: "Failed")
            } catch (e: Exception) { requireContext().toast("Error: ${e.message}") }
        }
    }

    private fun setStatus(id: Int, status: String) = lifecycleScope.launch {
        try {
            RetrofitClient.instance.updateAsset(id, mapOf("status" to status))
            loadEmployeeAssets()
        } catch (e: Exception) { requireContext().toast("Error: ${e.message}") }
    }

    private fun delete(id: Int) {
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Delete asset record?")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    try { RetrofitClient.instance.deleteAsset(id); loadEmployeeAssets() }
                    catch (e: Exception) { requireContext().toast("Error: ${e.message}") }
                }
            }.setNegativeButton("Cancel", null).show()
    }

    // ── Rendering ───────────────────────────────────────────────────────────────
    private fun renderLoading() { listContainer.removeAllViews(); listContainer.addView(muted("Loading…")) }
    private fun renderError(e: Exception) { listContainer.removeAllViews(); listContainer.addView(muted("Failed: ${e.message}")) }

    private fun render(rows: List<AssetAllocation>, actions: Boolean) {
        val ctx = requireContext()
        listContainer.removeAllViews()
        if (rows.isEmpty()) { listContainer.addView(muted("No assets allocated.")); return }
        rows.forEach { a ->
            val card = LinearLayout(ctx).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp(12), dp(10), dp(12), dp(10))
                setBackgroundColor(Color.parseColor("#F7FAF3"))
                val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                lp.bottomMargin = dp(8); layoutParams = lp
            }
            val returned = a.status == "returned"
            card.addView(TextView(ctx).apply {
                text = "${a.itemName}  ×${a.quantity}"; setTypeface(typeface, Typeface.BOLD); textSize = 14f
            })
            val sub = buildString {
                if (!a.serialNo.isNullOrBlank()) append("SN: ${a.serialNo}   ")
                if (!a.remark.isNullOrBlank()) append("${a.remark}   ")
                append(if (returned) "• Returned" else "• Allocated")
                if (!a.allocatedAt.isNullOrBlank()) append("  •  ${a.allocatedAt.toDisplayDate()}")
            }
            card.addView(TextView(ctx).apply {
                text = sub; textSize = 12f; setTextColor(Color.parseColor(if (returned) "#E65100" else "#2E7D32"))
            })
            if (actions) {
                val btns = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.END }
                btns.addView(Button(ctx).apply {
                    text = if (returned) "Undo" else "Returned"
                    setOnClickListener { setStatus(a.id, if (returned) "allocated" else "returned") }
                })
                btns.addView(Button(ctx).apply {
                    text = "Delete"; setTextColor(Color.parseColor("#C0392B"))
                    setOnClickListener { delete(a.id) }
                })
                card.addView(btns)
            }
            listContainer.addView(card)
        }
    }

    private fun muted(t: String) = TextView(requireContext()).apply {
        text = t; setTextColor(Color.parseColor("#8AAA6A")); setPadding(0, dp(8), 0, dp(8))
    }
}
