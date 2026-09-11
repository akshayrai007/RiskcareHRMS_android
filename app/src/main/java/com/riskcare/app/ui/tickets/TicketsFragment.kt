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

class TicketsFragment : Fragment() {
    private var mine = true
    private lateinit var rv: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var tvEmpty: TextView
    private lateinit var tabMine: TextView
    private lateinit var tabAssigned: TextView

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density
        val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(ctx.getColor(R.color.background)) }

        root.addView(LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(ctx.getColor(R.color.primary))
            setPadding((16*dp).toInt(), (16*dp).toInt(), (16*dp).toInt(), (16*dp).toInt())
            addView(TextView(ctx).apply {
                text = "🎫  Work Tickets"; textSize = 20f; setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ctx.getColor(R.color.white)); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            })
            addView(MaterialButton(ctx).apply {
                text = "+ Raise"; textSize = 12f
                setOnClickListener { showRaiseDialog(ctx) { load() } }
            })
        })

        val tabRow = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            background = pill(ctx.getColor(R.color.surface))
            setPadding((14*dp).toInt(), (10*dp).toInt(), (14*dp).toInt(), 0)
        }
        fun tabBtn() = TextView(ctx).apply {
            textSize = 13f; setTypeface(null, android.graphics.Typeface.BOLD); gravity = Gravity.CENTER
            setPadding(0, (10*dp).toInt(), 0, (10*dp).toInt())
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        tabMine = tabBtn().apply { text = "My Tickets" }
        tabAssigned = tabBtn().apply { text = "Assigned to Me" }
        tabRow.addView(tabMine); tabRow.addView(tabAssigned)
        root.addView(tabRow)

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

        tabMine.setOnClickListener { setActive(true) }
        tabAssigned.setOnClickListener { setActive(false) }
        setActive(true)
        return root
    }

    private fun setActive(showMine: Boolean) {
        mine = showMine
        val ctx = requireContext()
        tabMine.background = if (mine) pill(ctx.getColor(R.color.primary)) else null
        tabMine.setTextColor(if (mine) ctx.getColor(R.color.white) else ctx.getColor(R.color.text_secondary))
        tabAssigned.background = if (!mine) pill(ctx.getColor(R.color.primary)) else null
        tabAssigned.setTextColor(if (!mine) ctx.getColor(R.color.white) else ctx.getColor(R.color.text_secondary))
        load()
    }

    private fun load() {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density
        progress.visibility = View.VISIBLE; rv.visibility = View.GONE; tvEmpty.visibility = View.GONE
        lifecycleScope.launch {
            try {
                val res = if (mine) RetrofitClient.instance.getTickets(mine = "1")
                          else RetrofitClient.instance.getTickets(assignedToMe = "1")
                progress.visibility = View.GONE
                val list = res.body()?.data ?: emptyList()
                if (list.isEmpty()) { tvEmpty.text = if (mine) "You haven't raised any tickets." else "No tickets assigned to you."; tvEmpty.visibility = View.VISIBLE }
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
        if (mine) t.assignedToName?.let { meta.add("To $it") } else t.raisedByName?.let { meta.add("From $it") }
        t.dueDate?.let { meta.add("Due: ${it.take(10)}") }
        if (meta.isNotEmpty()) ll.addView(TextView(ctx).apply {
            text = meta.joinToString("  ·  "); textSize = 11f; setTextColor(ctx.getColor(R.color.text_hint))
        })
        card.addView(ll)
        return card
    }

    private fun showRaiseDialog(ctx: android.content.Context, onDone: () -> Unit) {
        val dp = ctx.resources.displayMetrics.density
        val container = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((20*dp).toInt(), (16*dp).toInt(), (20*dp).toInt(), 0) }
        val etTitle = EditText(ctx).apply { hint = "Title *" }
        val etDesc = EditText(ctx).apply { hint = "Description (optional)"; minLines = 2 }
        val spPriority = Spinner(ctx).apply {
            adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, listOf("Low", "Medium", "High")).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            setSelection(1)
        }
        val spAssignee = Spinner(ctx)

        container.addView(TextView(ctx).apply { text = "Route To *"; textSize = 12f; setPadding(0, (8*dp).toInt(), 0, 2) })
        container.addView(spAssignee)
        container.addView(etTitle)
        container.addView(etDesc)
        container.addView(TextView(ctx).apply { text = "Priority"; textSize = 12f; setPadding(0, (8*dp).toInt(), 0, 2) })
        container.addView(spPriority)

        var assignees: List<TicketAssignee> = emptyList()
        lifecycleScope.launch {
            try {
                val res = RetrofitClient.instance.getTicketAssignableEmployees()
                assignees = res.body()?.data ?: emptyList()
                spAssignee.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item,
                    assignees.map { "${it.name}${it.departmentName?.let { d -> " ($d)" } ?: ""}" })
            } catch (_: Exception) { ctx.toast("Could not load support staff") }
        }

        val scroll = ScrollView(ctx).apply { addView(container) }
        android.app.AlertDialog.Builder(ctx)
            .setTitle("Raise a Ticket")
            .setView(scroll)
            .setPositiveButton("Raise") { _, _ ->
                val title = etTitle.text.toString().trim()
                if (title.isEmpty()) { ctx.toast("Title is required"); return@setPositiveButton }
                val assignee = assignees.getOrNull(spAssignee.selectedItemPosition)
                if (assignee == null) { ctx.toast("Select who to route this to"); return@setPositiveButton }
                val priority = listOf("low", "medium", "high")[spPriority.selectedItemPosition]
                lifecycleScope.launch {
                    try {
                        val res = RetrofitClient.instance.createTicket(
                            CreateTicketRequest(
                                title = title,
                                description = etDesc.text.toString().trim().ifEmpty { null },
                                priority = priority,
                                assignedTo = listOf(assignee.id)
                            )
                        )
                        if (res.isSuccessful && res.body()?.success == true) { ctx.toast("Ticket raised"); onDone() }
                        else ctx.toast(res.body()?.message ?: "Failed to raise ticket")
                    } catch (_: Exception) { ctx.toast("Network error") }
                }
            }
            .setNegativeButton("Cancel", null).show()
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
            if (!t.description.isNullOrBlank()) {
                root.addView(TextView(ctx).apply {
                    text = t.description; textSize = 13f; setTextColor(ctx.getColor(R.color.text_secondary))
                    setPadding(0, (10*dp).toInt(), 0, 0)
                })
            }
            root.addView(TextView(ctx).apply {
                text = "Raised by ${t.raisedByName ?: "—"}  →  ${t.assignedToName ?: "unassigned"}"
                textSize = 12f; setTextColor(ctx.getColor(R.color.text_hint)); setPadding(0, (8*dp).toInt(), 0, 0)
            })

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
                text = "Comments"; textSize = 12f; setTypeface(null, android.graphics.Typeface.BOLD)
                setPadding(0, (14*dp).toInt(), 0, (4*dp).toInt())
            })
            val commentsWrap = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL }
            root.addView(commentsWrap)
            (t.events ?: emptyList()).filter { it.action == "comment" || it.action == "status_changed" }.forEach { e ->
                commentsWrap.addView(TextView(ctx).apply {
                    text = when (e.action) {
                        "status_changed" -> "${e.actorName ?: "Someone"} changed status to ${statusLabel(e.toStatus ?: "")}${e.note?.let { " — $it" } ?: ""}"
                        else -> "${e.actorName ?: "Someone"}: ${e.note}"
                    }
                    textSize = 12f; setTextColor(ctx.getColor(R.color.text_secondary)); setPadding(0, (4*dp).toInt(), 0, (4*dp).toInt())
                })
            }
            val etComment = EditText(ctx).apply {
                hint = "Add a comment…"; textSize = 13f
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.topMargin = (8*dp).toInt() }
            }
            root.addView(etComment)
            root.addView(MaterialButton(ctx).apply {
                text = "Post Comment"; textSize = 12f
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.topMargin = (6*dp).toInt(); it.bottomMargin = (16*dp).toInt() }
                setOnClickListener {
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
            })
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
