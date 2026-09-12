package com.riskcare.app.ui.tickets

// ═══════════════════════════════════════════════════════════════════════════════
// WORK TICKETS — a support/request ticket tracker, distinct from Task
// Assignment: any employee can raise a ticket; it gets routed to admin/HR/
// super_admin support staff and tracked through open -> in_progress ->
// resolved/closed, with a comment/status-change trail. Matches backend
// ticketController.js exactly. Pure-Kotlin UI, following this app's
// established convention (see TaskUiFragments.kt) — no XML layout.
// ═══════════════════════════════════════════════════════════════════════════════

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import com.riskcare.app.R
import com.riskcare.app.data.api.RetrofitClient
import com.riskcare.app.data.models.*
import com.riskcare.app.utils.toast
import kotlinx.coroutines.launch

private fun pill(color: Int): GradientDrawable = GradientDrawable().apply {
    shape = GradientDrawable.RECTANGLE; cornerRadius = 99f; setColor(color)
}
private fun statusColor(ctx: android.content.Context, status: String) = when (status) {
    "in_progress" -> ctx.getColor(R.color.accent_blue)
    "resolved"    -> ctx.getColor(R.color.accent_teal)
    "closed"      -> ctx.getColor(R.color.text_hint)
    else          -> ctx.getColor(R.color.status_pending)          // open
}
private fun statusLabel(status: String) = when (status) {
    "in_progress" -> "In Progress"; "resolved" -> "Resolved"; "closed" -> "Closed"; else -> "Open"
}
private fun priorityColor(ctx: android.content.Context, priority: String) = when (priority) {
    "high" -> ctx.getColor(R.color.accent_red); "low" -> ctx.getColor(R.color.text_hint)
    else -> ctx.getColor(R.color.status_pending)
}

private enum class TicketTab { MINE, ASSIGNED, SUPERVISING }

class TicketsFragment : Fragment() {
    private var tab = TicketTab.ASSIGNED
    private lateinit var rv: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var tvEmpty: TextView
    private lateinit var tabMine: TextView
    private lateinit var tabAssigned: TextView
    private lateinit var tabSupervising: TextView

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density
        val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(ctx.getColor(R.color.background)) }

        root.addView(LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            background = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(ctx.getColor(R.color.primary), ctx.getColor(R.color.accent_red))
            )
            setPadding((16*dp).toInt(), (16*dp).toInt(), (16*dp).toInt(), (16*dp).toInt())
            addView(LinearLayout(ctx).apply {
                orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
                addView(TextView(ctx).apply {
                    text = "🎫  Work Tickets"; textSize = 20f; setTypeface(null, android.graphics.Typeface.BOLD)
                    setTextColor(ctx.getColor(R.color.white)); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                })
                addView(TextView(ctx).apply {
                    text = "+ RAISE"; textSize = 12f; setTypeface(null, android.graphics.Typeface.BOLD)
                    setTextColor(ctx.getColor(R.color.white))
                    setOnClickListener { showRaiseDialog(ctx) { load() } }
                })
            })
            addView(TextView(ctx).apply {
                text = "Route and track requests"; textSize = 12f
                setTextColor(android.graphics.Color.WHITE); alpha = 0.85f
                setPadding(0, (2*dp).toInt(), 0, 0)
            })
        })

        val tabRow = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            background = pill(ctx.getColor(R.color.surface))
            setPadding((14*dp).toInt(), (10*dp).toInt(), (14*dp).toInt(), 0)
        }
        fun tabBtn() = TextView(ctx).apply {
            textSize = 12f; setTypeface(null, android.graphics.Typeface.BOLD); gravity = Gravity.CENTER
            setPadding(0, (10*dp).toInt(), 0, (10*dp).toInt())
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        tabAssigned = tabBtn().apply { text = "Assigned to Me" }
        tabMine = tabBtn().apply { text = "Raised by Me" }
        tabSupervising = tabBtn().apply { text = "Supervising" }
        tabRow.addView(tabAssigned); tabRow.addView(tabMine); tabRow.addView(tabSupervising)
        root.addView(tabRow)

        root.addView(MaterialButton(ctx).apply {
            text = "+ Raise a Ticket"; setBackgroundColor(ctx.getColor(R.color.primary))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .also { it.setMargins((14*dp).toInt(), (10*dp).toInt(), (14*dp).toInt(), 0) }
            setOnClickListener { showRaiseDialog(ctx) { load() } }
        })

        progress = ProgressBar(ctx).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .also { it.gravity = Gravity.CENTER_HORIZONTAL; it.topMargin = (24*dp).toInt() }
        }
        root.addView(progress)

        rv = RecyclerView(ctx).apply {
            layoutManager = LinearLayoutManager(ctx)
            setPadding((12*dp).toInt(), (12*dp).toInt(), (12*dp).toInt(), (80*dp).toInt())
            clipToPadding = false; visibility = View.GONE
        }
        root.addView(rv)
        tvEmpty = TextView(ctx).apply {
            text = "No tickets."; textSize = 14f; gravity = Gravity.CENTER
            setTextColor(ctx.getColor(R.color.text_hint))
            setPadding((16*dp).toInt(), (48*dp).toInt(), (16*dp).toInt(), 0)
            visibility = View.GONE
        }
        root.addView(tvEmpty)

        tabAssigned.setOnClickListener { setActive(TicketTab.ASSIGNED) }
        tabMine.setOnClickListener { setActive(TicketTab.MINE) }
        tabSupervising.setOnClickListener { setActive(TicketTab.SUPERVISING) }
        setActive(TicketTab.ASSIGNED)
        return root
    }

    private fun setActive(newTab: TicketTab) {
        tab = newTab
        val ctx = requireContext()
        fun style(v: TextView, active: Boolean) {
            v.background = if (active) pill(ctx.getColor(R.color.primary)) else null
            v.setTextColor(if (active) ctx.getColor(R.color.white) else ctx.getColor(R.color.text_secondary))
        }
        style(tabAssigned, tab == TicketTab.ASSIGNED)
        style(tabMine, tab == TicketTab.MINE)
        style(tabSupervising, tab == TicketTab.SUPERVISING)
        load()
    }

    private fun load() {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density
        progress.visibility = View.VISIBLE; rv.visibility = View.GONE; tvEmpty.visibility = View.GONE
        lifecycleScope.launch {
            try {
                val res = when (tab) {
                    TicketTab.MINE        -> RetrofitClient.instance.getTickets(mine = "1")
                    TicketTab.ASSIGNED    -> RetrofitClient.instance.getTickets(assignedToMe = "1")
                    TicketTab.SUPERVISING -> RetrofitClient.instance.getTickets(supervising = "1")
                }
                progress.visibility = View.GONE
                val list = res.body()?.data ?: emptyList()
                if (list.isEmpty()) {
                    tvEmpty.text = when (tab) {
                        TicketTab.MINE        -> "You haven't raised any tickets."
                        TicketTab.ASSIGNED    -> "No tickets assigned to you."
                        TicketTab.SUPERVISING -> "You're not supervising any tickets."
                    }
                    tvEmpty.visibility = View.VISIBLE
                }
                else {
                    rv.visibility = View.VISIBLE
                    rv.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                        override fun getItemCount() = list.size
                        override fun onCreateViewHolder(p: ViewGroup, t: Int) = object : RecyclerView.ViewHolder(FrameLayout(ctx)) {}
                        override fun onBindViewHolder(h: RecyclerView.ViewHolder, pos: Int) {
                            val frame = h.itemView as FrameLayout; frame.removeAllViews()
                            frame.addView(buildTicketCard(ctx, dp, list[pos]) { showDetailDialog(ctx, list[pos].id) { load() } })
                        }
                    }
                }
            } catch (_: Exception) {
                progress.visibility = View.GONE
                tvEmpty.text = "Could not load tickets"; tvEmpty.visibility = View.VISIBLE
            }
        }
    }

    private fun buildTicketCard(ctx: android.content.Context, dp: Float, t: Ticket, onClick: () -> Unit): CardView {
        val card = CardView(ctx).apply {
            radius = 14*dp; cardElevation = 2*dp
            layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
                .also { it.setMargins(0, 0, 0, (10*dp).toInt()) }
            setOnClickListener { onClick() }
        }
        val ll = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((14*dp).toInt(), (14*dp).toInt(), (14*dp).toInt(), (14*dp).toInt()) }
        ll.addView(TextView(ctx).apply {
            text = t.title; textSize = 15f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(ctx.getColor(R.color.text_primary))
        })
        val badgeRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; setPadding(0, (6*dp).toInt(), 0, (6*dp).toInt()) }
        badgeRow.addView(TextView(ctx).apply {
            text = t.priority.uppercase(); textSize = 9f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(ctx.getColor(R.color.white))
            background = pill(priorityColor(ctx, t.priority)); setPadding((8*dp).toInt(), (3*dp).toInt(), (8*dp).toInt(), (3*dp).toInt())
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.marginEnd = (6*dp).toInt() }
        })
        badgeRow.addView(TextView(ctx).apply {
            text = statusLabel(t.status); textSize = 9f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(ctx.getColor(R.color.white))
            background = pill(statusColor(ctx, t.status)); setPadding((8*dp).toInt(), (3*dp).toInt(), (8*dp).toInt(), (3*dp).toInt())
        })
        ll.addView(badgeRow)
        val meta = mutableListOf<String>()
        if (tab == TicketTab.MINE) t.assignedToName?.let { meta.add("To $it") } else t.raisedByName?.let { meta.add("From $it") }
        t.team?.let { meta.add("Dept: $it") }
        t.supervisorName?.let { meta.add("Supervisor: $it") }
        t.dueDate?.let { meta.add("Due: ${it.take(10)}") }
        if (meta.isNotEmpty()) ll.addView(TextView(ctx).apply {
            text = meta.joinToString("  ·  "); textSize = 11f; setTextColor(ctx.getColor(R.color.text_hint))
        })
        card.addView(ll)
        return card
    }

    private fun showRaiseDialog(ctx: android.content.Context, onDone: () -> Unit) {
        val dp = ctx.resources.displayMetrics.density

        fun fieldBg() = GradientDrawable().apply {
            setColor(android.graphics.Color.WHITE); cornerRadius = 8*dp
            setStroke((1*dp).toInt(), ctx.getColor(R.color.primary))
        }
        fun label(text: String) = TextView(ctx).apply {
            this.text = text; textSize = 12f; setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ctx.getColor(R.color.text_secondary))
            setPadding(0, (10*dp).toInt(), 0, (4*dp).toInt())
        }
        fun styledEditText(hintText: String, lines: Int = 1) = EditText(ctx).apply {
            hint = hintText; minLines = lines
            setTextColor(ctx.getColor(R.color.text_primary))
            setHintTextColor(ctx.getColor(R.color.text_hint))
            background = fieldBg()
            setPadding((10*dp).toInt(), (10*dp).toInt(), (10*dp).toInt(), (10*dp).toInt())
        }
        fun styledSpinner() = Spinner(ctx).apply { background = fieldBg() }

        // ── Outer shell: colored header banner (Riskcare red) + white card body ──
        val outer = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            background = GradientDrawable().apply { setColor(android.graphics.Color.WHITE); cornerRadius = 16*dp }
            clipToOutline = true
        }
        val header = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding((20*dp).toInt(), (18*dp).toInt(), (20*dp).toInt(), (18*dp).toInt())
            background = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(ctx.getColor(R.color.primary), ctx.getColor(R.color.accent_red))
            )
        }
        header.addView(TextView(ctx).apply {
            text = "🎫 Raise a Ticket"; textSize = 17f; setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(android.graphics.Color.WHITE)
        })
        header.addView(TextView(ctx).apply {
            text = "Route a request to the right person"; textSize = 12f
            setTextColor(android.graphics.Color.WHITE); alpha = 0.9f
            setPadding(0, (2*dp).toInt(), 0, 0)
        })
        outer.addView(header)

        // Capped so the popup never grows taller than the screen — content scrolls within it (same as KrishiHR).
        val sv = androidx.core.widget.NestedScrollView(ctx).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (0.72 * ctx.resources.displayMetrics.heightPixels).toInt()
            )
        }
        val container = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((20*dp).toInt(), (14*dp).toInt(), (20*dp).toInt(), (16*dp).toInt()) }
        sv.addView(container)
        outer.addView(sv)

        val etTitle = styledEditText("e.g. Clear pending QC backlog")
        val etDesc = styledEditText("Description (optional)", lines = 2)
        val spPriority = styledSpinner().apply {
            adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, listOf("Low", "Medium", "High")).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            setSelection(1)
        }
        val etTeam = styledEditText("Auto-fills from the assignee's department")
        val spSupervisor = styledSpinner()

        // ── Assign To (pick one or more) — checkbox list, same interaction as KrishiHR's Assign Work ──
        container.addView(label("Route To (pick one or more) *"))
        val selectedIds = mutableSetOf<Int>()
        val employeeListWrap = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            background = fieldBg()
            setPadding((8*dp).toInt(), (4*dp).toInt(), (8*dp).toInt(), (4*dp).toInt())
        }
        // No nested scroll needed here — the outer dialog scroll (`sv`) already handles
        // overflow, and the assignable-employee list is always short (support staff only).
        container.addView(employeeListWrap)

        var assignees: List<TicketAssignee> = emptyList()
        fun renderEmployeeList() {
            employeeListWrap.removeAllViews()
            if (assignees.isEmpty()) {
                employeeListWrap.addView(TextView(ctx).apply {
                    text = "Loading…"; textSize = 12f; setTextColor(ctx.getColor(R.color.text_hint))
                    gravity = Gravity.CENTER; setPadding(0, (16*dp).toInt(), 0, (16*dp).toInt())
                })
                return
            }
            assignees.forEach { emp ->
                val row = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL; setPadding(0, (4*dp).toInt(), 0, (4*dp).toInt()) }
                val cb = CheckBox(ctx).apply { isChecked = selectedIds.contains(emp.id) }
                cb.setOnCheckedChangeListener { _, checked ->
                    if (checked) {
                        selectedIds.add(emp.id)
                        if (!emp.departmentName.isNullOrBlank()) etTeam.setText(emp.departmentName)
                    } else selectedIds.remove(emp.id)
                }
                val empLabel = TextView(ctx).apply {
                    text = "${emp.name}${emp.employeeCode?.let { " ($it)" } ?: ""}${emp.departmentName?.let { " · $it" } ?: ""}"
                    textSize = 13f; setTextColor(ctx.getColor(R.color.text_primary))
                }
                row.addView(cb); row.addView(empLabel)
                employeeListWrap.addView(row)
            }
        }
        renderEmployeeList()

        container.addView(label("Title / What needs to be done *"))
        container.addView(etTitle)
        container.addView(label("Department"))
        container.addView(etTeam)
        container.addView(label("Supervisor"))
        container.addView(spSupervisor)
        container.addView(label("Description"))
        container.addView(etDesc)
        container.addView(label("Priority"))
        container.addView(spPriority)

        val btnRow = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .also { it.topMargin = (18*dp).toInt() }
        }
        val btnCancel = com.google.android.material.button.MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply {
            text = "Cancel"; setTextColor(ctx.getColor(R.color.text_secondary))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).also { it.marginEnd = (8*dp).toInt() }
        }
        val btnRaise = com.google.android.material.button.MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonStyle).apply {
            text = "🎫 Raise"; setBackgroundColor(ctx.getColor(R.color.primary))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        btnRow.addView(btnCancel); btnRow.addView(btnRaise)
        container.addView(btnRow)

        lifecycleScope.launch {
            try {
                val res = RetrofitClient.instance.getTicketAssignableEmployees()
                assignees = res.body()?.data ?: emptyList()
                renderEmployeeList()
                spSupervisor.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item,
                    listOf("— none —") + assignees.map { "${it.name}${it.departmentName?.let { d -> " ($d)" } ?: ""}" })
            } catch (_: Exception) { ctx.toast("Could not load support staff") }
        }

        val scroll = ScrollView(ctx).apply { addView(outer) }
        val dialog = android.app.AlertDialog.Builder(ctx).setView(scroll).create()
        dialog.window?.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT))

        btnCancel.setOnClickListener { dialog.dismiss() }
        btnRaise.setOnClickListener {
            val title = etTitle.text.toString().trim()
            if (title.isEmpty()) { ctx.toast("Title is required"); return@setOnClickListener }
            if (selectedIds.isEmpty()) { ctx.toast("Select at least one person to route this to"); return@setOnClickListener }
            val supervisor = if (spSupervisor.selectedItemPosition > 0) assignees.getOrNull(spSupervisor.selectedItemPosition - 1) else null
            if (supervisor != null && selectedIds.contains(supervisor.id)) { ctx.toast("Supervisor can't also be an assignee"); return@setOnClickListener }
            val priority = listOf("low", "medium", "high")[spPriority.selectedItemPosition]
            lifecycleScope.launch {
                try {
                    val res = RetrofitClient.instance.createTicket(
                        CreateTicketRequest(
                            title = title,
                            description = etDesc.text.toString().trim().ifEmpty { null },
                            priority = priority,
                            assignedTo = selectedIds.toList(),
                            team = etTeam.text.toString().trim().ifEmpty { null },
                            supervisorId = supervisor?.id
                        )
                    )
                    if (res.isSuccessful && res.body()?.success == true) { ctx.toast(res.body()?.message ?: "Ticket raised"); dialog.dismiss(); onDone() }
                    else ctx.toast(res.body()?.message ?: "Failed to raise ticket")
                } catch (_: Exception) { ctx.toast("Network error") }
            }
        }
        dialog.show()
    }

    private fun showDetailDialog(ctx: android.content.Context, ticketId: Int, onChanged: () -> Unit) {
        val dp = ctx.resources.displayMetrics.density
        val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((20*dp).toInt(), (16*dp).toInt(), (20*dp).toInt(), 0) }
        root.addView(TextView(ctx).apply { text = "Loading…"; textSize = 13f; setTextColor(ctx.getColor(R.color.text_hint)) })
        val scroll = ScrollView(ctx).apply {
            addView(root)
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (0.72 * ctx.resources.displayMetrics.heightPixels).toInt())
        }
        val dialog = android.app.AlertDialog.Builder(ctx).setView(scroll).setNegativeButton("Close", null).create()
        dialog.show()

        fun render(t: Ticket) {
            root.removeAllViews()
            root.addView(TextView(ctx).apply { text = t.title; textSize = 17f; setTypeface(null, android.graphics.Typeface.BOLD) })
            root.addView(TextView(ctx).apply {
                text = statusLabel(t.status).uppercase(); textSize = 10f; setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ctx.getColor(R.color.white)); background = pill(statusColor(ctx, t.status))
                setPadding((10*dp).toInt(), (4*dp).toInt(), (10*dp).toInt(), (4*dp).toInt())
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.topMargin = (8*dp).toInt() }
            })

            // ── Bordered info card: assigned to / assigned by / supervisor / team / due ──
            val infoCard = LinearLayout(ctx).apply {
                orientation = LinearLayout.VERTICAL
                background = GradientDrawable().apply {
                    setColor(android.graphics.Color.WHITE); cornerRadius = 12*dp
                    setStroke((1*dp).toInt(), ctx.getColor(R.color.primary))
                }
                setPadding((14*dp).toInt(), (12*dp).toInt(), (14*dp).toInt(), (12*dp).toInt())
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.topMargin = (14*dp).toInt() }
            }
            fun row(icon: String, text: String) {
                val r = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; setPadding(0, (3*dp).toInt(), 0, (3*dp).toInt()) }
                r.addView(TextView(ctx).apply { this.text = icon; textSize = 13f; setPadding(0, 0, (8*dp).toInt(), 0) })
                r.addView(TextView(ctx).apply { this.text = text; textSize = 12.5f; setTextColor(ctx.getColor(R.color.text_secondary)) })
                infoCard.addView(r)
            }
            row("👤", "Assigned to: ${t.assignedToName ?: "—"}${t.assignedToCode?.let { " ($it)" } ?: ""}")
            row("👔", "Assigned by: ${t.raisedByName ?: "—"}")
            row("🧑‍💼", "Supervisor: ${t.supervisorName ?: "—"}")
            t.team?.let { row("🧩", "Department: $it") }
            t.dueDate?.let { row("📅", "Due: ${it.take(10)}") }
            root.addView(infoCard)

            if (!t.description.isNullOrBlank()) {
                root.addView(TextView(ctx).apply {
                    text = t.description; textSize = 12.5f; setTextColor(ctx.getColor(R.color.text_secondary))
                    setPadding((14*dp).toInt(), (10*dp).toInt(), (14*dp).toInt(), (10*dp).toInt())
                    background = GradientDrawable().apply { setColor(ctx.getColor(R.color.surface)); cornerRadius = 10*dp }
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.topMargin = (10*dp).toInt() }
                })
            }

            val canAct = t.status != "closed"
            if (canAct) {
                val spinner = Spinner(ctx).apply {
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.topMargin = (10*dp).toInt() }
                    val opts = mutableListOf("Change status…")
                    listOf("open", "in_progress", "resolved", "closed").filter { it != t.status }.forEach { opts.add(statusLabel(it)) }
                    val statusValues = listOf("open", "in_progress", "resolved", "closed").filter { it != t.status }
                    adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, opts)
                    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                            if (pos == 0) return
                            val newStatus = statusValues[pos - 1]
                            lifecycleScope.launch {
                                try {
                                    val r = RetrofitClient.instance.updateTicketStatus(t.id, TicketStatusRequest(newStatus))
                                    if (r.isSuccessful && r.body()?.success == true) { ctx.toast("Status updated"); dialog.dismiss(); onChanged() }
                                    else ctx.toast(r.body()?.message ?: "Update failed")
                                } catch (_: Exception) { ctx.toast("Network error") }
                            }
                        }
                        override fun onNothingSelected(p: AdapterView<*>?) {}
                    }
                }
                root.addView(spinner)
            }

            root.addView(TextView(ctx).apply {
                text = "ACTIVITY"; textSize = 11.5f; setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ctx.getColor(R.color.primary))
                setPadding(0, (16*dp).toInt(), 0, (6*dp).toInt())
            })
            val activityWrap = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL }
            root.addView(activityWrap)
            fun activityRow(text: String) {
                activityWrap.addView(LinearLayout(ctx).apply {
                    orientation = LinearLayout.VERTICAL
                    background = GradientDrawable().apply { setColor(ctx.getColor(R.color.surface)); cornerRadius = 8*dp }
                    setPadding((10*dp).toInt(), (8*dp).toInt(), (10*dp).toInt(), (8*dp).toInt())
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.bottomMargin = (6*dp).toInt() }
                    addView(TextView(ctx).apply { this.text = text; textSize = 12.5f; setTextColor(ctx.getColor(R.color.text_secondary)) })
                })
            }
            activityRow("${t.raisedByName ?: "Someone"} created this ticket")
            (t.events ?: emptyList()).filter { it.action == "comment" || it.action == "status_changed" }.forEach { e ->
                activityRow(when (e.action) {
                    "status_changed" -> "${e.actorName ?: "Someone"} changed status to ${statusLabel(e.toStatus ?: "")}${e.note?.let { " — $it" } ?: ""}"
                    else -> "${e.actorName ?: "Someone"}: ${e.note}"
                })
            }

            val commentRow = LinearLayout(ctx).apply {
                orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.topMargin = (8*dp).toInt(); it.bottomMargin = (16*dp).toInt() }
            }
            val etComment = EditText(ctx).apply {
                hint = "Add a comment…"; textSize = 13f
                background = GradientDrawable().apply { setColor(android.graphics.Color.WHITE); cornerRadius = 8*dp; setStroke((1*dp).toInt(), ctx.getColor(R.color.primary)) }
                setPadding((10*dp).toInt(), (10*dp).toInt(), (10*dp).toInt(), (10*dp).toInt())
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).also { it.marginEnd = (8*dp).toInt() }
            }
            val btnSend = com.google.android.material.button.MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonStyle).apply {
                text = "Send"; setBackgroundColor(ctx.getColor(R.color.primary))
            }
            commentRow.addView(etComment); commentRow.addView(btnSend)
            root.addView(commentRow)
            btnSend.setOnClickListener {
                val note = etComment.text.toString().trim()
                if (note.isEmpty()) return@setOnClickListener
                lifecycleScope.launch {
                    try {
                        val r = RetrofitClient.instance.addTicketComment(t.id, TicketCommentRequest(note))
                        if (r.isSuccessful && r.body()?.success == true) { etComment.setText(""); loadDetail(ctx, ticketId) { render(it) } }
                        else ctx.toast(r.body()?.message ?: "Failed")
                    } catch (_: Exception) { ctx.toast("Network error") }
                }
            }
        }

        loadDetail(ctx, ticketId) { render(it) }
    }

    private fun loadDetail(ctx: android.content.Context, ticketId: Int, onLoaded: (Ticket) -> Unit) {
        lifecycleScope.launch {
            try {
                val res = RetrofitClient.instance.getTicket(ticketId)
                val t = res.body()?.data
                if (t != null) onLoaded(t) else ctx.toast("Could not load ticket")
            } catch (_: Exception) { ctx.toast("Network error") }
        }
    }
}
