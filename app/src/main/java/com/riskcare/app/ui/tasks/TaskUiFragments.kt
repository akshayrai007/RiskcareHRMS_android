package com.riskcare.app.ui.tasks

// ═══════════════════════════════════════════════════════════════════════════════
// TASK ASSIGNMENT + WORK TRACKER — matches web app's tasks.html/board.html/
// my-work.html/work-tracker.html exactly, including the RBAC:
//   - "manager" = has real reportees in the org chart (checked server-side via
//     GET tasks/am-i-manager), NOT the literal role field — Vijay Jain
//     (role='accounts') has reportees and must be treated as a manager.
//   - Super Admin sees/assigns everyone's tasks; a manager only their own
//     direct reportees; everyone else sees only tasks assigned to them.
//   - Work Tracker is only shown to an employee once flagged "required" by
//     their manager/Super Admin, or to whoever manages others.
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

// ── Shared helpers ────────────────────────────────────────────────────────────
private fun pill(ctx: android.content.Context, color: Int): GradientDrawable = GradientDrawable().apply {
    shape = GradientDrawable.RECTANGLE
    cornerRadius = 99f
    setColor(color)
}
private fun priorityColor(ctx: android.content.Context, priority: String) = when (priority) {
    "high" -> ctx.getColor(R.color.accent_red)
    "low"  -> ctx.getColor(R.color.text_hint)
    else   -> ctx.getColor(R.color.status_pending)
}
private fun statusColor(ctx: android.content.Context, status: String) = when (status) {
    "in_progress" -> ctx.getColor(R.color.accent_blue)
    "completed"   -> ctx.getColor(R.color.accent_teal)
    else          -> ctx.getColor(R.color.status_pending)
}
private fun statusLabel(status: String) = when (status) {
    "in_progress" -> "In Progress"
    "completed"   -> "Completed"
    else          -> "Pending"
}

private fun buildTaskCard(
    ctx: android.content.Context,
    dp: Float,
    t: Task,
    showAssignee: Boolean,
    onStatusChange: ((String) -> Unit)? = null,
    onDelete: (() -> Unit)? = null
): CardView {
    val card = CardView(ctx).apply {
        radius = 14 * dp; cardElevation = 2 * dp
        layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT
        ).also { it.setMargins(0, 0, 0, (10 * dp).toInt()) }
        if (t.isCompulsory) {
            setContentPadding(0, 0, 0, 0)
        }
    }
    val ll = LinearLayout(ctx).apply {
        orientation = LinearLayout.VERTICAL
        setPadding((14 * dp).toInt(), (14 * dp).toInt(), (14 * dp).toInt(), (14 * dp).toInt())
        if (t.isCompulsory) {
            background = GradientDrawable().apply {
                setColor(ctx.getColor(R.color.surface))
                setStroke((3 * dp).toInt(), ctx.getColor(R.color.accent_red))
                cornerRadius = 14 * dp
            }
        }
    }

    // Title row
    ll.addView(TextView(ctx).apply {
        text = t.title; textSize = 15f; setTypeface(null, android.graphics.Typeface.BOLD)
        setTextColor(ctx.getColor(R.color.text_primary))
    })

    // Badges row
    val badgeRow = LinearLayout(ctx).apply {
        orientation = LinearLayout.HORIZONTAL
        setPadding(0, (6 * dp).toInt(), 0, (6 * dp).toInt())
    }
    if (t.isCompulsory) {
        badgeRow.addView(TextView(ctx).apply {
            text = "🔴 COMPULSORY"; textSize = 9f; setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ctx.getColor(R.color.white))
            background = pill(ctx, ctx.getColor(R.color.accent_red))
            setPadding((8 * dp).toInt(), (3 * dp).toInt(), (8 * dp).toInt(), (3 * dp).toInt())
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.marginEnd = (6 * dp).toInt() }
        })
    }
    badgeRow.addView(TextView(ctx).apply {
        text = t.priority.uppercase(); textSize = 9f; setTypeface(null, android.graphics.Typeface.BOLD)
        setTextColor(ctx.getColor(R.color.white))
        background = pill(ctx, priorityColor(ctx, t.priority))
        setPadding((8 * dp).toInt(), (3 * dp).toInt(), (8 * dp).toInt(), (3 * dp).toInt())
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.marginEnd = (6 * dp).toInt() }
    })
    badgeRow.addView(TextView(ctx).apply {
        text = statusLabel(t.status); textSize = 9f; setTypeface(null, android.graphics.Typeface.BOLD)
        setTextColor(ctx.getColor(R.color.white))
        background = pill(ctx, statusColor(ctx, t.status))
        setPadding((8 * dp).toInt(), (3 * dp).toInt(), (8 * dp).toInt(), (3 * dp).toInt())
    })
    ll.addView(badgeRow)

    // Description
    if (!t.description.isNullOrBlank()) {
        ll.addView(TextView(ctx).apply {
            text = t.description; textSize = 12f
            setTextColor(ctx.getColor(R.color.text_secondary))
            setPadding(0, 0, 0, (6 * dp).toInt())
        })
    }

    // Meta line
    val metaParts = mutableListOf<String>()
    if (showAssignee) metaParts.add("👤 ${t.assigneeName ?: ""} (${t.assigneeCode ?: ""})")
    t.departmentName?.let { metaParts.add(it) }
    t.assignerName?.let { metaParts.add("By $it") }
    t.dueDate?.let { metaParts.add("Due: ${it.take(10)}") }
    if (metaParts.isNotEmpty()) {
        ll.addView(TextView(ctx).apply {
            text = metaParts.joinToString("  ·  "); textSize = 11f
            setTextColor(ctx.getColor(R.color.text_hint))
        })
    }

    // Actions
    if (onStatusChange != null && t.status != "completed") {
        val spinner = Spinner(ctx).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.topMargin = (8 * dp).toInt() }
            val opts = mutableListOf("Update status…")
            if (t.status != "in_progress") opts.add("In Progress")
            opts.add("Mark Completed")
            adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, opts)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                    val sel = opts.getOrNull(pos) ?: return
                    when (sel) {
                        "In Progress" -> onStatusChange("in_progress")
                        "Mark Completed" -> onStatusChange("completed")
                    }
                }
                override fun onNothingSelected(p: AdapterView<*>?) {}
            }
        }
        ll.addView(spinner)
    }
    if (onDelete != null) {
        ll.addView(MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply {
            text = "🗑 Delete"; textSize = 11f
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.topMargin = (6 * dp).toInt() }
            setOnClickListener {
                android.app.AlertDialog.Builder(ctx)
                    .setTitle("Delete Task").setMessage("Delete \"${t.title}\"?")
                    .setPositiveButton("Delete") { _, _ -> onDelete() }
                    .setNegativeButton("Cancel", null).show()
            }
        })
    }

    card.addView(ll)
    return card
}

private fun sectionHeader(ctx: android.content.Context, dp: Float, text: String, bg: Int): TextView =
    TextView(ctx).apply {
        this.text = text; textSize = 13f; setTypeface(null, android.graphics.Typeface.BOLD)
        setTextColor(ctx.getColor(R.color.white))
        setBackgroundColor(bg)
        setPadding((14 * dp).toInt(), (10 * dp).toInt(), (14 * dp).toInt(), (10 * dp).toInt())
    }

private fun loaderProgress(ctx: android.content.Context): ProgressBar = ProgressBar(ctx).apply {
    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        .also { it.gravity = Gravity.CENTER_HORIZONTAL; it.topMargin = (24 * (ctx.resources.displayMetrics.density)).toInt() }
}

// ═══════════════════════════════════════════════════════════════════════════════
// MY WORK — every employee's own assigned tasks
// ═══════════════════════════════════════════════════════════════════════════════
class MyWorkFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density
        val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(ctx.getColor(R.color.background)) }

        root.addView(LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(ctx.getColor(R.color.primary))
            setPadding((16 * dp).toInt(), (16 * dp).toInt(), (16 * dp).toInt(), (16 * dp).toInt())
            addView(TextView(ctx).apply {
                text = "🙋  My Work"; textSize = 20f; setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ctx.getColor(R.color.white))
            })
        })

        val progress = loaderProgress(ctx); root.addView(progress)
        val rv = RecyclerView(ctx).apply {
            layoutManager = LinearLayoutManager(ctx)
            setPadding((12 * dp).toInt(), (12 * dp).toInt(), (12 * dp).toInt(), (80 * dp).toInt())
            clipToPadding = false; visibility = View.GONE
        }
        root.addView(rv)
        val tvEmpty = TextView(ctx).apply {
            text = "No tasks assigned to you."; textSize = 14f; gravity = Gravity.CENTER
            setTextColor(ctx.getColor(R.color.text_hint))
            setPadding((16 * dp).toInt(), (48 * dp).toInt(), (16 * dp).toInt(), 0)
            visibility = View.GONE
        }
        root.addView(tvEmpty)

        fun load() {
            lifecycleScope.launch {
                try {
                    val res = RetrofitClient.instance.getTasks(mine = "1")
                    progress.visibility = View.GONE
                    val list = res.body()?.data ?: emptyList()
                    if (list.isEmpty()) { tvEmpty.visibility = View.VISIBLE; rv.visibility = View.GONE }
                    else {
                        rv.visibility = View.VISIBLE; tvEmpty.visibility = View.GONE
                        rv.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                            override fun getItemCount() = list.size
                            override fun onCreateViewHolder(p: ViewGroup, t: Int) =
                                object : RecyclerView.ViewHolder(FrameLayout(ctx)) {}
                            override fun onBindViewHolder(h: RecyclerView.ViewHolder, pos: Int) {
                                val frame = h.itemView as FrameLayout
                                frame.removeAllViews()
                                val t = list[pos]
                                frame.addView(buildTaskCard(ctx, dp, t, showAssignee = false, onStatusChange = { newStatus ->
                                    lifecycleScope.launch {
                                        try {
                                            val r = RetrofitClient.instance.updateTaskStatus(t.id, TaskStatusRequest(newStatus))
                                            if (r.isSuccessful && r.body()?.success == true) { ctx.toast("Task updated"); load() }
                                            else ctx.toast(r.body()?.message ?: "Update failed")
                                        } catch (_: Exception) { ctx.toast("Network error") }
                                    }
                                }))
                            }
                        }
                    }
                } catch (_: Exception) {
                    progress.visibility = View.GONE
                    tvEmpty.text = "Could not load tasks"; tvEmpty.visibility = View.VISIBLE
                }
            }
        }
        load()
        return root
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ALL TASKS — Manager/Super Admin list view with Assign Task
// ═══════════════════════════════════════════════════════════════════════════════
class AllTasksFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density
        val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(ctx.getColor(R.color.background)) }

        root.addView(LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(ctx.getColor(R.color.primary))
            setPadding((16 * dp).toInt(), (16 * dp).toInt(), (16 * dp).toInt(), (16 * dp).toInt())
            addView(TextView(ctx).apply {
                text = "📋  All Tasks"; textSize = 20f; setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ctx.getColor(R.color.white)); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            })
            addView(MaterialButton(ctx).apply {
                text = "+ Assign"; textSize = 12f
                setOnClickListener { showAssignDialog(ctx) { load() } }
            })
        })

        val progress = loaderProgress(ctx); root.addView(progress)
        val rv = RecyclerView(ctx).apply {
            layoutManager = LinearLayoutManager(ctx)
            setPadding((12 * dp).toInt(), (12 * dp).toInt(), (12 * dp).toInt(), (80 * dp).toInt())
            clipToPadding = false; visibility = View.GONE
        }
        root.addView(rv)
        val tvEmpty = TextView(ctx).apply {
            text = "No tasks found."; textSize = 14f; gravity = Gravity.CENTER
            setTextColor(ctx.getColor(R.color.text_hint))
            setPadding((16 * dp).toInt(), (48 * dp).toInt(), (16 * dp).toInt(), 0)
            visibility = View.GONE
        }
        root.addView(tvEmpty)

        fun load() {
            lifecycleScope.launch {
                try {
                    val res = RetrofitClient.instance.getTasks()
                    progress.visibility = View.GONE
                    val list = res.body()?.data ?: emptyList()
                    if (list.isEmpty()) { tvEmpty.visibility = View.VISIBLE; rv.visibility = View.GONE }
                    else {
                        rv.visibility = View.VISIBLE; tvEmpty.visibility = View.GONE
                        rv.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                            override fun getItemCount() = list.size
                            override fun onCreateViewHolder(p: ViewGroup, t: Int) =
                                object : RecyclerView.ViewHolder(FrameLayout(ctx)) {}
                            override fun onBindViewHolder(h: RecyclerView.ViewHolder, pos: Int) {
                                val frame = h.itemView as FrameLayout
                                frame.removeAllViews()
                                val t = list[pos]
                                frame.addView(buildTaskCard(ctx, dp, t, showAssignee = true, onDelete = {
                                    lifecycleScope.launch {
                                        try {
                                            val r = RetrofitClient.instance.deleteTask(t.id)
                                            if (r.isSuccessful && r.body()?.success == true) { ctx.toast("Task deleted"); load() }
                                            else ctx.toast(r.body()?.message ?: "Delete failed")
                                        } catch (_: Exception) { ctx.toast("Network error") }
                                    }
                                }))
                            }
                        }
                    }
                } catch (_: Exception) {
                    progress.visibility = View.GONE
                    tvEmpty.text = "Could not load tasks"; tvEmpty.visibility = View.VISIBLE
                }
            }
        }
        // expose load() to the + Assign button's callback via closure trick
        this.reloadFn = ::load
        load()
        return root
    }

    private var reloadFn: (() -> Unit)? = null
    private fun load() { reloadFn?.invoke() }

    private fun showAssignDialog(ctx: android.content.Context, onDone: () -> Unit) {
        val dp = ctx.resources.displayMetrics.density
        val container = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding((20 * dp).toInt(), (16 * dp).toInt(), (20 * dp).toInt(), 0)
        }
        val spAssignee = Spinner(ctx)
        val etTitle = EditText(ctx).apply { hint = "Task title *" }
        val etDesc = EditText(ctx).apply { hint = "Description (optional)"; minLines = 2 }
        val spPriority = Spinner(ctx).apply {
            adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, listOf("Low", "Medium", "High")).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            setSelection(1)
        }
        val cbCompulsory = CheckBox(ctx).apply { text = "🔴 Compulsory" }

        container.addView(TextView(ctx).apply { text = "Assign To *"; textSize = 12f; setPadding(0, (8*dp).toInt(), 0, 2) })
        container.addView(spAssignee)
        container.addView(etTitle.also { (it.layoutParams as? ViewGroup.MarginLayoutParams) })
        container.addView(etDesc)
        container.addView(TextView(ctx).apply { text = "Priority"; textSize = 12f; setPadding(0, (8*dp).toInt(), 0, 2) })
        container.addView(spPriority)
        container.addView(cbCompulsory)

        var employees: List<AssignableEmployee> = emptyList()
        lifecycleScope.launch {
            try {
                val res = RetrofitClient.instance.getAssignableEmployees()
                employees = res.body()?.data ?: emptyList()
                spAssignee.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item,
                    employees.map { "${it.name} (${it.employeeCode})" })
            } catch (_: Exception) { ctx.toast("Could not load employees") }
        }

        val scroll = ScrollView(ctx).apply { addView(container) }
        android.app.AlertDialog.Builder(ctx)
            .setTitle("Assign Task")
            .setView(scroll)
            .setPositiveButton("Assign") { _, _ ->
                val title = etTitle.text.toString().trim()
                if (title.isEmpty()) { ctx.toast("Title is required"); return@setPositiveButton }
                val assignee = employees.getOrNull(spAssignee.selectedItemPosition)
                if (assignee == null) { ctx.toast("Select an employee"); return@setPositiveButton }
                val priority = listOf("low", "medium", "high")[spPriority.selectedItemPosition]
                lifecycleScope.launch {
                    try {
                        val res = RetrofitClient.instance.createTask(
                            TaskCreateRequest(
                                title = title,
                                description = etDesc.text.toString().trim().ifEmpty { null },
                                assignedTo = assignee.id,
                                priority = priority,
                                isCompulsory = cbCompulsory.isChecked
                            )
                        )
                        if (res.isSuccessful && res.body()?.success == true) { ctx.toast("Task assigned"); onDone() }
                        else ctx.toast(res.body()?.message ?: "Failed to assign task")
                    } catch (_: Exception) { ctx.toast("Network error") }
                }
            }
            .setNegativeButton("Cancel", null).show()
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// TASK BOARD — Pending / In Progress / Completed sections, Manager/Super Admin
// ═══════════════════════════════════════════════════════════════════════════════
class TaskBoardFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density
        val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(ctx.getColor(R.color.background)) }

        root.addView(LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(ctx.getColor(R.color.primary))
            setPadding((16 * dp).toInt(), (16 * dp).toInt(), (16 * dp).toInt(), (16 * dp).toInt())
            addView(TextView(ctx).apply {
                text = "🗂️  Task Board"; textSize = 20f; setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ctx.getColor(R.color.white))
            })
        })

        val progress = loaderProgress(ctx); root.addView(progress)
        val scroll = ScrollView(ctx)
        val content = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 0, 0, (80 * dp).toInt())
        }
        scroll.addView(content)
        root.addView(scroll)

        lifecycleScope.launch {
            try {
                val res = RetrofitClient.instance.getTaskBoard()
                progress.visibility = View.GONE
                val data = res.body()?.data ?: return@launch
                val sections = listOf(
                    Triple("🟡 Pending", data.columns.pending, ctx.getColor(R.color.status_pending)),
                    Triple("🔵 In Progress", data.columns.inProgress, ctx.getColor(R.color.accent_blue)),
                    Triple("🟢 Completed", data.columns.completed, ctx.getColor(R.color.accent_teal))
                )
                sections.forEach { (label, tasks, color) ->
                    content.addView(sectionHeader(ctx, dp, "$label (${tasks.size})", color))
                    val inner = LinearLayout(ctx).apply {
                        orientation = LinearLayout.VERTICAL
                        setPadding((12 * dp).toInt(), (12 * dp).toInt(), (12 * dp).toInt(), (4 * dp).toInt())
                    }
                    if (tasks.isEmpty()) {
                        inner.addView(TextView(ctx).apply {
                            text = "No tasks"; textSize = 12f; setTextColor(ctx.getColor(R.color.text_hint))
                            setPadding(0, 0, 0, (12*dp).toInt())
                        })
                    } else {
                        tasks.forEach { t -> inner.addView(buildTaskCard(ctx, dp, t, showAssignee = true)) }
                    }
                    content.addView(inner)
                }
            } catch (_: Exception) {
                progress.visibility = View.GONE
                content.addView(TextView(ctx).apply { text = "Could not load board"; setPadding((16*dp).toInt(), (16*dp).toInt(), 0, 0) })
            }
        }
        return root
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// WORK TRACKER — submit own daily log; managers/Super Admin also get a
// required-employee toggle list + everyone's submitted logs
// ═══════════════════════════════════════════════════════════════════════════════
class WorkTrackerFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density
        val root = ScrollView(ctx).apply { setBackgroundColor(ctx.getColor(R.color.background)) }
        val content = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding(0, 0, 0, (80*dp).toInt()) }
        root.addView(content)

        content.addView(LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(ctx.getColor(R.color.primary))
            setPadding((16 * dp).toInt(), (16 * dp).toInt(), (16 * dp).toInt(), (16 * dp).toInt())
            addView(TextView(ctx).apply {
                text = "📝  Work Tracker"; textSize = 20f; setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ctx.getColor(R.color.white))
            })
        })

        val progress = loaderProgress(ctx); content.addView(progress)

        lifecycleScope.launch {
            try {
                val res = RetrofitClient.instance.getWorkTrackerMyStatus()
                progress.visibility = View.GONE
                val d = res.body()?.data
                val canManage = d?.canManageOthers == true

                // Matches KrishiHR exactly: everyone can submit their own log,
                // always. The manage/view-others panel is admin-tier only
                // (HR/Accounts/Admin/Super Admin).
                buildSubmitSection(ctx, dp, content)
                if (canManage) buildManageSection(ctx, dp, content)
            } catch (_: Exception) {
                progress.visibility = View.GONE
                content.addView(TextView(ctx).apply { text = "Could not load Work Tracker"; setPadding((16*dp).toInt(), (16*dp).toInt(), 0, 0) })
            }
        }
        return root
    }

    // Matches KrishiHR's unified progress-log form exactly: Team, % Done Today
    // (% Remaining derived), Today's Task, This Week's Task/Goal, Blockers, Remark.
    private fun buildSubmitSection(ctx: android.content.Context, dp: Float, content: LinearLayout) {
        val card = CardView(ctx).apply {
            radius = 14 * dp; cardElevation = 2 * dp
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .also { it.setMargins((14*dp).toInt(), (14*dp).toInt(), (14*dp).toInt(), (14*dp).toInt()) }
        }
        val inner = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((16*dp).toInt(), (16*dp).toInt(), (16*dp).toInt(), (16*dp).toInt()) }
        val tvTitle = TextView(ctx).apply { text = "📝 Submit Today's Work Report"; textSize = 15f; setTypeface(null, android.graphics.Typeface.BOLD) }
        inner.addView(tvTitle)

        fun label(text: String) = TextView(ctx).apply {
            this.text = text; textSize = 11f; setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ctx.getColor(R.color.text_secondary)); setPadding(0, (10*dp).toInt(), 0, (4*dp).toInt())
        }
        inner.addView(label("Team (Department)"))
        val spTeam = Spinner(ctx).apply { adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, listOf("— select —")) }
        inner.addView(spTeam)
        var departmentNames: List<String> = emptyList()

        inner.addView(label("% Done Today"))
        val etDone = EditText(ctx).apply { hint = "e.g. 50"; inputType = android.text.InputType.TYPE_CLASS_NUMBER }
        inner.addView(etDone)

        inner.addView(label("Today's Task — what are you working on? *"))
        val etToday = EditText(ctx).apply { hint = "e.g. Followed up on 15 pending motor insurance claims"; minLines = 3 }
        inner.addView(etToday)

        inner.addView(label("This Week's Task / Goal (optional)"))
        val etWeek = EditText(ctx).apply { hint = "e.g. Close all pending renewal quotes by Friday"; minLines = 2 }
        inner.addView(etWeek)

        inner.addView(label("Blockers (optional)"))
        val etBlockers = EditText(ctx).apply { hint = "e.g. Waiting on client documents"; minLines = 2 }
        inner.addView(etBlockers)

        inner.addView(label("Remark (optional)"))
        val etRemark = EditText(ctx).apply { hint = "e.g. Client meeting ran long today"; minLines = 2 }
        inner.addView(etRemark)

        val btnSubmit = MaterialButton(ctx).apply {
            text = "✅ Submit Today's Progress"
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.topMargin = (12*dp).toInt() }
        }
        inner.addView(btnSubmit)
        card.addView(inner)
        content.addView(card)

        // Load real company departments (Marketing, HR, Operations, etc. —
        // not KrishiHR's agri-survey teams), then pre-fill from today's entry
        // if one already exists (edit-in-place).
        lifecycleScope.launch {
            try {
                val deptRes = RetrofitClient.instance.getDepartments()
                departmentNames = deptRes.body()?.data?.map { it.name } ?: emptyList()
                spTeam.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, listOf("— select —") + departmentNames)
            } catch (_: Exception) {}
            try {
                val res = RetrofitClient.instance.getMyWorkLogToday()
                val cur = res.body()?.data
                if (cur != null) {
                    val idx = departmentNames.indexOf(cur.team)
                    if (idx >= 0) spTeam.setSelection(idx + 1)
                    etDone.setText(cur.percentDone.toString())
                    etToday.setText(cur.todayTask ?: "")
                    etWeek.setText(cur.weekTask ?: "")
                    etBlockers.setText(cur.blockers ?: "")
                    etRemark.setText(cur.remark ?: "")
                    btnSubmit.text = "✅ Update Today's Progress"
                }
            } catch (_: Exception) {}
        }

        btnSubmit.setOnClickListener {
            val todayTask = etToday.text.toString().trim()
            if (todayTask.isEmpty()) { ctx.toast("Describe today's task"); return@setOnClickListener }
            lifecycleScope.launch {
                try {
                    val res = RetrofitClient.instance.submitWorkLog(
                        WorkLogSubmitRequest(
                            team = if (spTeam.selectedItemPosition > 0) spTeam.selectedItem as? String else null,
                            todayTask = todayTask,
                            percentDone = etDone.text.toString().toIntOrNull() ?: 0,
                            weekTask = etWeek.text.toString().trim().ifEmpty { null },
                            blockers = etBlockers.text.toString().trim().ifEmpty { null },
                            remark = etRemark.text.toString().trim().ifEmpty { null }
                        )
                    )
                    if (res.isSuccessful && res.body()?.success == true) {
                        ctx.toast("Progress submitted"); btnSubmit.text = "✅ Update Today's Progress"
                    } else ctx.toast(res.body()?.message ?: "Failed to submit")
                } catch (_: Exception) { ctx.toast("Network error") }
            }
        }
    }

    private fun buildManageSection(ctx: android.content.Context, dp: Float, content: LinearLayout) {
        content.addView(sectionHeader(ctx, dp, "👥 Who Must Fill Work Tracker", ctx.getColor(R.color.accent_blue)))
        val listContainer = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((12*dp).toInt(), (8*dp).toInt(), (12*dp).toInt(), (8*dp).toInt()) }
        content.addView(listContainer)

        lifecycleScope.launch {
            try {
                val res = RetrofitClient.instance.getWorkTrackerRequiredList()
                val list = res.body()?.data ?: emptyList()
                if (list.isEmpty()) {
                    listContainer.addView(TextView(ctx).apply { text = "No employees to manage."; textSize = 12f; setTextColor(ctx.getColor(R.color.text_hint)) })
                } else {
                    list.forEach { e ->
                        val row = LinearLayout(ctx).apply {
                            orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
                            setPadding(0, (8*dp).toInt(), 0, (8*dp).toInt())
                        }
                        row.addView(LinearLayout(ctx).apply {
                            orientation = LinearLayout.VERTICAL
                            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                            addView(TextView(ctx).apply { text = "${e.name} (${e.employeeCode})"; textSize = 13f; setTypeface(null, android.graphics.Typeface.BOLD) })
                            e.departmentName?.let { addView(TextView(ctx).apply { text = it; textSize = 11f; setTextColor(ctx.getColor(R.color.text_hint)) }) }
                        })
                        val sw = com.google.android.material.switchmaterial.SwitchMaterial(ctx).apply {
                            isChecked = e.workTrackerRequired
                            setOnCheckedChangeListener { _, checked ->
                                lifecycleScope.launch {
                                    try {
                                        val r = RetrofitClient.instance.setWorkTrackerRequired(WorkTrackerSetRequiredRequest(e.id, checked))
                                        if (!(r.isSuccessful && r.body()?.success == true)) ctx.toast(r.body()?.message ?: "Failed to update")
                                    } catch (_: Exception) { ctx.toast("Network error") }
                                }
                            }
                        }
                        row.addView(sw)
                        listContainer.addView(row)
                    }
                }
            } catch (_: Exception) {
                listContainer.addView(TextView(ctx).apply { text = "Could not load list"; textSize = 12f })
            }
        }

        content.addView(sectionHeader(ctx, dp, "📋 Submitted Logs", ctx.getColor(R.color.accent_teal)))
        val logsContainer = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((12*dp).toInt(), (8*dp).toInt(), (12*dp).toInt(), (8*dp).toInt()) }
        content.addView(logsContainer)

        lifecycleScope.launch {
            try {
                val res = RetrofitClient.instance.getWorkTrackerLogs()
                val logs = res.body()?.data ?: emptyList()
                if (logs.isEmpty()) {
                    logsContainer.addView(TextView(ctx).apply { text = "No logs submitted yet."; textSize = 12f; setTextColor(ctx.getColor(R.color.text_hint)) })
                } else {
                    logs.forEach { l ->
                        val card = CardView(ctx).apply {
                            radius = 10 * dp; cardElevation = 1 * dp
                            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.bottomMargin = (8*dp).toInt() }
                        }
                        val inner = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((12*dp).toInt(), (10*dp).toInt(), (12*dp).toInt(), (10*dp).toInt()) }
                        inner.addView(LinearLayout(ctx).apply {
                            orientation = LinearLayout.HORIZONTAL
                            addView(TextView(ctx).apply {
                                text = "${l.employeeName} (${l.employeeCode})  ·  ${l.logDate}"
                                textSize = 12f; setTypeface(null, android.graphics.Typeface.BOLD)
                                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                            })
                            addView(TextView(ctx).apply {
                                text = "${l.percentDone}% done"; textSize = 11f; setTypeface(null, android.graphics.Typeface.BOLD)
                                setTextColor(if (l.percentDone >= 100) android.graphics.Color.parseColor("#16a34a") else if (l.percentDone >= 50) android.graphics.Color.parseColor("#2563eb") else android.graphics.Color.parseColor("#dc2626"))
                            })
                        })
                        l.team?.let { inner.addView(TextView(ctx).apply { text = "Team: $it"; textSize = 11f; setTextColor(ctx.getColor(R.color.text_hint)); setPadding(0, (2*dp).toInt(), 0, 0) }) }
                        inner.addView(TextView(ctx).apply { text = l.todayTask ?: ""; textSize = 12f; setTextColor(ctx.getColor(R.color.text_secondary)); setPadding(0, (4*dp).toInt(), 0, 0) })
                        l.blockers?.let { inner.addView(TextView(ctx).apply { text = "Blockers: $it"; textSize = 11f; setTextColor(android.graphics.Color.parseColor("#dc2626")); setPadding(0, (4*dp).toInt(), 0, 0) }) }
                        card.addView(inner)
                        logsContainer.addView(card)
                    }
                }
            } catch (_: Exception) {
                logsContainer.addView(TextView(ctx).apply { text = "Could not load logs"; textSize = 12f })
            }
        }
    }
}
