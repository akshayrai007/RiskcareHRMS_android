package com.riskcare.app.ui.more

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
import com.riskcare.app.data.models.EmpDocument
import com.riskcare.app.utils.SessionManager
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class MyDocumentsFragment : Fragment() {

    private val api     get() = RetrofitClient.instance
    private lateinit var session: SessionManager
    private var employeeId = 0

    private var uploadDocKey   = ""
    private var uploadDocLabel = ""
    private var uploadedDocs: Map<String, List<EmpDocument>> = emptyMap()

    // Document checklist — matching web documentsController
    private val DOC_CHECKLIST = linkedMapOf(
        "identity" to listOf(
            "aadhar"     to "Aadhaar Card",
            "pan"        to "PAN Card"
        ),
        "education" to listOf(
            "10th_cert"  to "10th Certificate",
            "12th_cert"  to "12th Certificate",
            "graduation" to "Graduation Degree",
            "pg"         to "Post Graduation Degree",
            "other_edu"  to "Other Education Certificate"
        ),
        "employment" to listOf(
            "offer_letter"     to "Offer Letter (Previous)",
            "exp_letter"       to "Experience Letter",
            "relieving_letter" to "Relieving Letter",
            "salary_slips"     to "Salary Slips (Last 3 months)",
            "form16"           to "Form 16"
        ),
        "financial" to listOf(
            "bank_passbook"   to "Bank Passbook / Cancelled Cheque",
            "pan_bank"        to "PAN (Bank linked)",
            "pf_statement"    to "PF Statement",
            "salary_cert"     to "Salary Certificate"
        ),
        "other" to listOf(
            "photo"           to "Passport Size Photo",
            "medical_cert"    to "Medical Fitness Certificate",
            "address_proof"   to "Address Proof",
            "noc"             to "NOC from Previous Employer"
        )
    )

    private val pickFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uploadFile(it) }
        }
    }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        session    = SessionManager(requireContext())
        employeeId = session.getEmployee()?.id ?: 0
        return ScrollView(requireContext()).apply {
            val outer = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                tag = "docRoot"
            }
            addView(outer)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDocuments()
    }

    private fun loadDocuments() {
        lifecycleScope.launch {
            val resp = try { api.getEmpDocuments(employeeId) } catch (e: Exception) { null }
            val list = resp?.body()?.list ?: emptyList()
            uploadedDocs = list.groupBy { it.docKey }
            renderChecklist()
        }
    }

    private fun renderChecklist() {
        val ctx    = context ?: return
        val scroll = view as? ScrollView ?: return
        val root   = scroll.findViewWithTag<LinearLayout>("docRoot") ?: return
        root.removeAllViews()

        // Header
        root.addView(LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#B71C1C"))
            setPadding(32, 28, 32, 24)
            addView(TextView(ctx).apply {
                text = "📁 My Documents"
                textSize = 20f; setTextColor(Color.WHITE)
                setTypeface(typeface, android.graphics.Typeface.BOLD)
            })
            addView(TextView(ctx).apply {
                text = "Upload and manage your employee documents"
                textSize = 12f; setTextColor(Color.parseColor("#EF9A9A"))
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { topMargin = 4 }
            })
        })

        // Progress summary
        val totalDocs    = DOC_CHECKLIST.values.sumOf { it.size }
        val uploadedCount = DOC_CHECKLIST.values.sumOf { defs -> defs.count { (key, _) -> uploadedDocs.containsKey(key) } }
        root.addView(LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            setBackgroundColor(Color.WHITE)
            setPadding(28, 16, 28, 16)
            gravity = android.view.Gravity.CENTER_VERTICAL
            addView(TextView(ctx).apply {
                text = "📊 $uploadedCount / $totalDocs documents uploaded"
                textSize = 13f; setTextColor(Color.parseColor("#B71C1C"))
                setTypeface(typeface, android.graphics.Typeface.BOLD)
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            })
            val pct = if (totalDocs > 0) (uploadedCount * 100 / totalDocs) else 0
            addView(TextView(ctx).apply {
                text = "$pct%"
                textSize = 14f; setTextColor(if (pct == 100) Color.parseColor("#991B1B") else Color.parseColor("#92400E"))
                setTypeface(typeface, android.graphics.Typeface.BOLD)
            })
        })

        // ProgressBar
        root.addView(android.widget.ProgressBar(ctx, null, android.R.attr.progressBarStyleHorizontal).apply {
            max = totalDocs; progress = uploadedCount
            progressDrawable?.setColorFilter(Color.parseColor("#B71C1C"), android.graphics.PorterDuff.Mode.SRC_IN)
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 8).apply { leftMargin = 28; rightMargin = 28; bottomMargin = 8 }
        })

        val padding = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.setMargins(16, 8, 16, 0) }

        // Each category
        for ((cat, defs) in DOC_CHECKLIST) {
            val catLabel = when (cat) {
                "identity"   -> "🪪 Identity Documents"
                "education"  -> "🎓 Education Documents"
                "employment" -> "💼 Employment Documents"
                "financial"  -> "🏦 Financial Documents"
                else         -> "📎 Other Documents"
            }
            val catUploaded = defs.count { (key, _) -> uploadedDocs.containsKey(key) }
            val catDone     = catUploaded == defs.size

            val cardContent = LinearLayout(ctx).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(24, 18, 24, 12)
                setBackgroundColor(if (catDone) Color.parseColor("#FFF5F5") else Color.WHITE)
            }

            // Category header
            val hRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; gravity = android.view.Gravity.CENTER_VERTICAL }
            hRow.addView(TextView(ctx).apply {
                text = catLabel; textSize = 14f
                setTextColor(if (catDone) Color.parseColor("#991B1B") else Color.parseColor("#B71C1C"))
                setTypeface(typeface, android.graphics.Typeface.BOLD)
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            })
            if (catDone) {
                hRow.addView(TextView(ctx).apply {
                    text = "✅ Complete"; textSize = 11f
                    setTextColor(Color.parseColor("#991B1B")); setBackgroundColor(Color.parseColor("#FEE2E2"))
                    setPadding(12, 4, 12, 4)
                })
            } else {
                hRow.addView(TextView(ctx).apply {
                    text = "$catUploaded/${defs.size}"; textSize = 11f
                    setTextColor(Color.parseColor("#6B7280")); setBackgroundColor(Color.parseColor("#F3F4F6"))
                    setPadding(12, 4, 12, 4)
                })
            }
            cardContent.addView(hRow)
            cardContent.addView(View(ctx).apply { layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1).apply { topMargin = 10; bottomMargin = 8 }; setBackgroundColor(Color.parseColor("#E5E7EB")) })

            // Each doc
            for ((docKey, docLabel) in defs) {
                val existing = uploadedDocs[docKey] ?: emptyList()
                val isDone   = existing.isNotEmpty()

                val docRow = LinearLayout(ctx).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { bottomMargin = 10 }
                }

                val topRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; gravity = android.view.Gravity.CENTER_VERTICAL }
                topRow.addView(TextView(ctx).apply {
                    text = if (isDone) "✅ $docLabel" else "⬜ $docLabel"
                    textSize = 12f
                    setTextColor(if (isDone) Color.parseColor("#991B1B") else Color.parseColor("#374151"))
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                })
                topRow.addView(Button(ctx).apply {
                    text = if (isDone) "Replace" else "+ Upload"
                    textSize = 10f; setTextColor(Color.WHITE)
                    backgroundTintList = android.content.res.ColorStateList.valueOf(
                        if (isDone) Color.parseColor("#6B7280") else Color.parseColor("#B71C1C")
                    )
                    setPadding(16, 4, 16, 4)
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    setOnClickListener {
                        uploadDocKey   = docKey
                        uploadDocLabel = docLabel
                        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                            type = "*/*"
                            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/pdf", "image/jpeg", "image/png"))
                        }
                        pickFileLauncher.launch(intent)
                    }
                })
                docRow.addView(topRow)

                for (doc in existing) {
                    val fileRow = LinearLayout(ctx).apply {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = android.view.Gravity.CENTER_VERTICAL
                        setPadding(0, 4, 0, 4)
                    }
                    fileRow.addView(TextView(ctx).apply {
                        text = if (doc.mimeType?.contains("pdf") == true) "📄" else "🖼️"
                        textSize = 14f; layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { marginEnd = 6 }
                    })
                    fileRow.addView(TextView(ctx).apply {
                        text = doc.originalName ?: docLabel
                        textSize = 11f; setTextColor(Color.parseColor("#374151"))
                        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                    })
                    fileRow.addView(Button(ctx).apply {
                        text = "👁"; textSize = 11f
                        setTextColor(Color.parseColor("#1565C0"))
                        backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#DBEAFE"))
                        setPadding(12, 2, 12, 2)
                        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { marginEnd = 6 }
                        setOnClickListener { viewDocument(doc) }
                    })
                    fileRow.addView(Button(ctx).apply {
                        text = "🗑"; textSize = 11f
                        setTextColor(Color.parseColor("#DC2626"))
                        backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#FEE2E2"))
                        setPadding(12, 2, 12, 2)
                        setOnClickListener { confirmDelete(doc) }
                    })
                    docRow.addView(fileRow)
                }
                cardContent.addView(docRow)
            }

            root.addView(androidx.cardview.widget.CardView(ctx).apply {
                radius = 20f; cardElevation = if (catDone) 6f else 3f
                layoutParams = padding
                addView(cardContent)
            })
        }

        // Bottom spacer
        root.addView(View(ctx).apply { layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60) })
    }

    private fun uploadFile(uri: Uri) {
        val ctx  = context ?: return
        val name = run {
            var n: String? = null
            ctx.contentResolver.query(uri, null, null, null, null)?.use {
                if (it.moveToFirst()) { val idx = it.getColumnIndex(OpenableColumns.DISPLAY_NAME); if (idx != -1) n = it.getString(idx) }
            }
            n ?: uri.lastPathSegment ?: "document"
        }
        val mime = ctx.contentResolver.getType(uri) ?: "application/octet-stream"
        val tmp  = File(ctx.cacheDir, "doc_upload_${System.currentTimeMillis()}_$name")
        try { ctx.contentResolver.openInputStream(uri)?.use { i -> FileOutputStream(tmp).use { o -> i.copyTo(o) } } }
        catch (e: Exception) { Toast.makeText(ctx, "Could not read file", Toast.LENGTH_SHORT).show(); return }

        Toast.makeText(ctx, "Uploading $name…", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            try {
                val filePart = MultipartBody.Part.createFormData("file", name, tmp.asRequestBody(mime.toMediaTypeOrNull()))
                val empBody  = employeeId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val keyBody  = uploadDocKey.toRequestBody("text/plain".toMediaTypeOrNull())
                val resp     = api.uploadEmpDocument(filePart, empBody, keyBody)
                Toast.makeText(ctx, resp.body()?.message ?: if (resp.isSuccessful) "Uploaded!" else "Upload failed", Toast.LENGTH_SHORT).show()
                if (resp.isSuccessful) loadDocuments()
            } catch (e: Exception) {
                Toast.makeText(ctx, "Upload error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally { tmp.delete() }
        }
    }

    private fun viewDocument(doc: EmpDocument) {
        val ctx   = context ?: return
        val token = session.getToken() ?: return
        Toast.makeText(ctx, "Opening…", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            try {
                val client = okhttp3.OkHttpClient.Builder()
                    .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                    .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS).build()
                val req = okhttp3.Request.Builder()
                    .url("${AndroidMain.BASE_URL.trimEnd('/')}/emp-documents/file/${doc.id}")
                    .addHeader("Authorization", "Bearer $token").build()
                val response = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) { client.newCall(req).execute() }
                if (!response.isSuccessful) { Toast.makeText(ctx, "Error ${response.code}", Toast.LENGTH_SHORT).show(); return@launch }
                val bytes = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) { response.body?.bytes() } ?: return@launch
                val safe  = (doc.originalName ?: "doc_${doc.id}").replace(Regex("[^a-zA-Z0-9._-]"), "_")
                val file  = File(ctx.cacheDir, safe)
                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) { file.writeBytes(bytes) }
                val uri   = androidx.core.content.FileProvider.getUriForFile(ctx, "${ctx.packageName}.fileprovider", file)
                ctx.startActivity(Intent.createChooser(Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, doc.mimeType ?: "application/octet-stream")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
                }, "Open with"))
            } catch (e: Exception) { Toast.makeText(ctx, "Error: ${e.message}", Toast.LENGTH_LONG).show() }
        }
    }

    private fun confirmDelete(doc: EmpDocument) {
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Delete Document")
            .setMessage("Delete \"${doc.originalName ?: doc.docKey}\"? This cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    try {
                        val resp = api.deleteEmpDocument(doc.id)
                        if (resp.isSuccessful) {
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                            loadDocuments()
                        } else {
                            val errBody = resp.errorBody()?.string() ?: "Unknown error"
                            Toast.makeText(context, "Failed: $errBody", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null).show()
            .getButton(android.app.AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.parseColor("#DC2626"))
    }
}
