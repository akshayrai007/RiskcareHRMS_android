package com.riskcare.app.ui.attendance
import com.riskcare.app.AndroidMain

import android.app.AlertDialog
import android.util.Log
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.*
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PatternItem
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayoutMediator
import com.riskcare.app.R
import com.riskcare.app.service.LocationTrackingService
import com.riskcare.app.domain.model.StopReason
import com.riskcare.app.domain.model.TrackingPrefs
import com.riskcare.app.domain.usecase.TrackingManager
import com.riskcare.app.permission.PermissionManager
import com.riskcare.app.data.api.RetrofitClient
import com.riskcare.app.data.models.*
import com.riskcare.app.databinding.FragmentAttendanceBinding
import com.riskcare.app.databinding.FragmentAttendanceTodayBinding
import com.riskcare.app.ui.leave.LeaveListAdapter
import com.riskcare.app.utils.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import kotlin.coroutines.resume

// ── HOST FRAGMENT ─────────────────────────────────────────────────────────────
class AttendanceFragment : Fragment() {
    private var _b: FragmentAttendanceBinding? = null
    private val binding get() = _b!!

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentAttendanceBinding.inflate(i, c, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = AttendancePagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = when (pos) { 0 -> "Today"; 1 -> "History"; 2 -> "Regularize"; else -> "OD/WFH" }
        }.attach()
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}

class AttendancePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
    override fun getItemCount() = 4
    override fun createFragment(pos: Int) = when (pos) {
        0    -> AttendanceTodayFragment()
        1    -> AttendanceHistoryFragment()
        2    -> RegularizationFragment()
        else -> ODWFHFragment()
    }
}

// ── TODAY TAB ─────────────────────────────────────────────────────────────────
class AttendanceTodayFragment : Fragment(), OnMapReadyCallback {
    private var _b: FragmentAttendanceTodayBinding? = null
    private val binding get() = _b!!
    private var hasPunchedIn  = false
    private var hasPunchedOut = false
    private var isPunching    = false

    // ── Google Maps geofence ──────────────────────────────────────────────────
    private var googleMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null
    private var mapReady = false
    private var geofenceLocations: List<MyGeofenceLocation> = emptyList()
    private var lastEmployeeLat: Double? = null
    private var lastEmployeeLng: Double? = null
    private var isInsideBuffer = false
    private var empDotMarker: Marker? = null
    private var cameraInitialized = false
    private var isUserTouching = false
    private var userHasInteracted = false  // stays true forever once user touches map
    private var lastValidatTime = 0L
    private var boundaryDrawn = false
    private var lastDistrictRing: List<LatLng>? = null

    // Map overlays — cleared on each redraw
    private val mapPolygons  = mutableListOf<com.google.android.gms.maps.model.Polygon>()
    private val mapPolylines = mutableListOf<com.google.android.gms.maps.model.Polyline>()
    private val mapCircles   = mutableListOf<com.google.android.gms.maps.model.Circle>()
    private val mapMarkers   = mutableListOf<Marker>()

    private val mapLocationHandler = Handler(Looper.getMainLooper())
    private val mapLocationRunnable = object : Runnable {
        override fun run() {
            if (_b != null && !isUserTouching) {
                lifecycleScope.launch { refreshEmployeeDotOnMap() }
            }
            mapLocationHandler.postDelayed(this, AndroidMain.TRACK_INTERVAL_MOVING_MS)
        }
    }

    private lateinit var permissionManager: PermissionManager

    private val gpsSettingsLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            lifecycleScope.launch { executePunch() }
        } else {
            if (_b != null) updatePunchButtons()
            toast("GPS is required to punch in. Please enable Location and try again.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionManager = PermissionManager(this)
    }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentAttendanceTodayBinding.inflate(i, c, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPunchIn.isEnabled  = false
        binding.btnPunchOut.isEnabled = false
        binding.btnPunchIn.text = "Loading..."
        loadToday()
        binding.btnPunchIn.setOnClickListener  { doPunch() }
        binding.btnPunchOut.setOnClickListener { doPunch() }
        binding.btnApplyOD.setOnClickListener  { ODWFHBottomSheet("OD")  { loadToday() }.show(childFragmentManager, "od") }
        binding.btnApplyWFH.setOnClickListener { ODWFHBottomSheet("WFH") { loadToday() }.show(childFragmentManager, "wfh") }

        // Init Google Maps fragment into map_container — tiles and office circles load
        // immediately without location permission. Only the blue dot needs permission.
        initMapFragment()
        loadGeofenceMap()

        permissionManager.checkAndRequestAll { _ -> /* dot appears via mapLocationRunnable */ }

        setupLateNotice()
    }

    // ── Coming Late — inserted as extra rows below Punch In/Out, appended in
    // code rather than the XML so this doesn't touch the punch-button layout. ──
    private fun setupLateNotice() {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density
        val punchRow = binding.btnPunchOut.parent as? android.view.ViewGroup ?: return
        val mainColumn = punchRow.parent as? android.widget.LinearLayout ?: return
        val insertAt = mainColumn.indexOfChild(punchRow) + 1

        val btnLate = com.google.android.material.button.MaterialButton(
            ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle
        ).apply {
            text = "🕒 Coming Late — Inform Manager & HR"; textSize = 13f
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).also { it.bottomMargin = (12*dp).toInt() }
        }
        mainColumn.addView(btnLate, insertAt)
        btnLate.setOnClickListener { showLateNoticeDialog() }
    }

    private fun showLateNoticeDialog() {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density

        fun fieldBg() = android.graphics.drawable.GradientDrawable().apply {
            setColor(android.graphics.Color.WHITE); cornerRadius = 8*dp
            setStroke((1*dp).toInt(), ctx.getColor(R.color.primary))
        }
        fun label(text: String) = TextView(ctx).apply {
            this.text = text; textSize = 12f; setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ctx.getColor(R.color.text_secondary))
            setPadding(0, (10*dp).toInt(), 0, (4*dp).toInt())
        }

        // ── Same shell as the Raise-a-Ticket dialog: gradient header + white card body ──
        val outer = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            background = android.graphics.drawable.GradientDrawable().apply { setColor(android.graphics.Color.WHITE); cornerRadius = 16*dp }
            clipToOutline = true
        }
        val header = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding((20*dp).toInt(), (18*dp).toInt(), (20*dp).toInt(), (18*dp).toInt())
            background = android.graphics.drawable.GradientDrawable(
                android.graphics.drawable.GradientDrawable.Orientation.TL_BR,
                intArrayOf(ctx.getColor(R.color.primary), ctx.getColor(R.color.accent_red))
            )
        }
        header.addView(TextView(ctx).apply {
            text = "🕒 Coming Late Today"; textSize = 17f; setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(android.graphics.Color.WHITE)
        })
        header.addView(TextView(ctx).apply {
            text = "Your manager and HR will be informed"; textSize = 12f
            setTextColor(android.graphics.Color.WHITE); alpha = 0.9f
            setPadding(0, (2*dp).toInt(), 0, 0)
        })
        outer.addView(header)

        val container = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((20*dp).toInt(), (14*dp).toInt(), (20*dp).toInt(), (16*dp).toInt()) }

        val cal = Calendar.getInstance()
        var expectedTime = String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))
        val tvTime = TextView(ctx).apply {
            text = expectedTime; textSize = 15f; setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ctx.getColor(R.color.text_primary))
            background = fieldBg()
            setPadding((10*dp).toInt(), (12*dp).toInt(), (10*dp).toInt(), (12*dp).toInt())
        }
        tvTime.setOnClickListener {
            TimePickerDialog(ctx, { _, h, m ->
                expectedTime = String.format("%02d:%02d", h, m)
                tvTime.text = expectedTime
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
        val etReason = EditText(ctx).apply {
            hint = "e.g. Doctor's appointment, traffic"; minLines = 2
            setTextColor(ctx.getColor(R.color.text_primary))
            setHintTextColor(ctx.getColor(R.color.text_hint))
            background = fieldBg()
            setPadding((10*dp).toInt(), (10*dp).toInt(), (10*dp).toInt(), (10*dp).toInt())
        }

        container.addView(label("Expected Arrival Time *"))
        container.addView(tvTime)
        container.addView(label("Reason *"))
        container.addView(etReason)

        val btnRow = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .also { it.topMargin = (18*dp).toInt() }
        }
        val btnCancel = com.google.android.material.button.MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply {
            text = "Cancel"; setTextColor(ctx.getColor(R.color.text_secondary))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).also { it.marginEnd = (8*dp).toInt() }
        }
        val btnSubmit = com.google.android.material.button.MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonStyle).apply {
            text = "🕒 Submit"; setBackgroundColor(ctx.getColor(R.color.primary))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        btnRow.addView(btnCancel); btnRow.addView(btnSubmit)
        container.addView(btnRow)
        outer.addView(container)

        val scroll = ScrollView(ctx).apply { addView(outer) }
        val dialog = android.app.AlertDialog.Builder(ctx).setView(scroll).create()
        dialog.window?.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT))

        btnCancel.setOnClickListener { dialog.dismiss() }
        btnSubmit.setOnClickListener {
            val reason = etReason.text.toString().trim()
            if (reason.isEmpty()) { toast("Enter a reason"); return@setOnClickListener }
            lifecycleScope.launch {
                try {
                    val res = RetrofitClient.instance.createLateNotice(CreateLateNoticeRequest(expectedTime, reason))
                    if (res.isSuccessful && res.body()?.success == true) {
                        toast(res.body()?.message ?: "Submitted")
                        dialog.dismiss()
                    } else toast(res.body()?.message ?: "Failed to submit")
                } catch (_: Exception) { toast("Network error") }
            }
        }
        dialog.show()
    }

    // ── Map init ──────────────────────────────────────────────────────────────

    private fun initMapFragment() {
        // Card must be VISIBLE before the map fragment is committed —
        // a SupportMapFragment inside a GONE view never gets a render surface.
        if (_b != null) binding.cardGeofenceMap.visibility = View.VISIBLE

        mapFragment = SupportMapFragment.newInstance(
            GoogleMapOptions()
                .mapType(GoogleMap.MAP_TYPE_HYBRID)
                .zoomControlsEnabled(false)
                .compassEnabled(false)
                .liteMode(false)
        )
        childFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment!!)
            .commitNow()
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        mapReady  = true
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        map.uiSettings.isZoomControlsEnabled   = true   // show +/- buttons
        map.uiSettings.isZoomGesturesEnabled   = true   // pinch to zoom
        map.uiSettings.isScrollGesturesEnabled = true
        map.uiSettings.isTiltGesturesEnabled   = false
        map.uiSettings.isRotateGesturesEnabled = true
        map.setMinZoomPreference(5f)
        map.setMaxZoomPreference(21f)
        map.setOnCameraMoveStartedListener { reason ->
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                isUserTouching = true
                userHasInteracted = true  // permanent — never auto-zoom after this
            }
        }
        map.setOnCameraIdleListener { isUserTouching = false }

        // Draw any pending overlays that arrived before map was ready
        if (geofenceLocations.isNotEmpty() || lastDistrictRing != null) {
            drawGeofenceOnMap()
        }
        lastEmployeeLat?.let { lat ->
            lastEmployeeLng?.let { lng -> placeEmployeeDot(lat, lng) }
        }
    }

    private var employeeBufferRule: com.riskcare.app.data.models.BufferRuleResponse? = null

    private fun loadGeofenceMap() {
        lifecycleScope.launch {
            val session = com.riskcare.app.utils.SessionManager(requireContext())
            val empId = session.getEmployee()?.id ?: 0
            repeat(2) { attempt ->
                if (employeeBufferRule == null) {
                    try {
                        val ruleRes = RetrofitClient.instance.getBufferRule(empId)
                        employeeBufferRule = ruleRes.body()?.data
                    } catch (_: Exception) {
                        if (attempt == 0) delay(1500)
                    }
                }
            }
            try {
                val res = RetrofitClient.instance.getMyGeofenceLocations()
                geofenceLocations = res.body()?.data ?: emptyList()
            } catch (_: Exception) { geofenceLocations = emptyList() }
            if (_b == null) return@launch
            // cardGeofenceMap is already VISIBLE (set in initMapFragment)
            setupMapUiLabels()
            boundaryDrawn = false
            mapLocationHandler.removeCallbacks(mapLocationRunnable)
            mapLocationHandler.post(mapLocationRunnable)
        }
    }

    private fun setupMapUiLabels() {
        if (_b == null) return
        val rule = employeeBufferRule
        when (rule?.ruleType) {
            "district" -> {
                binding.tvGeofenceStatus.text = "📍 District Boundary"
                binding.tvGeofenceStatus.setTextColor(Color.parseColor("#FF9800"))
                binding.tvGeofenceDistance.text = if (rule.district != null && rule.state != null)
                    "Checking if you are in ${rule.district}, ${rule.state}…"
                else "Waiting for location signal..."
            }
            "state" -> {
                binding.tvGeofenceStatus.text = "🗺️ State Boundary"
                binding.tvGeofenceStatus.setTextColor(Color.parseColor("#FF9800"))
                binding.tvGeofenceDistance.text = if (rule.state != null)
                    "Checking if you are in ${rule.state}…"
                else "Waiting for location signal..."
            }
            "universal" -> {
                binding.tvGeofenceStatus.text = "✅ Universal access"
                binding.tvGeofenceStatus.setTextColor(Color.parseColor("#4CAF50"))
                binding.tvGeofenceDistance.text = "Punch allowed from anywhere"
            }
            else -> {
                binding.tvGeofenceStatus.text = "📡 Getting GPS..."
                binding.tvGeofenceStatus.setTextColor(Color.parseColor("#FF9800"))
                binding.tvGeofenceDistance.text = "Waiting for location signal..."
            }
        }
        if (mapReady) drawGeofenceOnMap()
    }

    // ── Geofence drawing ──────────────────────────────────────────────────────

    private fun clearMapOverlays() {
        mapPolygons.forEach  { it.remove() }; mapPolygons.clear()
        mapPolylines.forEach { it.remove() }; mapPolylines.clear()
        mapCircles.forEach   { it.remove() }; mapCircles.clear()
        mapMarkers.forEach   { it.remove() }; mapMarkers.clear()
        // empDotMarker is kept — only geofence decorations are cleared
    }

    private fun drawGeofenceOnMap() {
        if (boundaryDrawn) return
        val map = googleMap ?: return
        val rule = employeeBufferRule
        when (rule?.ruleType) {
            "office"   -> { drawOfficeCircles(map); boundaryDrawn = true }
            "district" -> if (rule.state != null && rule.district != null) {
                lifecycleScope.launch { drawDistrictPolygon(map, rule.state, rule.district); boundaryDrawn = true }
            }
            "state"    -> if (rule.state != null) {
                lifecycleScope.launch { drawStatePolygons(map, rule.state); boundaryDrawn = true }
            }
            "universal" -> {
                drawOfficeCircles(map); boundaryDrawn = true
                if (_b != null) {
                    binding.tvGeofenceStatus.text = "✅ Universal access"
                    binding.tvGeofenceStatus.setTextColor(Color.parseColor("#4CAF50"))
                    binding.tvGeofenceDistance.text = "Punch allowed from anywhere"
                    updatePunchButtons()
                }
            }
            null -> {
                // Rule not yet loaded — show office circles as fallback, don't mark drawn
                // so when rule loads the correct boundary can still render
                drawOfficeCircles(map)
                if (_b != null) {
                    binding.tvGeofenceStatus.text = "📡 Loading boundary…"
                    binding.tvGeofenceStatus.setTextColor(Color.parseColor("#FF9800"))
                    binding.tvGeofenceDistance.text = "Waiting for location data"
                }
                lifecycleScope.launch {
                    delay(AndroidMain.PUNCH_TOAST_DELAY_MS)
                    val retryRule = employeeBufferRule
                    if (retryRule != null && _b != null) {
                        clearMapOverlays(); boundaryDrawn = false
                        drawGeofenceOnMap()
                    }
                }
            }
            else -> { drawOfficeCircles(map); boundaryDrawn = true }
        }
    }

    private fun drawOfficeCircles(map: GoogleMap) {
        clearMapOverlays()
        val userLat = lastEmployeeLat
        val userLng = lastEmployeeLng
        var closestDist = Double.MAX_VALUE
        var closestEdgeLat = 0.0
        var closestEdgeLng = 0.0

        for (loc in geofenceLocations) {
            if (loc.radiusMeters > AndroidMain.GEOFENCE_MAX_RADIUS_M) continue
            val center = LatLng(loc.latitude, loc.longitude)

            mapCircles += map.addCircle(
                CircleOptions()
                    .center(center)
                    .radius(loc.radiusMeters.toDouble())
                    .fillColor(Color.argb(38, 236, 72, 153))     // #ec4899 @ 15%
                    .strokeColor(Color.argb(255, 29, 78, 216))   // #1d4ed8
                    .strokeWidth(5f)
            )

            if (userLat != null && userLng != null) {
                val r = FloatArray(1)
                Location.distanceBetween(userLat, userLng, loc.latitude, loc.longitude, r)
                val distToCenter = r[0].toDouble()
                val distToEdge   = Math.abs(distToCenter - loc.radiusMeters)
                if (distToEdge < closestDist) {
                    closestDist = distToEdge
                    val bearing = Math.toDegrees(Math.atan2(
                        (userLng - loc.longitude) * Math.cos(Math.toRadians(loc.latitude)),
                        userLat - loc.latitude
                    ))
                    val edgeLatRad = Math.toRadians(loc.latitude) +
                            (loc.radiusMeters / 6371000.0) * Math.cos(Math.toRadians(bearing))
                    val edgeLngRad = Math.toRadians(loc.longitude) +
                            (loc.radiusMeters / 6371000.0) * Math.sin(Math.toRadians(bearing)) /
                            Math.cos(Math.toRadians(loc.latitude))
                    closestEdgeLat = Math.toDegrees(edgeLatRad)
                    closestEdgeLng = Math.toDegrees(edgeLngRad)
                }
            }
        }

        if (userLat != null && userLng != null && closestDist < Double.MAX_VALUE) {
            val pattern: List<PatternItem> = listOf(Dash(20f), Gap(15f))
            mapPolylines += map.addPolyline(
                PolylineOptions()
                    .add(LatLng(userLat, userLng), LatLng(closestEdgeLat, closestEdgeLng))
                    .color(Color.argb(200, 29, 78, 216))
                    .width(4f)
                    .pattern(pattern)
            )
            if (_b != null) {
                val distText = if (closestDist < 1000) "${closestDist.toInt()} m from boundary"
                else "${"%.1f".format(closestDist / 1000)} km from boundary"
                binding.tvGeofenceDistance.text = "📏 $distText"
            }
        }

        zoomToOfficeBuffer()
    }

    private suspend fun drawDistrictPolygon(map: GoogleMap, state: String, district: String) {
        try {
            val res  = RetrofitClient.instance.getBoundary(state, district)
            val data = res.body()?.data ?: return
            if (data.coordinates.isEmpty()) return
            val ring = data.coordinates[0].map { pt -> LatLng(pt[1], pt[0]) }
            lastDistrictRing = ring

            withContext(kotlinx.coroutines.Dispatchers.Main) {
                if (_b == null || googleMap == null) return@withContext
                clearMapOverlays()

                mapPolygons += map.addPolygon(
                    PolygonOptions()
                        .addAll(ring)
                        .fillColor(Color.argb(89, 236, 72, 153))    // #ec4899 @ 35%
                        .strokeColor(Color.argb(255, 29, 78, 216))  // #1d4ed8
                        .strokeWidth(8f)
                )

                val userLat = lastEmployeeLat
                val userLng = lastEmployeeLng
                val boundsBuilder = LatLngBounds.Builder()
                ring.forEach { boundsBuilder.include(it) }

                if (userLat != null && userLng != null) {
                    boundsBuilder.include(LatLng(userLat, userLng))
                    val nearestPt = ring.minByOrNull { pt ->
                        Math.sqrt(Math.pow(pt.latitude - userLat, 2.0) + Math.pow(pt.longitude - userLng, 2.0))
                    }
                    if (nearestPt != null) {
                        val pattern: List<PatternItem> = listOf(Dash(20f), Gap(15f))
                        mapPolylines += map.addPolyline(
                            PolylineOptions()
                                .add(LatLng(userLat, userLng), nearestPt)
                                .color(Color.argb(200, 29, 78, 216))
                                .width(4f)
                                .pattern(pattern)
                        )
                    }
                }
                try {
                    if (!userHasInteracted) map.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 60))
                } catch (_: Exception) {}
            }
        } catch (_: Exception) {}
    }

    private suspend fun drawStatePolygons(map: GoogleMap, state: String) {
        try {
            val res      = RetrofitClient.instance.getStateBoundary(state)
            val districts = res.body()?.data ?: return
            val boundsBuilder = LatLngBounds.Builder()
            var hasPoints = false

            withContext(kotlinx.coroutines.Dispatchers.Main) {
                if (_b == null || googleMap == null) return@withContext
                clearMapOverlays()

                for (d in districts) {
                    if (d.coordinates.isEmpty()) continue
                    val ring = d.coordinates[0].map { pt -> LatLng(pt[1], pt[0]) }
                    if (ring.isEmpty()) continue
                    mapPolygons += map.addPolygon(
                        PolygonOptions()
                            .addAll(ring)
                            .fillColor(Color.argb(25, 236, 72, 153))    // #ec4899 @ ~10%
                            .strokeColor(Color.argb(210, 29, 78, 216))  // #1d4ed8
                            .strokeWidth(3f)
                    )
                    ring.forEach { boundsBuilder.include(it); hasPoints = true }
                }
                if (hasPoints) {
                    try {
                        if (!userHasInteracted) map.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 60))
                    } catch (_: Exception) {}
                }
            }
        } catch (_: Exception) {}
    }

    // ── Employee dot ──────────────────────────────────────────────────────────

    private fun createBlueDotBitmap(sizePx: Int): Bitmap {
        val bmp    = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val cx = sizePx / 2f; val cy = sizePx / 2f
        canvas.drawCircle(cx, cy, sizePx / 2f,
            Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.argb(60, 33, 150, 243); style = Paint.Style.FILL })
        canvas.drawCircle(cx, cy, sizePx / 2f * 0.62f,
            Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; style = Paint.Style.FILL })
        canvas.drawCircle(cx, cy, sizePx / 2f * 0.44f,
            Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.argb(255, 33, 150, 243); style = Paint.Style.FILL })
        return bmp
    }

    private fun placeEmployeeDot(lat: Double, lng: Double) {
        val map = googleMap ?: return
        val pos = LatLng(lat, lng)
        if (empDotMarker == null) {
            val bmp = createBlueDotBitmap(52)
            empDotMarker = map.addMarker(
                MarkerOptions()
                    .position(pos)
                    .title("You")
                    .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                    .anchor(0.5f, 0.5f)
            )
        } else {
            empDotMarker!!.position = pos
        }
    }

    private fun zoomToOfficeBuffer() {
        val map = googleMap ?: return
        if (userHasInteracted) return  // user is in control — never override their zoom
        if (geofenceLocations.isEmpty()) return
        val target = if (lastEmployeeLat != null && lastEmployeeLng != null) {
            geofenceLocations.minByOrNull { loc ->
                val r = FloatArray(1)
                Location.distanceBetween(lastEmployeeLat!!, lastEmployeeLng!!, loc.latitude, loc.longitude, r)
                r[0]
            } ?: geofenceLocations[0]
        } else geofenceLocations[0]

        val radiusM = target.radiusMeters.toDouble().coerceAtLeast(50.0)
        val cosLat  = Math.cos(Math.toRadians(target.latitude))
        val zoom    = (Math.log(156543.0 * cosLat / (radiusM * 2.0)) / Math.log(2.0))
            .coerceIn(15.0, 20.0).toFloat()
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(target.latitude, target.longitude), zoom),
            600, null
        )
    }

    private suspend fun refreshEmployeeDotOnMap() {
        val map = googleMap ?: return
        if (isUserTouching) return
        try {
            val loc = getLocation() ?: return
            val empLat = loc.latitude; val empLng = loc.longitude
            lastEmployeeLat = empLat; lastEmployeeLng = empLng

            withContext(kotlinx.coroutines.Dispatchers.Main) {
                if (_b == null || googleMap == null) return@withContext
                placeEmployeeDot(empLat, empLng)

                // Refresh dotted line with updated user position
                if (boundaryDrawn) {
                    mapPolylines.forEach { it.remove() }; mapPolylines.clear()
                    val rule = employeeBufferRule
                    when (rule?.ruleType) {
                        "office", null -> {
                            var closestDist = Double.MAX_VALUE
                            var ceLat = 0.0; var ceLng = 0.0
                            for (loc2 in geofenceLocations) {
                                if (loc2.radiusMeters > AndroidMain.GEOFENCE_MAX_RADIUS_M) continue
                                val r = FloatArray(1)
                                Location.distanceBetween(empLat, empLng, loc2.latitude, loc2.longitude, r)
                                val distToEdge = Math.abs(r[0].toDouble() - loc2.radiusMeters)
                                if (distToEdge < closestDist) {
                                    closestDist = distToEdge
                                    val bearing = Math.toDegrees(Math.atan2(
                                        (empLng - loc2.longitude) * Math.cos(Math.toRadians(loc2.latitude)),
                                        empLat - loc2.latitude))
                                    ceLat = Math.toDegrees(Math.toRadians(loc2.latitude) + (loc2.radiusMeters / 6371000.0) * Math.cos(Math.toRadians(bearing)))
                                    ceLng = Math.toDegrees(Math.toRadians(loc2.longitude) + (loc2.radiusMeters / 6371000.0) * Math.sin(Math.toRadians(bearing)) / Math.cos(Math.toRadians(loc2.latitude)))
                                }
                            }
                            if (closestDist < Double.MAX_VALUE) {
                                mapPolylines += map.addPolyline(PolylineOptions()
                                    .add(LatLng(empLat, empLng), LatLng(ceLat, ceLng))
                                    .color(Color.argb(200, 29, 78, 216)).width(4f)
                                    .pattern(listOf(Dash(20f), Gap(15f))))
                            }
                        }
                        "district" -> {
                            val ring2 = lastDistrictRing
                            if (ring2 != null) {
                                val nearestPt = ring2.minByOrNull { pt ->
                                    Math.sqrt(Math.pow(pt.latitude - empLat, 2.0) + Math.pow(pt.longitude - empLng, 2.0))
                                }
                                if (nearestPt != null) {
                                    mapPolylines += map.addPolyline(PolylineOptions()
                                        .add(LatLng(empLat, empLng), nearestPt)
                                        .color(Color.argb(200, 29, 78, 216)).width(4f)
                                        .pattern(listOf(Dash(20f), Gap(15f))))
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }

            if (_b == null) return
            val now  = System.currentTimeMillis()
            val rule = employeeBufferRule
            if (rule?.ruleType == "universal") {
                binding.tvGeofenceStatus.text = "✅ Universal access"
                binding.tvGeofenceStatus.setTextColor(Color.parseColor("#4CAF50"))
                binding.tvGeofenceDistance.text = if (geofenceLocations.isNotEmpty()) buildDistanceLabel(empLat, empLng) else "Punch allowed from anywhere"
                isInsideBuffer = true
                if (!hasPunchedOut) updatePunchButtons()
                if (!cameraInitialized) { zoomToOfficeBuffer(); cameraInitialized = true }
                return
            }
            if (now - lastValidatTime < AndroidMain.GEOFENCE_VALIDATE_COOLDOWN_MS) return
            lastValidatTime = now
            try {
                val bufRes  = RetrofitClient.instance.validateBuffer(ValidateBufferRequest(latitude = empLat, longitude = empLng))
                val bufData = bufRes.body(); val inside = bufData?.valid ?: true
                isInsideBuffer = inside
                if (inside) {
                    binding.tvGeofenceStatus.text = "✅ Inside boundary"
                    binding.tvGeofenceStatus.setTextColor(Color.BLACK)
                    binding.tvGeofenceDistance.text = bufData?.message ?: when (rule?.ruleType) {
                        "district" -> "Within ${rule.district}, ${rule.state}"
                        "state"    -> "Within ${rule.state}"
                        "office"   -> buildDistanceLabel(empLat, empLng)
                        else       -> "Boundary verified"
                    }
                    if (!hasPunchedOut) updatePunchButtons()
                } else {
                    binding.tvGeofenceStatus.text = "⛔ Outside boundary"
                    binding.tvGeofenceStatus.setTextColor(Color.parseColor("#F44336"))
                    binding.tvGeofenceDistance.text = bufData?.message ?: "Outside your assigned boundary"
                    if (!hasPunchedIn) {
                        binding.btnPunchIn.isEnabled  = false
                        binding.btnPunchOut.isEnabled = false
                        binding.btnPunchIn.text = "Outside Boundary"
                    }
                }
            } catch (_: Exception) {
                isInsideBuffer = true
                binding.tvGeofenceStatus.text = "⚠ Validation unavailable"
                binding.tvGeofenceStatus.setTextColor(Color.parseColor("#FF9800"))
                binding.tvGeofenceDistance.text = "Could not verify boundary"
                if (!hasPunchedOut) updatePunchButtons()
            }
            if (!cameraInitialized && !userHasInteracted) {
                val rule2 = employeeBufferRule
                if ((rule2?.ruleType == "district" || rule2?.ruleType == "state") && lastDistrictRing != null) {
                    val ring3 = lastDistrictRing!!
                    val bb = LatLngBounds.Builder()
                    ring3.forEach { bb.include(it) }
                    bb.include(LatLng(empLat, empLng))
                    try { googleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bb.build(), 60)) } catch (_: Exception) {}
                } else {
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(empLat, empLng), 15f))
                }
                cameraInitialized = true
            }
        } catch (_: Exception) {}
    }

    // ── Distance helpers ──────────────────────────────────────────────────────

    // Calculate minimum distance (metres) from user point to nearest edge of district polygon.
    // Uses point-to-segment projection — same algorithm as the web frontend.
    private fun calcDistanceToDistrictBoundary(empLat: Double, empLng: Double): Float? {
        val ring = lastDistrictRing ?: return null
        if (ring.size < 2) return null
        var minDist = Float.MAX_VALUE
        for (i in ring.indices) {
            val a = ring[i]; val b = ring[(i + 1) % ring.size]
            val ax = a.longitude; val ay = a.latitude
            val bx = b.longitude; val by = b.latitude
            val px = empLng;      val py = empLat
            val dx = bx - ax;    val dy = by - ay
            val lenSq = dx * dx + dy * dy
            val t = if (lenSq == 0.0) 0.0 else ((px - ax) * dx + (py - ay) * dy) / lenSq
            val clampedT = t.coerceIn(0.0, 1.0)
            val nearLat = ay + clampedT * dy; val nearLng = ax + clampedT * dx
            val result = FloatArray(1)
            Location.distanceBetween(empLat, empLng, nearLat, nearLng, result)
            if (result[0] < minDist) minDist = result[0]
        }
        return if (minDist == Float.MAX_VALUE) null else minDist
    }

    private fun buildDistanceLabel(empLat: Double, empLng: Double): String {
        val rule = employeeBufferRule
        when (rule?.ruleType) {
            "universal" -> return "Punch allowed from anywhere"
            "state"     -> return if (rule.state != null) "🗺️ Boundary: ${rule.state}" else "State boundary check"
            "district"  -> {
                val dist = calcDistanceToDistrictBoundary(empLat, empLng)
                return if (dist != null) {
                    val distText = if (dist < 1000) "${dist.toInt()} m from boundary" else "${"%.1f".format(dist / 1000)} km from boundary"
                    if (rule.district != null && rule.state != null) "📏 $distText • ${rule.district}, ${rule.state}" else "📏 $distText"
                } else if (rule.district != null && rule.state != null) "📍 Boundary: ${rule.district}, ${rule.state}"
                else "District boundary check"
            }
        }
        val relevant = geofenceLocations.filter { it.radiusMeters <= AndroidMain.GEOFENCE_MAX_RADIUS_M }
        if (relevant.isEmpty()) {
            val nearest = geofenceLocations.minByOrNull { loc ->
                val r = FloatArray(1); Location.distanceBetween(empLat, empLng, loc.latitude, loc.longitude, r); r[0]
            } ?: return "Universal access — punch allowed from anywhere"
            val r = FloatArray(1); Location.distanceBetween(empLat, empLng, nearest.latitude, nearest.longitude, r)
            val km = (r[0] / 1000).toInt()
            return if (r[0] <= nearest.radiusMeters) "✅ Within ${nearest.name}" else "📍 ${nearest.name} (~${km}km away)"
        }
        val sorted = relevant.sortedBy { loc ->
            val r = FloatArray(1); Location.distanceBetween(empLat, empLng, loc.latitude, loc.longitude, r); r[0]
        }
        return sorted.joinToString("  •  ") { loc ->
            val r = FloatArray(1); Location.distanceBetween(empLat, empLng, loc.latitude, loc.longitude, r)
            val dist = r[0].toInt()
            val shortName = loc.name.replace("Corporate Office – ", "").replace("Krishi Care HQ – ", "").trim()
            if (dist <= loc.radiusMeters) "✅ $shortName (${dist}m)" else "⛔ $shortName (${dist}m away, need ${loc.radiusMeters}m)"
        }
    }

    // ── Punch logic ───────────────────────────────────────────────────────────

    fun loadToday() {
        lifecycleScope.launch {
            val todayIST = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).also { it.timeZone = TimeZone.getTimeZone(AndroidMain.TIMEZONE) }.format(Date())
            val prefs = requireContext().getSharedPreferences(AndroidMain.PREFS_ATT_CACHE, android.content.Context.MODE_PRIVATE)
            val cachedDate    = prefs.getString("today_date", null)
            val cachedPunchIn = prefs.getString("punch_in", "").takeIf { !it.isNullOrBlank() }
            val cachedPunchOut = prefs.getString("punch_out", "").takeIf { !it.isNullOrBlank() }
            val cachedHours   = prefs.getFloat("working_hours", -1f)
            val cachedStatus  = prefs.getString("status", null)
            if (cachedDate == todayIST && cachedPunchIn != null) {
                hasPunchedIn  = true; hasPunchedOut = cachedPunchOut != null
                binding.tvPunchInTime.text  = AttendanceRecord.parseTime(cachedPunchIn)  ?: "--:--"
                binding.tvPunchOutTime.text = if (cachedPunchOut != null) AttendanceRecord.parseTime(cachedPunchOut) ?: "--:--" else "--:--"
                if (cachedPunchIn != null && cachedPunchOut != null && cachedHours > 0) {
                    val m = (cachedHours * 60).toInt(); binding.tvWorkingHours.text = "${m / 60}h ${m % 60}m"
                } else binding.tvWorkingHours.text = "--"
                if (cachedStatus != null) {
                    binding.tvStatus.text = cachedStatus.replaceFirstChar { it.uppercase() }
                    binding.tvStatus.setTextColor(getStatusColor(requireContext(), cachedStatus))
                }
                updatePunchButtons()
            }
            var foundFromAttendance = false
            try {
                val istCal = Calendar.getInstance(java.util.TimeZone.getTimeZone(AndroidMain.TIMEZONE))
                val attRes = RetrofitClient.instance.getAttendance(month = istCal.get(Calendar.MONTH) + 1, year = istCal.get(Calendar.YEAR))
                if (attRes.isSuccessful) {
                    val att = attRes.body()?.data?.firstOrNull { it.dateStr == todayIST || it.date == todayIST || it.date?.startsWith(todayIST) == true }
                    if (att != null) { updateUI(att); foundFromAttendance = true }
                }
            } catch (_: Exception) {}
            if (!foundFromAttendance) {
                try {
                    val res = RetrofitClient.instance.getDashboard()
                    if (res.isSuccessful && res.body()?.success == true) updateUI(res.body()!!.data?.todayAttendance)
                } catch (_: Exception) {}
            }
            if (_b != null) updatePunchButtons()
        }
    }

    private fun updateUI(att: AttendanceRecord?) {
        if (_b == null) return
        if (att != null) {
            hasPunchedIn  = att.punchIn  != null; hasPunchedOut = att.punchOut != null
            binding.tvPunchInTime.text  = att.displayPunchIn  ?: "--:--"
            binding.tvPunchOutTime.text = att.displayPunchOut ?: "--:--"
            val todayIST = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).also { it.timeZone = TimeZone.getTimeZone(AndroidMain.TIMEZONE) }.format(Date())
            requireContext().getSharedPreferences(AndroidMain.PREFS_ATT_CACHE, android.content.Context.MODE_PRIVATE).edit().apply {
                putString("today_date", todayIST); putString("punch_in", att.punchIn ?: ""); putString("punch_out", att.punchOut ?: "")
                putFloat("working_hours", att.workingHours?.toFloat() ?: -1f); putString("status", att.status); apply()
            }
            binding.tvWorkingHours.text = if (att.punchIn != null && att.punchOut != null) {
                att.workingHours?.let { hrs -> val m = (hrs * 60).toInt(); "${m / 60}h ${m % 60}m" } ?: "--"
            } else "--"
            val st = att.status ?: "unknown"
            binding.tvStatus.text = when (st) { "missing_punch_out" -> "Missing Punch Out"; else -> st.replaceFirstChar { it.uppercase() }.replace("_", " ") }
            binding.tvStatus.setTextColor(getStatusColor(requireContext(), st))
        } else {
            hasPunchedIn = false; hasPunchedOut = false
            binding.tvPunchInTime.text  = "--:--"; binding.tvPunchOutTime.text = "--:--"; binding.tvWorkingHours.text = "--"
            binding.tvStatus.text = "Not Marked"; binding.tvStatus.setTextColor(requireContext().getColor(R.color.text_secondary))
            requireContext().getSharedPreferences(AndroidMain.PREFS_ATT_CACHE, android.content.Context.MODE_PRIVATE).edit()
                .remove("punch_in").remove("punch_out").remove("working_hours").remove("status").remove("today_date").apply()
        }
        updatePunchButtons()
        // Only start/stop if state actually changed — updateUI can be called multiple times
        if (hasPunchedIn && !hasPunchedOut && !LocationTrackingService.isRunning(requireContext()))
            LocationTrackingService.start(requireContext(), isOd = false)
        else if (hasPunchedOut) LocationTrackingService.stop(requireContext(), StopReason.PUNCH_OUT)
    }

    private fun updatePunchButtons() {
        if (_b == null) return
        when {
            hasPunchedOut -> {
                binding.btnPunchIn.isEnabled  = false; binding.btnPunchOut.isEnabled = false
                binding.btnPunchIn.text  = "✅ Punched In"; binding.btnPunchOut.text = "✅ Punched Out"
                binding.btnPunchIn.backgroundTintList  = android.content.res.ColorStateList.valueOf(requireContext().getColor(R.color.text_secondary))
                binding.btnPunchOut.backgroundTintList = android.content.res.ColorStateList.valueOf(requireContext().getColor(R.color.text_secondary))
            }
            hasPunchedIn -> {
                binding.btnPunchIn.isEnabled  = false; binding.btnPunchOut.isEnabled = true
                binding.btnPunchIn.text  = "✅ Punched In"; binding.btnPunchOut.text = "Punch Out"
                binding.btnPunchIn.backgroundTintList  = android.content.res.ColorStateList.valueOf(requireContext().getColor(R.color.text_secondary))
                binding.btnPunchOut.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#C62828"))
            }
            else -> {
                binding.btnPunchIn.isEnabled  = true; binding.btnPunchOut.isEnabled = false
                binding.btnPunchIn.text  = "Punch In"; binding.btnPunchOut.text = "Punch Out"
                binding.btnPunchIn.backgroundTintList  = android.content.res.ColorStateList.valueOf(Color.parseColor("#2E7D45"))
                binding.btnPunchOut.backgroundTintList = android.content.res.ColorStateList.valueOf(requireContext().getColor(R.color.text_secondary))
            }
        }
    }

    private fun doPunch() {
        if (isPunching) { toast("⏳ Already processing punch, please wait..."); return }
        permissionManager.checkAndRequestAll { granted ->
            if (granted) checkGpsAndPunch()
        }
    }

    private fun checkGpsAndPunch() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, AndroidMain.GEOFENCE_LOCATION_REQUEST_MS).build()
        val settingsRequest = LocationSettingsRequest.Builder().addLocationRequest(locationRequest).setAlwaysShow(true).build()
        val settingsClient: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        settingsClient.checkLocationSettings(settingsRequest)
            .addOnSuccessListener { lifecycleScope.launch { executePunch() } }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try { gpsSettingsLauncher.launch(IntentSenderRequest.Builder(exception.resolution).build()) }
                    catch (_: Exception) { lifecycleScope.launch { executePunch() } }
                } else lifecycleScope.launch { executePunch() }
            }
    }

    private suspend fun executePunch(noLocation: Boolean = false) {
        if (isPunching) return
        isPunching = true
        try {
            binding.btnPunchIn.isEnabled = false; binding.btnPunchOut.isEnabled = false
            binding.btnPunchIn.text = "Getting location..."
            var lat: Double? = null; var lng: Double? = null; var locLabel = "Office"; var geofenceValid: Boolean? = null
            if (!noLocation) {
                try {
                    val loc = getLocation(); lat = loc?.latitude; lng = loc?.longitude
                    locLabel = if (lat != null) "GPS: ${String.format("%.4f", lat)},${String.format("%.4f", lng)}" else "Manual"
                } catch (_: Exception) { locLabel = "Manual" }
            }
            if (lat != null && lng != null) {
                binding.btnPunchIn.text = "Validating location..."
                try {
                    val bufRes  = RetrofitClient.instance.validateBuffer(ValidateBufferRequest(latitude = lat, longitude = lng))
                    val bufData = bufRes.body(); geofenceValid = bufData?.valid
                    if (geofenceValid == false) {
                        val msg = bufData?.message ?: "Outside your assigned boundary"
                        if (_b != null) updatePunchButtons()
                        toast("❌ $msg"); return
                    }
                } catch (_: Exception) { geofenceValid = null }
            }
            binding.btnPunchIn.text = "Please wait..."
            val istTimeSdf = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).also { it.timeZone = java.util.TimeZone.getTimeZone(AndroidMain.TIMEZONE) }
            val istDateSdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).also { it.timeZone = java.util.TimeZone.getTimeZone(AndroidMain.TIMEZONE) }
            val now = java.util.Date(); val punchTime = istTimeSdf.format(now); val punchDate = istDateSdf.format(now)
            var lastError = "Failed"
            repeat(3) { attempt ->
                try {
                    val req = if (!hasPunchedIn) PunchRequest(lat = lat, lng = lng, punchInLocation = locLabel, punchOutLocation = null, punchTime = punchTime, punchDate = punchDate, source = "mobile", geofenceValid = geofenceValid)
                    else PunchRequest(lat = lat, lng = lng, punchInLocation = null, punchOutLocation = locLabel, punchTime = punchTime, punchDate = punchDate, source = "mobile", geofenceValid = geofenceValid)
                    val res = if (!hasPunchedIn) RetrofitClient.instance.punchIn(req) else RetrofitClient.instance.punchOut(req)
                    if (res.isSuccessful && res.body()?.success == true) {
                        toast("✅ ${res.body()?.message ?: if (!hasPunchedIn) "Punched In" else "Punched Out"}")
                        if (!hasPunchedIn) {
                            val todayStr = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                            val isOnOD = try {
                                val odRes = RetrofitClient.instance.getMyODRequests(status = "approved")
                                (odRes.body()?.data ?: emptyList()).any { od -> (od.fromDate?.take(10) ?: "") <= todayStr && (od.toDate?.take(10) ?: "") >= todayStr }
                            } catch (_: Exception) { false }
                            requireContext().getSharedPreferences(TrackingPrefs.PREFS_NAME, android.content.Context.MODE_PRIVATE).edit().putBoolean(TrackingPrefs.KEY_IS_OD, isOnOD).apply()
                            // Log GPS point at punch-in via offline queue
                            // isPunchIn=true bypasses all gates so the anchor
                            // point is always saved even with poor GPS indoors.
                            // If no internet — saved to Room DB, synced later.
                            if (lat != null && lng != null) {
                                try {
                                    val repo = com.riskcare.app.data.repository.LocationRepository(requireContext())
                                    val uploaded = repo.saveAndSync(
                                        lat        = lat!!,
                                        lng        = lng!!,
                                        accuracy   = 0f,
                                        isOd       = isOnOD,
                                        isPunchIn  = true
                                    )
                                    Log.d("AttendanceFragment", "✅ Punch-in point saved via queue (uploaded=$uploaded): $lat,$lng")
                                } catch (e: Exception) {
                                    Log.w("AttendanceFragment", "Punch-in point queue failed: ${e.message}")
                                }
                            }
                            // Movement/location tracking removed — no corresponding
                            // feature on the web app. Punch-in records a one-time
                            // lat/lng inside the geofence, nothing ongoing.
                        } else {
                            LocationTrackingService.stop(requireContext(), StopReason.PUNCH_OUT)
                        }
                        if (hasPunchedIn) requireContext().getSharedPreferences(AndroidMain.PREFS_LEAVE_CACHE, android.content.Context.MODE_PRIVATE).edit().putBoolean("balance_stale", true).apply()
                        loadToday(); return
                    } else {
                        lastError = res.body()?.message ?: res.errorBody()?.string()?.take(120) ?: "Server error ${res.code()}"
                        if (attempt < 2) { binding.btnPunchIn.text = "Waking server… (${attempt + 1}/3)"; delay(AndroidMain.PUNCH_RETRY_DELAY_MS) }
                    }
                } catch (e: Exception) {
                    lastError = "Network error: ${e.message}"
                    if (attempt < 2) { binding.btnPunchIn.text = "Retrying… (${attempt + 1}/3)"; delay(AndroidMain.PUNCH_RETRY_DELAY_MS) }
                }
            }
            toast("❌ $lastError")
            if (_b != null) updatePunchButtons()
        } finally { isPunching = false }
    }

    private suspend fun getLocation(): Location? {
        val client = LocationServices.getFusedLocationProviderClient(requireActivity())
        return try {
            val last = suspendCancellableCoroutine<Location?> { cont ->
                try { client.lastLocation.addOnSuccessListener { cont.resume(it, null) }.addOnFailureListener { cont.resume(null, null) } }
                catch (_: SecurityException) { cont.resume(null, null) }
            }
            if (last != null) return last
            val balanced = withTimeoutOrNull(8_000L) {
                suspendCancellableCoroutine<Location?> { cont ->
                    val cts = CancellationTokenSource(); cont.invokeOnCancellation { cts.cancel() }
                    try { client.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, cts.token).addOnSuccessListener { cont.resume(it, null) }.addOnFailureListener { cont.resume(null, null) } }
                    catch (_: SecurityException) { cont.resume(null, null) }
                }
            }
            if (balanced != null) return balanced
            withTimeoutOrNull(12_000L) {
                suspendCancellableCoroutine { cont ->
                    val cts = CancellationTokenSource(); cont.invokeOnCancellation { cts.cancel() }
                    try { client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token).addOnSuccessListener { cont.resume(it, null) }.addOnFailureListener { cont.resume(null, null) } }
                    catch (_: SecurityException) { cont.resume(null, null) }
                }
            }
        } catch (_: Exception) { null }
    }

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    override fun onResume() {
        super.onResume()
        if (mapReady) {
            mapLocationHandler.removeCallbacks(mapLocationRunnable)
            mapLocationHandler.post(mapLocationRunnable)
        }
        checkAndRequestAllPermissions()
    }

    // Foreground location only — needed so the geofence map can show the
    // employee's own dot. No background permission, no battery-optimization
    // prompt, no tracking setup: Riskcare has no continuous tracking feature.
    private fun checkAndRequestAllPermissions() {
        if (!permissionManager.hasForegroundPermission()) {
            permissionManager.checkAndRequestAll {}
        }
    }

    override fun onPause() {
        mapLocationHandler.removeCallbacks(mapLocationRunnable)
        super.onPause()
    }

    override fun onDestroyView() {
        mapLocationHandler.removeCallbacks(mapLocationRunnable)
        clearMapOverlays()
        empDotMarker?.remove(); empDotMarker = null
        googleMap = null; mapReady = false; cameraInitialized = false; boundaryDrawn = false; userHasInteracted = false
        super.onDestroyView(); _b = null
    }

    private fun showOnsiteTrackingPrompt(isOd: Boolean) {
        // Always start tracking for ALL employees after punch-in.
        // Onsite employees use foreground-only tracking if background permission not granted.
        // Removing the "Skip" option which caused 0-point tracking for onsite employees.
        val ctx = requireContext()
        LocationTrackingService.start(ctx, isOd = isOd)
        LocationTrackingService.requestBatteryExemption(ctx)
        showTrackingSetupDialogIfNeeded()
        android.util.Log.d("AttendanceFragment", "Onsite employee: tracking started")
    }

    private fun showTrackingSetupDialogIfNeeded() {
        val prefs = requireContext().getSharedPreferences(AndroidMain.PREFS_TRACK, android.content.Context.MODE_PRIVATE)
        if (prefs.getBoolean("tracking_setup_shown", false)) return
        prefs.edit().putBoolean("tracking_setup_shown", true).apply()
        val brand = android.os.Build.MANUFACTURER.lowercase()
        val (title, steps) = AndroidMain.getBatteryOptSteps(brand)
        val brandIntent = LocationTrackingService.getBrandBatteryIntent()
        android.app.AlertDialog.Builder(requireContext()).setTitle(title).setMessage(steps)
            .setPositiveButton(if (brandIntent != null) "Open Settings" else "Got it") { _, _ ->
                if (brandIntent != null) { try { startActivity(brandIntent) } catch (_: Exception) { LocationTrackingService.requestBatteryExemption(requireContext()) } }
            }.setNegativeButton("Later", null).setCancelable(false).show()
    }
}


// ── CALENDAR TAB — ported from KrishiHR-Android's exact layout: title, nav row,
// Present/Absent/Leave stat chips, a white rounded card with circular day
// badges, and a two-row legend. Riskcare red in place of KrishiHR green. ──────
class AttendanceCalendarFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density
        val outer = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(ctx.getColor(R.color.background)) }
        outer.addView(TextView(ctx).apply {
            text = "📅 Attendance Calendar"; textSize = 19f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(ctx.getColor(R.color.text_primary))
            setPadding((16*dp).toInt(),(18*dp).toInt(),(16*dp).toInt(),(10*dp).toInt())
        })
        val sv = androidx.core.widget.NestedScrollView(ctx).apply { setBackgroundColor(ctx.getColor(R.color.background)) }
        outer.addView(sv, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f))
        val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((10*dp).toInt(),(10*dp).toInt(),(10*dp).toInt(),(80*dp).toInt()) }
        sv.addView(root)
        val cal = Calendar.getInstance(); var displayYear = cal.get(Calendar.YEAR); var displayMonth = cal.get(Calendar.MONTH)
        val tvMonthYear = TextView(ctx).apply { textSize = 20f; setTypeface(null, android.graphics.Typeface.BOLD); gravity = android.view.Gravity.CENTER; setTextColor(ctx.getColor(R.color.text_primary)) }
        val calCard = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            background = android.graphics.drawable.GradientDrawable().apply { setColor(android.graphics.Color.WHITE); cornerRadius = 14*dp; setStroke((1*dp).toInt(), 0xFFe5e7eb.toInt()) }
            setPadding((4*dp).toInt(),0,(4*dp).toInt(),(6*dp).toInt())
        }
        val calGrid = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL }
        val statsRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; setPadding(0,(4*dp).toInt(),0,(12*dp).toInt()) }
        var records: List<AttendanceRecord> = emptyList(); var holidays: List<com.riskcare.app.data.models.Holiday> = emptyList()

        fun stColor(st: String?): Int { val s = st?.lowercase() ?: ""; return when {
            s == "present" || s == "regularized" || s == "od" || s == "wfh" -> ctx.getColor(R.color.primary)
            s == "absent" -> 0xFFdc2626.toInt()
            s == "late" -> 0xFFeab308.toInt()
            s == "missing_punch_out" -> 0xFFec4899.toInt()
            s == "half-day" || s == "half_day" -> 0xFF2563eb.toInt()
            s == "on-leave" || s == "leave" -> 0xFF2563eb.toInt()
            s == "lwp" -> 0xFF6b7280.toInt()
            s.startsWith("h-") -> 0xFF2563eb.toInt()
            s == "holiday" -> 0xFFd97706.toInt(); s == "weekend" -> 0xFF9ca3af.toInt(); else -> 0xFFd1d5db.toInt()
        }}
        fun tint(color: Int, whiteAmount: Float): Int {
            val r = (android.graphics.Color.red(color) * (1 - whiteAmount) + 255 * whiteAmount).toInt()
            val g = (android.graphics.Color.green(color) * (1 - whiteAmount) + 255 * whiteAmount).toInt()
            val b = (android.graphics.Color.blue(color) * (1 - whiteAmount) + 255 * whiteAmount).toInt()
            return android.graphics.Color.rgb(r, g, b)
        }
        fun stBadge(st: String?): String { val s = st?.lowercase() ?: ""; return when {
            s == "present" || s == "regularized" || s == "od" || s == "wfh" -> "✓"
            s == "absent" -> "A"
            s == "late" -> "L"
            s == "missing_punch_out" -> "!"
            s == "lwp" -> "O"
            s == "on-leave" || s == "leave" || s == "half-day" || s == "half_day" || s.startsWith("h-") -> "E"
            else -> ""
        }}
        fun dayCircle(size: Float, fill: Int, border: Int?, symbol: String, symbolColor: Int, dateText: String, dateColor: Int, showDate: Boolean = true): LinearLayout {
            return LinearLayout(ctx).apply {
                orientation = LinearLayout.VERTICAL; gravity = android.view.Gravity.CENTER
                background = android.graphics.drawable.GradientDrawable().apply {
                    shape = android.graphics.drawable.GradientDrawable.OVAL; setColor(fill)
                    if (border != null) setStroke((1.5f*dp).toInt(), border)
                }
                layoutParams = LinearLayout.LayoutParams((size*dp).toInt(),(size*dp).toInt())
                if (symbol.isNotEmpty()) addView(TextView(ctx).apply { text = symbol; textSize = if (showDate) 10f else 12f; setTextColor(symbolColor); setTypeface(null, android.graphics.Typeface.BOLD); gravity = android.view.Gravity.CENTER })
                if (showDate) addView(TextView(ctx).apply { text = dateText; textSize = if (symbol.isNotEmpty()) 9f else 12f; setTextColor(dateColor); setTypeface(null, android.graphics.Typeface.BOLD); gravity = android.view.Gravity.CENTER })
            }
        }
        fun buildCalendar() {
            calGrid.removeAllViews(); statsRow.removeAllViews()
            val monthCal = Calendar.getInstance().also { it.set(displayYear, displayMonth, 1) }
            tvMonthYear.text = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(monthCal.time)
            val sessionMgr = com.riskcare.app.utils.SessionManager(requireContext()); val satPolicy = sessionMgr.getEmployee()?.saturdayPolicy ?: "2nd_4th_off"
            val daysInMonth = monthCal.getActualMaximum(Calendar.DAY_OF_MONTH); val firstDow = monthCal.get(Calendar.DAY_OF_WEEK) - 1
            val headerRow = LinearLayout(ctx).apply {
                orientation = LinearLayout.HORIZONTAL
                background = android.graphics.drawable.GradientDrawable().apply {
                    setColor(0xFFf8fafc.toInt()); cornerRadii = floatArrayOf(14*dp,14*dp,14*dp,14*dp,0f,0f,0f,0f)
                }
            }
            listOf("Sun","Mon","Tue","Wed","Thu","Fri","Sat").forEachIndexed { idx, d ->
                headerRow.addView(TextView(ctx).apply {
                    text = d; textSize = 13f; gravity = android.view.Gravity.CENTER
                    setPadding(0,(12*dp).toInt(),0,(12*dp).toInt())
                    setTextColor(when (idx) { 0 -> 0xFFdc2626.toInt(); 6 -> ctx.getColor(R.color.primary); else -> 0xFF1e293b.toInt() })
                    setTypeface(null, android.graphics.Typeface.BOLD); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                })
            }
            calGrid.addView(headerRow)
            val recMap = records.associate { r -> (r.displayDate ?: r.date?.take(10) ?: "") to r }
            val holMap = holidays.associate { h -> h.date to h.name }
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            var presentCount = 0; var absentCount = 0; var leaveCount = 0
            var dayOfMonth = 1
            for (week in 0..5) {
                if (dayOfMonth > daysInMonth) break
                val weekRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL }
                for (dow in 0..6) {
                    val cellDate = if (week == 0 && dow < firstDow || dayOfMonth > daysInMonth) null else dayOfMonth++
                    val dateStr = if (cellDate != null) String.format("%04d-%02d-%02d", displayYear, displayMonth+1, cellDate) else null
                    val rec = recMap[dateStr]; val holName = holMap[dateStr]; val isToday = dateStr == today; val isSunday = dow == 0; val isSaturday = dow == 6
                    val satNum = if (isSaturday && cellDate != null) { var count = 0; for (d in 1..cellDate) { val tmp = Calendar.getInstance(); tmp.set(displayYear, displayMonth, d); if (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) count++ }; count } else 0
                    val is2nd4thSat = isSaturday && satPolicy == "2nd_4th_off" && (satNum == 2 || satNum == 4)
                    val effectiveStatus = when {
                        holName != null -> "holiday"; isSunday -> "weekend"
                        is2nd4thSat && (rec == null || rec.status == "absent") -> "weekend"
                        rec != null -> rec.status; else -> null
                    }
                    if (effectiveStatus != null) { val es = effectiveStatus.lowercase(); when {
                        es in listOf("present","regularized","late","od","wfh") -> presentCount++
                        es == "absent" -> absentCount++
                        es in listOf("on-leave","leave","lwp") || es.startsWith("h-") -> leaveCount++
                        else -> {}
                    }}
                    val cellH = (58*dp).toInt()
                    val isWeekendCell = effectiveStatus == "weekend"
                    val badge = stBadge(effectiveStatus)
                    val cell = LinearLayout(ctx).apply {
                        orientation = LinearLayout.VERTICAL; gravity = android.view.Gravity.CENTER
                        layoutParams = LinearLayout.LayoutParams(0, cellH, 1f)
                    }
                    if (cellDate == null) {
                        cell.addView(TextView(ctx).apply { text = "–"; textSize = 13f; setTextColor(0xFFcbd5e1.toInt()) })
                    } else if (isToday) {
                        val todayFill = if (badge.isNotEmpty()) stColor(effectiveStatus) else android.graphics.Color.WHITE
                        val todaySymColor = if (badge.isNotEmpty()) android.graphics.Color.WHITE else ctx.getColor(R.color.primary)
                        val todayDateColor = if (badge.isNotEmpty()) android.graphics.Color.WHITE else ctx.getColor(R.color.primary)
                        cell.addView(dayCircle(34f, todayFill, ctx.getColor(R.color.primary), badge, todaySymColor, cellDate.toString(), todayDateColor))
                    } else if (isWeekendCell) {
                        cell.addView(dayCircle(30f, 0xFFfef3c7.toInt(), 0xFFfbbf24.toInt(), "😊", android.graphics.Color.WHITE, cellDate.toString(), 0xFF92400e.toInt()))
                    } else if (badge.isNotEmpty()) {
                        cell.addView(dayCircle(30f, stColor(effectiveStatus), null, badge, android.graphics.Color.WHITE, cellDate.toString(), android.graphics.Color.WHITE))
                    } else if (effectiveStatus == "holiday") {
                        cell.addView(dayCircle(30f, 0xFFfed7aa.toInt(), 0xFFea580c.toInt(), "🎉", android.graphics.Color.WHITE, cellDate.toString(), 0xFF9a3412.toInt()))
                    } else {
                        val dateColor = if (isSunday) 0xFFdc2626.toInt() else 0xFF1e293b.toInt()
                        cell.addView(dayCircle(30f, android.graphics.Color.TRANSPARENT, 0xFFe2e8f0.toInt(), "", dateColor, cellDate.toString(), dateColor))
                    }
                    weekRow.addView(cell)
                }
                calGrid.addView(weekRow)
            }
            fun statCard(icon: String, label: String, count: Int, color: Int) {
                val card = LinearLayout(ctx).apply {
                    orientation = LinearLayout.HORIZONTAL; gravity = android.view.Gravity.CENTER_VERTICAL
                    setPadding((8*dp).toInt(),(8*dp).toInt(),(6*dp).toInt(),(8*dp).toInt())
                    background = android.graphics.drawable.GradientDrawable().apply { cornerRadius = 12*dp; setColor(tint(color, 0.88f)) }
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).also { it.setMargins((2*dp).toInt(),0,(2*dp).toInt(),0) }
                }
                card.addView(TextView(ctx).apply {
                    text = icon; textSize = 12f; setTextColor(android.graphics.Color.WHITE); setTypeface(null, android.graphics.Typeface.BOLD)
                    gravity = android.view.Gravity.CENTER
                    background = android.graphics.drawable.GradientDrawable().apply { shape = android.graphics.drawable.GradientDrawable.OVAL; setColor(color) }
                    layoutParams = LinearLayout.LayoutParams((26*dp).toInt(),(26*dp).toInt()).also { it.marginEnd = (6*dp).toInt() }
                })
                val textCol = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL }
                textCol.addView(TextView(ctx).apply { text = count.toString(); textSize = 16f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(0xFF1e293b.toInt()) })
                textCol.addView(TextView(ctx).apply { text = label; textSize = 9f; setTextColor(0xFF64748b.toInt()) })
                card.addView(textCol)
                statsRow.addView(card)
            }
            statCard("✓", "Present", presentCount, ctx.getColor(R.color.primary))
            statCard("A", "Absent", absentCount, 0xFFdc2626.toInt())
            statCard("E", "Leave", leaveCount, 0xFF2563eb.toInt())
        }
        fun loadData() {
            lifecycleScope.launch {
                try {
                    val attRes = RetrofitClient.instance.getAttendance(displayMonth+1, displayYear); records = if (attRes.isSuccessful) attRes.body()?.data ?: emptyList() else emptyList()
                    val holRes = RetrofitClient.instance.getHolidays(displayYear); holidays = if (holRes.isSuccessful) holRes.body()?.data ?: emptyList() else emptyList()
                    buildCalendar()
                } catch (_: Exception) { buildCalendar() }
            }
        }
        fun navBtn(txt: String, onClick: () -> Unit) = TextView(ctx).apply {
            text = txt; textSize = 16f; setTextColor(0xFF374151.toInt()); gravity = android.view.Gravity.CENTER
            setTypeface(null, android.graphics.Typeface.BOLD)
            background = android.graphics.drawable.GradientDrawable().apply { cornerRadius = 10*dp; setColor(android.graphics.Color.WHITE); setStroke((1*dp).toInt(), 0xFFe2e8f0.toInt()) }
            layoutParams = LinearLayout.LayoutParams((40*dp).toInt(),(40*dp).toInt())
            setOnClickListener { onClick() }
        }
        val navRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; gravity = android.view.Gravity.CENTER_VERTICAL; setPadding(0,0,0,(12*dp).toInt()) }
        val btnPrev = navBtn("‹") { if (displayMonth == 0) { displayMonth = 11; displayYear-- } else displayMonth--; loadData() }
        val btnNext = navBtn("›") { if (displayMonth == 11) { displayMonth = 0; displayYear++ } else displayMonth++; loadData() }
        tvMonthYear.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        navRow.addView(btnPrev); navRow.addView(tvMonthYear); navRow.addView(btnNext)
        calCard.addView(calGrid)
        calCard.addView(View(ctx).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (1*dp).toInt()).also { it.topMargin = (10*dp).toInt(); it.bottomMargin = (10*dp).toInt() }
            setBackgroundColor(0xFFe5e7eb.toInt())
        })
        fun legendRow() = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; gravity = android.view.Gravity.CENTER_VERTICAL; setPadding(0,0,0,(10*dp).toInt()) }
        fun legendItem(row: LinearLayout, badge: String, label: String, color: Int) {
            val item = LinearLayout(ctx).apply {
                orientation = LinearLayout.VERTICAL; gravity = android.view.Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }
            item.addView(dayCircle(22f, color, null, badge, android.graphics.Color.WHITE, "", android.graphics.Color.WHITE, showDate = false))
            item.addView(TextView(ctx).apply { text = label; textSize = 9f; setTextColor(0xFF475569.toInt()); gravity = android.view.Gravity.CENTER; setPadding(0,(3*dp).toInt(),0,0) })
            row.addView(item)
        }
        val lr1 = legendRow(); legendItem(lr1, "✓", "Present", ctx.getColor(R.color.primary)); legendItem(lr1, "A", "Absent", 0xFFdc2626.toInt()); legendItem(lr1, "L", "Late", 0xFFeab308.toInt()); legendItem(lr1, "!", "Miss Punch", 0xFFec4899.toInt())
        val lr2 = legendRow().apply { setPadding(0,0,0,0) }; legendItem(lr2, "E", "Leave", 0xFF2563eb.toInt()); legendItem(lr2, "O", "LWP", 0xFF6b7280.toInt()); legendItem(lr2, "H", "Holiday", 0xFFea580c.toInt()); legendItem(lr2, "–", "Weekend", 0xFFfbbf24.toInt())
        calCard.addView(lr1); calCard.addView(lr2)
        root.addView(navRow); root.addView(statsRow); root.addView(calCard)
        loadData(); return outer
    }
}

// ── HISTORY TAB ───────────────────────────────────────────────────────────────
class AttendanceHistoryFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density; val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(ctx.getColor(R.color.background)) }
        val cal = Calendar.getInstance(); val months = arrayOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"); val years = (cal.get(Calendar.YEAR) downTo cal.get(Calendar.YEAR)-2).map { it.toString() }.toTypedArray()
        val filterRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; setPadding((16*dp).toInt(), (12*dp).toInt(), (16*dp).toInt(), (8*dp).toInt()) }
        val spMonth = Spinner(ctx).apply { adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, months); setSelection(cal.get(Calendar.MONTH)); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f) }
        val spYear  = Spinner(ctx).apply { adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, years); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).also { it.marginStart = (12*dp).toInt() } }
        filterRow.addView(spMonth); filterRow.addView(spYear); root.addView(filterRow)
        val tvSummary = TextView(ctx).apply { setPadding((16*dp).toInt(), 0, (16*dp).toInt(), (8*dp).toInt()); textSize = 12f; setTextColor(ctx.getColor(R.color.text_secondary)) }; root.addView(tvSummary)
        val rv = RecyclerView(ctx).apply { layoutManager = LinearLayoutManager(ctx); setPadding((8*dp).toInt(), 0, (8*dp).toInt(), (80*dp).toInt()); clipToPadding = false }; root.addView(rv, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f))
        fun load(month: Int, year: Int) {
            val apiMonth = month + 1; lifecycleScope.launch {
                try {
                    tvSummary.text = "Loading..."; val res = RetrofitClient.instance.getAttendance(apiMonth, year)
                    if (res.isSuccessful) {
                        val allRecords = res.body()?.data ?: emptyList(); val sessionMgr = com.riskcare.app.utils.SessionManager(requireContext()); val satPolicy = sessionMgr.getEmployee()?.saturdayPolicy ?: "2nd_4th_off"
                        fun satNumOf(y: Int, m: Int, d: Int): Int { var count = 0; for (i in 1..d) { val t = Calendar.getInstance(); t.set(y, m - 1, i); if (t.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) count++ }; return count }
                        val records = allRecords.filter { rec ->
                            val dStr = rec.displayDate ?: rec.date?.take(10) ?: return@filter true; val p = dStr.split("-"); if (p.size < 3) return@filter true
                            val y = p[0].toInt(); val m = p[1].toInt(); val d = p[2].toInt(); val tmp = Calendar.getInstance(); tmp.set(y, m - 1, d); val dow = tmp.get(Calendar.DAY_OF_WEEK)
                            if (dow == Calendar.SUNDAY) return@filter false
                            if (dow == Calendar.SATURDAY && rec.status == "absent" && satPolicy == "2nd_4th_off") { val sn = satNumOf(y, m, d); if (sn == 2 || sn == 4) return@filter false }
                            true
                        }
                        val present = records.count { it.status in listOf("present","late","half-day","regularized","od","wfh") }; val absent = records.count { it.status == "absent" }
                        tvSummary.text = "Present: $present  |  Absent: $absent  |  Total: ${records.size} days"; rv.adapter = AttendanceHistoryAdapter(records.sortedByDescending { it.displayDate ?: it.date })
                    } else tvSummary.text = "Error loading data"
                } catch (_: Exception) { tvSummary.text = "Network error" }
            }
        }
        var isInitialized = false; val selListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(a: AdapterView<*>?, v: View?, pos: Int, id: Long) { if (!isInitialized) return; load(spMonth.selectedItemPosition, years[spYear.selectedItemPosition].toInt()) }
            override fun onNothingSelected(a: AdapterView<*>?) {}
        }
        spMonth.onItemSelectedListener = selListener; spYear.onItemSelectedListener = selListener; isInitialized = true; load(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)); return root
    }
}

class AttendanceHistoryAdapter(private val items: List<AttendanceRecord>) : RecyclerView.Adapter<AttendanceHistoryAdapter.VH>() {
    inner class VH(val card: android.widget.FrameLayout) : RecyclerView.ViewHolder(card)
    override fun onCreateViewHolder(p: ViewGroup, t: Int): VH {
        val ctx = p.context; val dp = ctx.resources.displayMetrics.density; val card = androidx.cardview.widget.CardView(ctx).apply { radius = 12 * dp; cardElevation = 2 * dp; setCardBackgroundColor(ctx.getColor(R.color.surface)); layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT).also { it.setMargins((8*dp).toInt(), (4*dp).toInt(), (8*dp).toInt(), (4*dp).toInt()) } }
        val row = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; gravity = android.view.Gravity.CENTER_VERTICAL; setPadding((12*dp).toInt(), (10*dp).toInt(), (12*dp).toInt(), (10*dp).toInt()) }; card.addView(row); return VH(card)
    }
    override fun getItemCount() = items.size
    override fun onBindViewHolder(h: VH, pos: Int) {
        val it = items[pos]; val ctx = h.card.context; val row = (h.card as androidx.cardview.widget.CardView).getChildAt(0) as LinearLayout; row.removeAllViews()
        fun tv(text: String, size: Float = 13f, bold: Boolean = false, color: Int = ctx.getColor(R.color.text_primary)) = TextView(ctx).apply { this.text = text; textSize = size; if (bold) setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(color) }
        val left = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f) }
        left.addView(tv(it.displayDate?.toDisplayDate() ?: it.date?.toDisplayDate() ?: "—", 13f, true))
        left.addView(tv("In: ${it.displayPunchIn ?: "--"}  Out: ${it.displayPunchOut ?: "--"}", 11f, color = ctx.getColor(R.color.text_secondary)))
        if (it.workingHours != null && it.workingHours > 0) { val m = (it.workingHours * 60).toInt(); left.addView(tv("${m / 60}h ${m % 60}m", 11f, color = ctx.getColor(R.color.text_hint))) }
        val statusPill = TextView(ctx).apply {
            val st = it.status?.lowercase() ?: "unknown"; val label = when(st) { "present" -> "Present"; "absent" -> "Absent"; "late" -> "Late"; "half-day","half_day" -> "Half Day"; "regularized" -> "Regularized"; "od" -> "OD"; "wfh" -> "WFH"; else -> it.status?.replaceFirstChar { c -> c.uppercase() } ?: "—" }
            val bgColor = when(st) { "present","regularized" -> android.graphics.Color.parseColor("#2E7D45"); "od" -> android.graphics.Color.parseColor("#1565C0"); "wfh" -> android.graphics.Color.parseColor("#6A1B9A"); "absent" -> android.graphics.Color.parseColor("#C62828"); "late" -> android.graphics.Color.parseColor("#E65100"); "half-day","half_day" -> android.graphics.Color.parseColor("#1565C0"); else -> android.graphics.Color.parseColor("#90A4AE") }
            text = label; textSize = 11f; setPadding((10*h.card.context.resources.displayMetrics.density).toInt(),(4*h.card.context.resources.displayMetrics.density).toInt(),(10*h.card.context.resources.displayMetrics.density).toInt(),(4*h.card.context.resources.displayMetrics.density).toInt()); setTextColor(android.graphics.Color.WHITE); setTypeface(null, android.graphics.Typeface.BOLD); background = android.graphics.drawable.GradientDrawable().apply { setColor(bgColor); cornerRadius = 20 * h.card.context.resources.displayMetrics.density }
        }; row.addView(left); row.addView(statusPill)
    }
}

// ── REGULARIZE TAB ────────────────────────────────────────────────────────────
class RegularizationFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density; val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(ctx.getColor(R.color.background)); setPadding((16*dp).toInt(), (16*dp).toInt(), (16*dp).toInt(), (16*dp).toInt()) }
        val btnApply = MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonStyle).apply { text = "＋  Request Regularization"; textSize = 14f; setBackgroundColor(ctx.getColor(R.color.primary)); layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (48*dp).toInt()).also { it.bottomMargin = (12*dp).toInt() } }; root.addView(btnApply)
        val rv = RecyclerView(ctx).apply { layoutManager = LinearLayoutManager(ctx) }; root.addView(rv)
        fun load() { lifecycleScope.launch { try { val res = RetrofitClient.instance.getRegularizations(); rv.adapter = RegularizationAdapter(res.body()?.data ?: emptyList()) { load() } } catch (_: Exception) {} } }
        btnApply.setOnClickListener { RegularizeBottomSheet { load() }.show(childFragmentManager, "reg") }; load(); return root
    }
}

class RegularizationAdapter(private val items: List<RegularizationItem>, private val onRefresh: () -> Unit) : RecyclerView.Adapter<RegularizationAdapter.VH>() {
    inner class VH(val root: LinearLayout) : RecyclerView.ViewHolder(root)
    override fun onCreateViewHolder(p: ViewGroup, t: Int): VH {
        val ctx = p.context; val dp = ctx.resources.displayMetrics.density; val card = androidx.cardview.widget.CardView(ctx).apply { radius = 12 * dp; cardElevation = 2 * dp; setCardBackgroundColor(ctx.getColor(R.color.surface)); layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT).also { it.setMargins(0, 0, 0, (8*dp).toInt()) } }
        val ll = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((14*dp).toInt(), (12*dp).toInt(), (14*dp).toInt(), (12*dp).toInt()) }; card.addView(ll); return VH(LinearLayout(ctx).apply { addView(card) })
    }
    override fun getItemCount() = items.size
    override fun onBindViewHolder(h: VH, pos: Int) {
        val it = items[pos]; val ctx = h.root.context; val card = (h.root.getChildAt(0) as androidx.cardview.widget.CardView); val ll = card.getChildAt(0) as LinearLayout; ll.removeAllViews()
        fun tv(t: String, size: Float = 13f, bold: Boolean = false, color: Int = ctx.getColor(R.color.text_primary)) = TextView(ctx).apply { text = t; textSize = size; if (bold) setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(color) }
        ll.addView(tv(it.date?.toDisplayDate() ?: "—", 14f, true)); ll.addView(tv("In: ${it.requestedPunchIn?.take(5) ?: "--"}  Out: ${it.requestedPunchOut?.take(5) ?: "--"}", 12f, color = ctx.getColor(R.color.text_secondary))); ll.addView(tv(it.reason ?: "—", 12f, color = ctx.getColor(R.color.text_hint)))
        if (!it.managerRemarks.isNullOrBlank()) ll.addView(tv("👤 Manager: ${it.managerRemarks}", 11f, color = android.graphics.Color.parseColor("#0369A1")))
        // 2-step chain: Reporting Manager approves first, then HR gives the final approval —
        // so a "pending" request shows which bucket it's currently waiting in.
        val stColor2 = when(it.status?.lowercase()) { "approved" -> android.graphics.Color.parseColor("#2E7D45"); "rejected" -> android.graphics.Color.parseColor("#C62828"); else -> android.graphics.Color.parseColor("#E65100") }
        val stLabel2 = when { it.status?.lowercase() == "approved" -> "Approved"; it.status?.lowercase() == "rejected" -> "Rejected"; it.stage == "hr" -> "Pending HR"; else -> "Pending Manager" }
        ll.addView(TextView(ctx).apply { text = stLabel2; textSize = 11f; setPadding((10*h.root.context.resources.displayMetrics.density).toInt(),(3*h.root.context.resources.displayMetrics.density).toInt(),(10*h.root.context.resources.displayMetrics.density).toInt(),(3*h.root.context.resources.displayMetrics.density).toInt()); setTextColor(android.graphics.Color.WHITE); setTypeface(null, android.graphics.Typeface.BOLD); background = android.graphics.drawable.GradientDrawable().apply { setColor(stColor2); cornerRadius = 20*h.root.context.resources.displayMetrics.density } })
    }
}

// ── OD/WFH TAB ────────────────────────────────────────────────────────────────
class ODWFHFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density; val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(ctx.getColor(R.color.background)); setPadding((16*dp).toInt(), (16*dp).toInt(), (16*dp).toInt(), (16*dp).toInt()) }
        val btnRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL }; val btnOD = MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonStyle).apply { text = "Apply OD"; setBackgroundColor(ctx.getColor(R.color.accent_orange)); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).also { it.marginEnd = (8*dp).toInt() } }; val btnWFH = MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonStyle).apply { text = "Apply WFH"; setBackgroundColor(ctx.getColor(R.color.accent_purple)); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f) }; btnRow.addView(btnOD); btnRow.addView(btnWFH); root.addView(btnRow, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.bottomMargin = (16*dp).toInt() })
        val rvOD = RecyclerView(ctx).apply { layoutManager = LinearLayoutManager(ctx) }; root.addView(TextView(ctx).apply { text = "OD Requests"; textSize = 14f; setTypeface(null, android.graphics.Typeface.BOLD); setPadding(0, 0, 0, (8*dp).toInt()) }); root.addView(rvOD)
        fun load() { lifecycleScope.launch { try { val od = RetrofitClient.instance.getMyODRequests(); val wfh = RetrofitClient.instance.getMyWFHRequests(); rvOD.adapter = LeaveListAdapter((od.body()?.data ?: emptyList()) + (wfh.body()?.data ?: emptyList()), showName = false, showAction = false) {} } catch (_: Exception) {} } }
        btnOD.setOnClickListener { ODWFHBottomSheet("OD") { load() }.show(childFragmentManager, "od") }; btnWFH.setOnClickListener { ODWFHBottomSheet("WFH") { load() }.show(childFragmentManager, "wfh") }; load(); return root
    }
}

// ── BOTTOM SHEETS ─────────────────────────────────────────────────────────────
class RegularizeBottomSheet(private val onSuccess: () -> Unit) : BottomSheetDialogFragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density; val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((20*dp).toInt(), (20*dp).toInt(), (20*dp).toInt(), (20*dp).toInt()) }
        fun tv(t: String) = TextView(ctx).apply { text = t; textSize = 14f; setPadding(0, 0, 0, (4*dp).toInt()) }
        fun et(hint: String) = EditText(ctx).apply { this.hint = hint; setPadding((8*dp).toInt(), (8*dp).toInt(), (8*dp).toInt(), (8*dp).toInt()); setBackgroundColor(ctx.getColor(R.color.background)) }
        root.addView(TextView(ctx).apply { text = "Request Regularization"; textSize = 18f; setTypeface(null, android.graphics.Typeface.BOLD); setPadding(0,0,0,(16*dp).toInt()) })
        val cal = Calendar.getInstance(); var selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time); val tvDate = tv("Date: $selectedDate").also { root.addView(it) }; root.addView(MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply { text = "Select Date"; setOnClickListener { DatePickerDialog(ctx, { _, y, m, d -> selectedDate = String.format("%04d-%02d-%02d", y, m+1, d); tvDate.text = "Date: $selectedDate" }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show() } })
        var punchIn = ""; var punchOut = ""; val tvPI = tv("Punch In: --:--"); root.addView(tvPI); root.addView(MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply { text = "Set Punch In"; setOnClickListener { TimePickerDialog(ctx, { _, h, m -> punchIn = String.format("%02d:%02d", h, m); tvPI.text = "Punch In: $punchIn" }, 9, 0, true).show() } })
        val tvPO = tv("Punch Out: --:--"); root.addView(tvPO); root.addView(MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply { text = "Set Punch Out"; setOnClickListener { TimePickerDialog(ctx, { _, h, m -> punchOut = String.format("%02d:%02d", h, m); tvPO.text = "Punch Out: $punchOut" }, 18, 0, true).show() } })
        val etReason = et("Reason *"); root.addView(etReason); val progress = ProgressBar(ctx); progress.visibility = View.GONE; root.addView(progress)
        root.addView(MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonStyle).apply { text = "Submit"; setBackgroundColor(ctx.getColor(R.color.primary)); setOnClickListener { val reason = etReason.text.toString().trim(); if (reason.isEmpty()) { toast("Enter reason"); return@setOnClickListener }; progress.visibility = View.VISIBLE; isEnabled = false; lifecycleScope.launch { try { val res = RetrofitClient.instance.requestRegularization(RegularizationRequest(selectedDate, punchIn.ifEmpty { null }, punchOut.ifEmpty { null }, reason)); if (res.isSuccessful && res.body()?.success == true) { toast("Request submitted"); onSuccess(); dismiss() } else toast(res.body()?.message ?: "Failed") } catch (_: Exception) { toast("Network error") } finally { progress.visibility = View.GONE; isEnabled = true } } } })
        return root
    }
}

class ODWFHBottomSheet(private val type: String, private val onSuccess: () -> Unit) : BottomSheetDialogFragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density; val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((20*dp).toInt(), (20*dp).toInt(), (20*dp).toInt(), (32*dp).toInt()) }
        root.addView(TextView(ctx).apply { text = "Apply $type"; textSize = 18f; setTypeface(null, android.graphics.Typeface.BOLD); setPadding(0, 0, 0, (16*dp).toInt()) })
        val cal = Calendar.getInstance(); var fromDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time); var toDate = fromDate
        root.addView(TextView(ctx).apply { text = "FROM DATE *"; textSize = 11f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(ctx.getColor(R.color.text_secondary)); setPadding(0, 0, 0, (4*dp).toInt()) })
        root.addView(MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply { text = fromDate; layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (48*dp).toInt()).also { it.bottomMargin = (8*dp).toInt() }; setOnClickListener { DatePickerDialog(ctx, { _, y, m, d -> fromDate = String.format("%04d-%02d-%02d", y, m+1, d); text = fromDate; if (toDate < fromDate) toDate = fromDate }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show() } })
        root.addView(TextView(ctx).apply { text = "TO DATE *"; textSize = 11f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(ctx.getColor(R.color.text_secondary)); setPadding(0, 0, 0, (4*dp).toInt()) })
        root.addView(MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply { text = toDate; layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (48*dp).toInt()).also { it.bottomMargin = (12*dp).toInt() }; setOnClickListener { DatePickerDialog(ctx, { _, y, m, d -> toDate = String.format("%04d-%02d-%02d", y, m+1, d); text = toDate }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show() } })
        var etLocation: EditText? = null
        if (type == "OD") {
            root.addView(TextView(ctx).apply { text = "LOCATION / DESTINATION"; textSize = 11f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(ctx.getColor(R.color.text_secondary)); setPadding(0, 0, 0, (4*dp).toInt()) })
            etLocation = EditText(ctx).apply { hint = "e.g. Client Site, Field Visit, Mumbai..."; setBackgroundColor(ctx.getColor(R.color.background)); setPadding((12*dp).toInt(), (10*dp).toInt(), (12*dp).toInt(), (10*dp).toInt()); layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.bottomMargin = (12*dp).toInt() } }; root.addView(etLocation)
        }
        root.addView(TextView(ctx).apply { text = "REASON *"; textSize = 11f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(ctx.getColor(R.color.text_secondary)); setPadding(0, 0, 0, (4*dp).toInt()) })
        val etReason = EditText(ctx).apply { hint = "Purpose of ${type.lowercase()} duty..."; setBackgroundColor(ctx.getColor(R.color.background)); setPadding((12*dp).toInt(), (10*dp).toInt(), (12*dp).toInt(), (10*dp).toInt()); minLines = 3; gravity = android.view.Gravity.TOP; layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.bottomMargin = (12*dp).toInt() } }; root.addView(etReason)
        val progress = ProgressBar(ctx).apply { visibility = View.GONE }; root.addView(progress)
        root.addView(MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonStyle).apply { text = "Submit $type"; setBackgroundColor(ctx.getColor(if (type == "OD") R.color.accent_orange else R.color.accent_purple)); layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (52*dp).toInt()).also { it.topMargin = (4*dp).toInt() }; setOnClickListener { val reason = etReason.text.toString().trim(); if (reason.isEmpty()) { toast("Enter reason"); return@setOnClickListener }; if (toDate < fromDate) { toast("To date cannot be before From date"); return@setOnClickListener }; progress.visibility = View.VISIBLE; isEnabled = false; lifecycleScope.launch { try { val location = etLocation?.text?.toString()?.trim()?.ifEmpty { "Outdoor Duty" } ?: "Outdoor Duty"; val res = if (type == "OD") RetrofitClient.instance.applyOD(ODRequest(from_date = fromDate, to_date = toDate, reason = reason, location = location)) else RetrofitClient.instance.applyWFH(WFHRequest(from_date = fromDate, to_date = toDate, reason = reason)); if (res.isSuccessful && res.body()?.success == true) { toast("$type applied ✅"); onSuccess(); dismiss() } else toast(res.body()?.message ?: "Failed") } catch (_: Exception) { toast("Network error") } finally { progress.visibility = View.GONE; isEnabled = true } } } })
        return root
    }
}