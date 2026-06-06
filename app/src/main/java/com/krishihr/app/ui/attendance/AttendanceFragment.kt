package com.krishihr.app.ui.attendance
import com.krishihr.app.AndroidMain

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
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayoutMediator
import com.krishihr.app.R
import com.krishihr.app.service.LocationTrackingService
import com.krishihr.app.domain.model.StopReason
import com.krishihr.app.domain.model.TrackingPrefs
import com.krishihr.app.domain.usecase.TrackingManager
import com.krishihr.app.permission.PermissionManager
import com.krishihr.app.data.api.RetrofitClient
import com.krishihr.app.data.models.*
import com.krishihr.app.databinding.FragmentAttendanceBinding
import com.krishihr.app.databinding.FragmentAttendanceTodayBinding
import com.krishihr.app.ui.leave.LeaveListAdapter
import com.krishihr.app.utils.*
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
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
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
            tab.text = when (pos) { 0 -> "Today"; 1 -> "History"; 2 -> "Calendar"; 3 -> "Regulate"; else -> "OD/WFH" }
        }.attach()
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}

class AttendancePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
    override fun getItemCount() = 5
    override fun createFragment(pos: Int) = when (pos) {
        0    -> AttendanceTodayFragment()
        1    -> AttendanceHistoryFragment()
        2    -> AttendanceCalendarFragment()
        3    -> RegularizationFragment()
        else -> ODWFHFragment()
    }
}

// ── TODAY TAB ─────────────────────────────────────────────────────────────────
class AttendanceTodayFragment : Fragment() {
    private var _b: FragmentAttendanceTodayBinding? = null
    private val binding get() = _b!!
    private var hasPunchedIn  = false
    private var hasPunchedOut = false

    // ── Geofence Map (OSMDroid) ───────────────────────────────────────────────
    private var osmMap: MapView? = null
    private var geofenceLocations: List<MyGeofenceLocation> = emptyList()
    private var lastEmployeeLat: Double? = null
    private var lastEmployeeLng: Double? = null
    private var isInsideBuffer = false
    private var empDotMarker: Marker? = null
    private var cameraInitialized = false
    private var isUserTouching = false
    private var lastValidatTime = 0L
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
        Configuration.getInstance().load(
            requireContext(),
            requireContext().getSharedPreferences("osmdroid", android.content.Context.MODE_PRIVATE)
        )
        Configuration.getInstance().userAgentValue = requireContext().packageName
        osmMap = binding.geofenceMapView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPunchIn.isEnabled = false
        binding.btnPunchOut.isEnabled = false
        binding.btnPunchIn.text = "Loading..."
        loadToday()
        binding.btnPunchIn.setOnClickListener { doPunch() }
        binding.btnPunchOut.setOnClickListener { doPunch() }
        binding.btnApplyOD.setOnClickListener  { ODWFHBottomSheet("OD")  { loadToday() }.show(childFragmentManager, "od") }
        binding.btnApplyWFH.setOnClickListener { ODWFHBottomSheet("WFH") { loadToday() }.show(childFragmentManager, "wfh") }

        // FIX: Load map immediately so user sees geofence boundary BEFORE punch-in.
        // Previously map only loaded inside permission callback — if dialog showed,
        // user had no idea if they were inside the boundary or not.
        // Map tiles and office circles do NOT need location permission.
        // Only the blue employee dot needs permission — handled separately below.
        loadGeofenceMap()

        // Request permissions separately — just for the live location dot on map.
        // This does NOT gate the map from showing anymore.
        permissionManager.checkAndRequestAll { granted ->
            // Permission granted — blue dot will appear via mapLocationRunnable
            // No need to call loadGeofenceMap() again here
        }
    }

    private var employeeBufferRule: com.krishihr.app.data.models.BufferRuleResponse? = null

    private fun loadGeofenceMap() {
        lifecycleScope.launch {
            // Fetch buffer rule separately — if it fails, retry once before falling back
            val session = com.krishihr.app.utils.SessionManager(requireContext())
            val empId = session.getEmployee()?.id ?: 0
            repeat(2) { attempt ->
                if (employeeBufferRule == null) {
                    try {
                        val ruleRes = RetrofitClient.instance.getBufferRule(empId)
                        employeeBufferRule = ruleRes.body()?.data
                    } catch (_: Exception) {
                        if (attempt == 0) kotlinx.coroutines.delay(1500) // retry after 1.5s
                    }
                }
            }
            try {
                val res = RetrofitClient.instance.getMyGeofenceLocations()
                geofenceLocations = res.body()?.data ?: emptyList()
            } catch (_: Exception) { geofenceLocations = emptyList() }
            if (_b == null) return@launch
            binding.cardGeofenceMap.visibility = android.view.View.VISIBLE
            setupOsmMap()
        }
    }

    private fun setupOsmMap() {
        val map = osmMap ?: return
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)
        map.minZoomLevel = 5.0
        map.maxZoomLevel = 19.0
        map.isTilesScaledToDpi = true
        map.setLayerType(android.view.View.LAYER_TYPE_SOFTWARE, null)
        // Start at India overview — drawDistrictPolygon / drawStatePolygons will fitBounds after
        map.controller.setZoom(6.0)
        map.controller.setCenter(GeoPoint(22.5, 80.0))
        map.setOnTouchListener { v, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN, android.view.MotionEvent.ACTION_POINTER_DOWN -> isUserTouching = true
                android.view.MotionEvent.ACTION_UP, android.view.MotionEvent.ACTION_POINTER_UP, android.view.MotionEvent.ACTION_CANCEL -> {
                    isUserTouching = false; v.performClick()
                }
            }
            false
        }
        drawGeofenceOnMap()
        if (_b != null) {
            val rule = employeeBufferRule
            when (rule?.ruleType) {
                "district" -> {
                    binding.tvGeofenceStatus.text = "📍 District Boundary"
                    binding.tvGeofenceStatus.setTextColor(android.graphics.Color.parseColor("#FF9800"))
                    binding.tvGeofenceDistance.text = if (rule.district != null && rule.state != null)
                        "Checking if you are in ${rule.district}, ${rule.state}…"
                    else "Waiting for location signal..."
                }
                "state" -> {
                    binding.tvGeofenceStatus.text = "🗺️ State Boundary"
                    binding.tvGeofenceStatus.setTextColor(android.graphics.Color.parseColor("#FF9800"))
                    binding.tvGeofenceDistance.text = if (rule.state != null)
                        "Checking if you are in ${rule.state}…"
                    else "Waiting for location signal..."
                }
                "universal" -> {
                    binding.tvGeofenceStatus.text = "✅ Universal access"
                    binding.tvGeofenceStatus.setTextColor(android.graphics.Color.parseColor("#4CAF50"))
                    binding.tvGeofenceDistance.text = "Punch allowed from anywhere"
                }
                else -> {
                    binding.tvGeofenceStatus.text = "📡 Getting GPS..."
                    binding.tvGeofenceStatus.setTextColor(android.graphics.Color.parseColor("#FF9800"))
                    binding.tvGeofenceDistance.text = "Waiting for location signal..."
                }
            }
        }
        cameraInitialized = false
        boundaryDrawn = false
        mapLocationHandler.removeCallbacks(mapLocationRunnable)
        mapLocationHandler.post(mapLocationRunnable)
    }

    private var boundaryDrawn = false
    private var lastDistrictRing: List<GeoPoint>? = null  // FIX: store for distance calc

    private fun drawGeofenceOnMap() {
        if (boundaryDrawn) return
        val map = osmMap ?: return
        val rule = employeeBufferRule
        when (rule?.ruleType) {
            "office" -> { drawOfficeCircles(map); boundaryDrawn = true }
            "district" -> if (rule.state != null && rule.district != null) {
                lifecycleScope.launch { drawDistrictPolygon(map, rule.state, rule.district); boundaryDrawn = true }
            }
            "state" -> if (rule.state != null) {
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
                // Rule not loaded yet — show office circles as fallback but don't mark as drawn,
                // so when rule loads the correct boundary can still render
                drawOfficeCircles(map)
                // DON'T set boundaryDrawn = true so retry can draw correct boundary
                if (_b != null) {
                    binding.tvGeofenceStatus.text = "📡 Loading boundary…"
                    binding.tvGeofenceStatus.setTextColor(Color.parseColor("#FF9800"))
                    binding.tvGeofenceDistance.text = "Waiting for location data"
                }
                // Retry after 3s in case rule just hadn't loaded yet
                lifecycleScope.launch {
                    kotlinx.coroutines.delay(AndroidMain.PUNCH_TOAST_DELAY_MS)
                    val retryRule = employeeBufferRule
                    if (retryRule != null && _b != null) {
                        map.overlays.removeAll { it is Polygon || it is org.osmdroid.views.overlay.Marker }
                        map.invalidate()
                        drawGeofenceOnMap()
                    }
                }
            }
            else -> { drawOfficeCircles(map); boundaryDrawn = true }
        }
    }

    private fun drawOfficeCircles(map: MapView) {
        var closestDist = Double.MAX_VALUE
        var closestEdgeLat = 0.0
        var closestEdgeLng = 0.0
        val userLat = lastEmployeeLat
        val userLng = lastEmployeeLng

        for (loc in geofenceLocations) {
            if (loc.radiusMeters > AndroidMain.GEOFENCE_MAX_RADIUS_M) continue
            val center = GeoPoint(loc.latitude, loc.longitude)
            val circle = Polygon(map)
            circle.points = Polygon.pointsAsCircle(center, loc.radiusMeters.toDouble())
            // Blue border + pink fill — matches web and district style
            circle.fillPaint.color = Color.argb(38, 236, 72, 153)   // #ec4899 @ 15%
            circle.outlinePaint.color = Color.argb(255, 29, 78, 216) // #1d4ed8 blue
            circle.outlinePaint.strokeWidth = 5f
            circle.setOnClickListener { _, _, _ -> false }
            circle.infoWindow = null
            map.overlays.add(0, circle)

            // Find nearest circle edge for dotted line
            if (userLat != null && userLng != null) {
                val r = FloatArray(1)
                Location.distanceBetween(userLat, userLng, loc.latitude, loc.longitude, r)
                val distToCenter = r[0].toDouble()
                val distToEdge = Math.abs(distToCenter - loc.radiusMeters)
                if (distToEdge < closestDist) {
                    closestDist = distToEdge
                    // Point on circle edge in direction of user from center
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

        // Draw dotted line from user to nearest office circle edge
        if (userLat != null && userLng != null && closestDist < Double.MAX_VALUE) {
            val dottedLine = org.osmdroid.views.overlay.Polyline(map)
            dottedLine.setPoints(listOf(
                GeoPoint(userLat, userLng),
                GeoPoint(closestEdgeLat, closestEdgeLng)
            ))
            dottedLine.outlinePaint.color = Color.argb(200, 29, 78, 216) // #1d4ed8
            dottedLine.outlinePaint.strokeWidth = 4f
            dottedLine.outlinePaint.pathEffect =
                android.graphics.DashPathEffect(floatArrayOf(20f, 15f), 0f)
            map.overlays.add(dottedLine)

            // Update distance label
            if (_b != null) {
                val distText = if (closestDist < 1000)
                    "${closestDist.toInt()} m from boundary"
                else
                    "${"%.1f".format(closestDist / 1000)} km from boundary"
                binding.tvGeofenceDistance.text = "📏 $distText"
            }
        }

        map.post { map.invalidate() }
    }

    private suspend fun drawDistrictPolygon(map: MapView, state: String, district: String) {
        try {
            val res = RetrofitClient.instance.getBoundary(state, district)
            val data = res.body()?.data ?: return
            val coords = data.coordinates
            if (coords.isEmpty()) return
            val ring = coords[0].map { pt -> GeoPoint(pt[1], pt[0]) }
            lastDistrictRing = ring  // FIX: save for distance calculation
            val poly = Polygon(map)
            poly.points = ring
            // FIX: Blue border (#1d4ed8) + transparent pink fill (#ec4899 @ 12% opacity)
            poly.fillPaint.color = Color.argb(89, 236, 72, 153)   // #ec4899 @ 35% — visible at any zoom
            poly.outlinePaint.color = Color.argb(255, 29, 78, 216) // #1d4ed8 solid blue
            poly.outlinePaint.strokeWidth = 8f                      // thick — visible at India zoom
            poly.setOnClickListener { _, _, _ -> false }
            poly.infoWindow = null
            map.post {
                map.overlays.add(0, poly)
                map.invalidate()
                if (ring.isNotEmpty()) {
                    var north = ring.maxOf { it.latitude }
                    var south = ring.minOf { it.latitude }
                    var east  = ring.maxOf { it.longitude }
                    var west  = ring.minOf { it.longitude }
                    // Always include user location in bounds — auto-fits both locations
                    val userLat = lastEmployeeLat
                    val userLng = lastEmployeeLng
                    if (userLat != null && userLng != null) {
                        if (userLat > north) north = userLat
                        if (userLat < south) south = userLat
                        if (userLng > east)  east  = userLng
                        if (userLng < west)  west  = userLng

                        // Draw dotted line from user to nearest boundary edge
                        val nearestPt = ring.minByOrNull { pt ->
                            Math.sqrt(
                                Math.pow(pt.latitude - userLat, 2.0) +
                                        Math.pow(pt.longitude - userLng, 2.0)
                            )
                        }
                        if (nearestPt != null) {
                            val dottedLine = org.osmdroid.views.overlay.Polyline(map)
                            dottedLine.setPoints(listOf(GeoPoint(userLat, userLng), nearestPt))
                            dottedLine.outlinePaint.color = Color.argb(200, 29, 78, 216) // #1d4ed8
                            dottedLine.outlinePaint.strokeWidth = 4f
                            dottedLine.outlinePaint.pathEffect =
                                android.graphics.DashPathEffect(floatArrayOf(20f, 15f), 0f)
                            map.overlays.add(dottedLine)
                        }
                    }
                    val bbox = org.osmdroid.util.BoundingBox(north, east, south, west)
                    // increaseByScale(1.15f) = 15% padding so both points aren't clipped at edge
                    map.zoomToBoundingBox(bbox.increaseByScale(1.15f), true)
                }
            }
        } catch (_: Exception) {}
    }

    private suspend fun drawStatePolygons(map: MapView, state: String) {
        try {
            val res = RetrofitClient.instance.getStateBoundary(state)
            val districts = res.body()?.data ?: return
            var north = -90.0; var south = 90.0; var east = -180.0; var west = 180.0
            for (d in districts) {
                val coords = d.coordinates
                if (coords.isEmpty()) continue
                val ring = coords[0].map { pt -> GeoPoint(pt[1], pt[0]) }
                if (ring.isEmpty()) continue
                val poly = Polygon(map)
                poly.points = ring
                // FIX: Blue border + pink fill (same palette as district)
                poly.fillPaint.color = Color.argb(25, 236, 72, 153)   // #ec4899 @ ~10%
                poly.outlinePaint.color = Color.argb(210, 29, 78, 216) // #1d4ed8 blue
                poly.outlinePaint.strokeWidth = 3f
                poly.setOnClickListener { _, _, _ -> false }
                poly.infoWindow = null
                map.overlays.add(0, poly)
                ring.forEach { p ->
                    if (p.latitude > north) north = p.latitude
                    if (p.latitude < south) south = p.latitude
                    if (p.longitude > east) east = p.longitude
                    if (p.longitude < west) west = p.longitude
                }
            }
            map.invalidate()
            if (north > south) {
                val boundingBox = org.osmdroid.util.BoundingBox(north, east, south, west)
                map.post { map.zoomToBoundingBox(boundingBox.increaseByScale(1.1f), true) }
            }
        } catch (_: Exception) {}
    }

    private fun createBlueDotBitmap(sizePx: Int): Bitmap {
        val bmp    = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val cx     = sizePx / 2f
        val cy     = sizePx / 2f
        val pulse = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.argb(60, 33, 150, 243); style = Paint.Style.FILL }
        canvas.drawCircle(cx, cy, sizePx / 2f, pulse)
        val white = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; style = Paint.Style.FILL }
        canvas.drawCircle(cx, cy, sizePx / 2f * 0.62f, white)
        val blue = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.argb(255, 33, 150, 243); style = Paint.Style.FILL }
        canvas.drawCircle(cx, cy, sizePx / 2f * 0.44f, blue)
        return bmp
    }

    // FIX: Calculate minimum distance (metres) from user point to nearest edge of district polygon.
    // Uses point-to-segment projection — same algorithm as the web frontend.
    private fun calcDistanceToDistrictBoundary(empLat: Double, empLng: Double): Float? {
        val ring = lastDistrictRing ?: return null
        if (ring.size < 2) return null
        var minDist = Float.MAX_VALUE
        for (i in ring.indices) {
            val a = ring[i]
            val b = ring[(i + 1) % ring.size]
            // Distance from point to segment a→b using 2D lat/lng approximation
            val ax = a.longitude; val ay = a.latitude
            val bx = b.longitude; val by = b.latitude
            val px = empLng;       val py = empLat
            val dx = bx - ax;     val dy = by - ay
            val lenSq = dx * dx + dy * dy
            val t = if (lenSq == 0.0) 0.0
            else ((px - ax) * dx + (py - ay) * dy) / lenSq
            val clampedT = t.coerceIn(0.0, 1.0)
            val nearLat = ay + clampedT * dy
            val nearLng = ax + clampedT * dx
            val result = FloatArray(1)
            Location.distanceBetween(empLat, empLng, nearLat, nearLng, result)
            if (result[0] < minDist) minDist = result[0]
        }
        return if (minDist == Float.MAX_VALUE) null else minDist
    }

    private fun buildDistanceLabel(empLat: Double, empLng: Double): String {
        val rule = employeeBufferRule
        // For non-office rules, distance to an office is irrelevant — show boundary info instead
        when (rule?.ruleType) {
            "universal" -> return "Punch allowed from anywhere"
            "state"     -> return if (rule.state != null) "🗺️ Boundary: ${rule.state}" else "State boundary check"
            "district"  -> {
                // FIX: Calculate actual distance from user to nearest polygon edge
                val dist = calcDistanceToDistrictBoundary(empLat, empLng)
                return if (dist != null) {
                    val distText = if (dist < 1000) "${dist.toInt()} m from boundary"
                    else "${"%.1f".format(dist / 1000)} km from boundary"
                    if (rule.district != null && rule.state != null)
                        "📏 $distText • ${rule.district}, ${rule.state}"
                    else "📏 $distText"
                } else if (rule.district != null && rule.state != null)
                    "📍 Boundary: ${rule.district}, ${rule.state}"
                else "District boundary check"
            }
            // "office" and null fall through to distance calculation below
        }
        // Office rule — show actual distance to office(s)
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

    private fun zoomToOfficeBuffer() {
        val map = osmMap ?: return
        if (geofenceLocations.isEmpty()) return
        val target = if (lastEmployeeLat != null && lastEmployeeLng != null) {
            geofenceLocations.minByOrNull { loc ->
                val r = FloatArray(1); Location.distanceBetween(lastEmployeeLat!!, lastEmployeeLng!!, loc.latitude, loc.longitude, r); r[0]
            } ?: geofenceLocations[0]
        } else geofenceLocations[0]
        val center = GeoPoint(target.latitude, target.longitude)
        val radiusM = target.radiusMeters.toDouble().coerceAtLeast(50.0)
        val cosLat  = Math.cos(Math.toRadians(target.latitude))
        val zoom    = (Math.log(156543.0 * cosLat / (radiusM * 2.5)) / Math.log(2.0)).coerceIn(13.0, 19.0)
        map.controller.setZoom(zoom); map.controller.setCenter(center)
    }

    private suspend fun refreshEmployeeDotOnMap() {
        val map = osmMap ?: return
        if (isUserTouching) return
        try {
            val loc = getLocation() ?: return
            val empLat = loc.latitude; val empLng = loc.longitude
            lastEmployeeLat = empLat; lastEmployeeLng = empLng
            val empGeoPoint = GeoPoint(empLat, empLng)
            withContext(kotlinx.coroutines.Dispatchers.Main) {
                if (empDotMarker == null) {
                    val dotBitmap = createBlueDotBitmap(52)
                    val dot = Marker(map); dot.position = empGeoPoint; dot.title = "You"
                    dot.icon = android.graphics.drawable.BitmapDrawable(resources, dotBitmap)
                    dot.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER); dot.setInfoWindow(null)
                    map.overlays.add(dot); empDotMarker = dot
                } else empDotMarker!!.position = empGeoPoint
                map.invalidate()
            }
            if (_b == null) return
            val now = System.currentTimeMillis()
            val rule = employeeBufferRule
            if (rule?.ruleType == "universal") {
                binding.tvGeofenceStatus.text = "✅ Universal access"; binding.tvGeofenceStatus.setTextColor(Color.parseColor("#4CAF50"))
                binding.tvGeofenceDistance.text = if (geofenceLocations.isNotEmpty()) buildDistanceLabel(empLat, empLng) else "Punch allowed from anywhere"
                isInsideBuffer = true
                if (!hasPunchedOut) updatePunchButtons()
                if (!cameraInitialized) { zoomToOfficeBuffer(); cameraInitialized = true }; return
            }
            if (now - lastValidatTime < AndroidMain.GEOFENCE_VALIDATE_COOLDOWN_MS) return
            lastValidatTime = now
            try {
                val bufRes = RetrofitClient.instance.validateBuffer(ValidateBufferRequest(latitude = empLat, longitude = empLng))
                val bufData = bufRes.body(); val inside = bufData?.valid ?: true
                isInsideBuffer = inside
                if (inside) {
                    binding.tvGeofenceStatus.text = "✅ Inside boundary"; binding.tvGeofenceStatus.setTextColor(Color.BLACK)
                    binding.tvGeofenceDistance.text = bufData?.message ?: when (rule?.ruleType) {
                        "district" -> "Within ${rule.district}, ${rule.state}"; "state" -> "Within ${rule.state}"; "office" -> buildDistanceLabel(empLat, empLng)
                        else -> "Boundary verified"
                    }
                    if (!hasPunchedOut) updatePunchButtons()
                } else {
                    binding.tvGeofenceStatus.text = "⛔ Outside boundary"; binding.tvGeofenceStatus.setTextColor(Color.parseColor("#F44336"))
                    binding.tvGeofenceDistance.text = bufData?.message ?: "Outside your assigned boundary"
                    if (!hasPunchedIn) { binding.btnPunchIn.isEnabled = false; binding.btnPunchOut.isEnabled = false; binding.btnPunchIn.text = "Outside Boundary" }
                }
            } catch (_: Exception) {
                isInsideBuffer = true; binding.tvGeofenceStatus.text = "⚠ Validation unavailable"; binding.tvGeofenceStatus.setTextColor(Color.parseColor("#FF9800"))
                binding.tvGeofenceDistance.text = "Could not verify boundary"
                if (!hasPunchedOut) updatePunchButtons()
            }
            if (!cameraInitialized) {
                val rule2 = employeeBufferRule
                if ((rule2?.ruleType == "district" || rule2?.ruleType == "state") && lastDistrictRing != null) {
                    val ring = lastDistrictRing!!
                    var north = ring.maxOf { it.latitude }
                    var south = ring.minOf { it.latitude }
                    var east  = ring.maxOf { it.longitude }
                    var west  = ring.minOf { it.longitude }
                    if (empLat > north) north = empLat
                    if (empLat < south) south = empLat
                    if (empLng > east)  east  = empLng
                    if (empLng < west)  west  = empLng
                    val bbox = org.osmdroid.util.BoundingBox(north, east, south, west)
                    map.post { map.zoomToBoundingBox(bbox.increaseByScale(1.15f), true) }
                } else {
                    map.controller.animateTo(empGeoPoint)
                    map.controller.setZoom(15.0)
                }
                cameraInitialized = true
            }
        } catch (_: Exception) {}
    }

    fun loadToday() {
        lifecycleScope.launch {
            val todayIST = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).also { it.timeZone = TimeZone.getTimeZone("Asia/Kolkata") }.format(Date())
            val prefs = requireContext().getSharedPreferences(AndroidMain.PREFS_ATT_CACHE, android.content.Context.MODE_PRIVATE)
            val cachedDate   = prefs.getString("today_date", null)
            val cachedPunchIn  = prefs.getString("punch_in", "").takeIf { !it.isNullOrBlank() }
            val cachedPunchOut = prefs.getString("punch_out", "").takeIf { !it.isNullOrBlank() }
            val cachedHours  = prefs.getFloat("working_hours", -1f)
            val cachedStatus = prefs.getString("status", null)
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
                val istCal = Calendar.getInstance(java.util.TimeZone.getTimeZone("Asia/Kolkata"))
                val attRes = RetrofitClient.instance.getAttendance(month = istCal.get(Calendar.MONTH) + 1, year  = istCal.get(Calendar.YEAR))
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
            binding.tvPunchInTime.text  = att.displayPunchIn  ?: "--:--"; binding.tvPunchOutTime.text = att.displayPunchOut ?: "--:--"
            val todayIST = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).also { it.timeZone = TimeZone.getTimeZone("Asia/Kolkata") }.format(Date())
            requireContext().getSharedPreferences(AndroidMain.PREFS_ATT_CACHE, android.content.Context.MODE_PRIVATE).edit().apply {
                putString("today_date",  todayIST); putString("punch_in",    att.punchIn ?: ""); putString("punch_out",   att.punchOut ?: "")
                putFloat("working_hours", att.workingHours?.toFloat() ?: -1f); putString("status",      att.status); apply()
            }
            binding.tvWorkingHours.text = if (att.punchIn != null && att.punchOut != null) {
                att.workingHours?.let { hrs -> val totalMinutes = (hrs * 60).toInt(); "${totalMinutes / 60}h ${totalMinutes % 60}m" } ?: "--"
            } else "--"
            val st = att.status ?: "unknown"; binding.tvStatus.text = when (st) { "missing_punch_out" -> "Missing Punch Out"; else -> st.replaceFirstChar { it.uppercase() }.replace("_", " ") }
            binding.tvStatus.setTextColor(getStatusColor(requireContext(), st))
        } else {
            hasPunchedIn = false; hasPunchedOut = false
            binding.tvPunchInTime.text  = "--:--"; binding.tvPunchOutTime.text = "--:--"; binding.tvWorkingHours.text = "--"
            binding.tvStatus.text = "Not Marked"; binding.tvStatus.setTextColor(requireContext().getColor(R.color.text_secondary))
            requireContext().getSharedPreferences(AndroidMain.PREFS_ATT_CACHE, android.content.Context.MODE_PRIVATE).edit().remove("punch_in").remove("punch_out").remove("working_hours").remove("status").remove("today_date").apply()
        }
        updatePunchButtons()
        // FIX: Only start/stop if state actually changed — updateUI is called multiple times
        // (onViewCreated, after punch, after OD/WFH). Each call was restarting the service
        // which caused duplicate pings all at the same timestamp.
        if (hasPunchedIn && !hasPunchedOut && !LocationTrackingService.isRunning(requireContext()))
            LocationTrackingService.start(requireContext(), isOd = false)
        else if (hasPunchedOut) LocationTrackingService.stop(requireContext(), StopReason.PUNCH_OUT)
    }

    private fun updatePunchButtons() {
        if (_b == null) return
        when {
            hasPunchedOut -> {
                binding.btnPunchIn.isEnabled  = false; binding.btnPunchOut.isEnabled = false
                binding.btnPunchIn.text       = "✅ Punched In"; binding.btnPunchOut.text      = "✅ Punched Out"
                binding.btnPunchIn.backgroundTintList  = android.content.res.ColorStateList.valueOf(requireContext().getColor(R.color.text_secondary))
                binding.btnPunchOut.backgroundTintList = android.content.res.ColorStateList.valueOf(requireContext().getColor(R.color.text_secondary))
            }
            hasPunchedIn -> {
                binding.btnPunchIn.isEnabled  = false; binding.btnPunchOut.isEnabled = true
                binding.btnPunchIn.text       = "✅ Punched In"; binding.btnPunchOut.text      = "Punch Out"
                binding.btnPunchIn.backgroundTintList  = android.content.res.ColorStateList.valueOf(requireContext().getColor(R.color.text_secondary))
                binding.btnPunchOut.backgroundTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#C62828"))
            }
            else -> {
                binding.btnPunchIn.isEnabled  = true; binding.btnPunchOut.isEnabled = false
                binding.btnPunchIn.text       = "Punch In"; binding.btnPunchOut.text      = "Punch Out"
                binding.btnPunchIn.backgroundTintList  = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#2E7D45"))
                binding.btnPunchOut.backgroundTintList = android.content.res.ColorStateList.valueOf(requireContext().getColor(R.color.text_secondary))
            }
        }
    }

    private fun doPunch() {
        permissionManager.checkAndRequestAll { granted -> if (granted) checkGpsAndPunch() }
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
                val bufRes = RetrofitClient.instance.validateBuffer(ValidateBufferRequest(latitude = lat, longitude = lng))
                val bufData = bufRes.body(); geofenceValid = bufData?.valid
                if (geofenceValid == false) {
                    val msg = bufData?.message ?: "Outside your assigned boundary"
                    if (_b != null) updatePunchButtons()
                    toast("❌ $msg"); return
                }
            } catch (_: Exception) { geofenceValid = null }
        }
        binding.btnPunchIn.text = "Please wait..."
        val istTimeSdf = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).also { it.timeZone = java.util.TimeZone.getTimeZone("Asia/Kolkata") }
        val istDateSdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).also { it.timeZone = java.util.TimeZone.getTimeZone("Asia/Kolkata") }
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
                            (odRes.body()?.data ?: emptyList()).any { od -> (od.fromDate?.take(10) ?: "") <= todayStr && (od.toDate?.take(10)   ?: "") >= todayStr }
                        } catch (_: Exception) { false }
                        requireContext().getSharedPreferences(TrackingPrefs.PREFS_NAME, android.content.Context.MODE_PRIVATE).edit().putBoolean(TrackingPrefs.KEY_IS_OD, isOnOD).apply()

                        // FIX: Log the GPS point captured during punch as the FIRST movement point.
                        // This guarantees at least 1 point even if the employee never moves.
                        // The service's own first ping comes 30s later — this covers the gap.
                        if (lat != null && lng != null) {
                            try {
                                RetrofitClient.instance.logMovement(
                                    com.krishihr.app.data.models.MovementLogRequest(
                                        lat = lat, lng = lng,
                                        accuracy = 0f,
                                        isOd = isOnOD
                                    )
                                )
                                Log.d("AttendanceFragment", "✅ First movement point logged at punch-in: $lat,$lng")
                            } catch (e: Exception) {
                                Log.w("AttendanceFragment", "First movement log failed (non-fatal): ${e.message}")
                            }
                        }

                        LocationTrackingService.start(requireContext(), isOd = isOnOD); LocationTrackingService.requestBatteryExemption(requireContext()); showTrackingSetupDialogIfNeeded()
                    } else { LocationTrackingService.stop(requireContext(), StopReason.PUNCH_OUT) }
                    if (hasPunchedIn) requireContext().getSharedPreferences(AndroidMain.PREFS_LEAVE_CACHE, android.content.Context.MODE_PRIVATE).edit().putBoolean("balance_stale", true).apply()
                    loadToday(); return
                } else {
                    lastError = res.body()?.message ?: res.errorBody()?.string()?.take(120) ?: "Server error ${res.code()}"
                    if (attempt < 2) { binding.btnPunchIn.text = "Waking server… (${attempt + 1}/3)"; kotlinx.coroutines.delay(AndroidMain.PUNCH_RETRY_DELAY_MS) }
                }
            } catch (e: Exception) {
                lastError = "Network error: ${e.message}"
                if (attempt < 2) { binding.btnPunchIn.text = "Retrying… (${attempt + 1}/3)"; kotlinx.coroutines.delay(AndroidMain.PUNCH_RETRY_DELAY_MS) }
            }
        }
        toast("❌ $lastError")
        if (_b != null) updatePunchButtons()
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

    override fun onResume() {
        super.onResume()
        osmMap?.onResume()
        if (osmMap != null) { mapLocationHandler.removeCallbacks(mapLocationRunnable); mapLocationHandler.post(mapLocationRunnable) }
        checkAndRequestAllPermissions()
    }

    private fun checkAndRequestAllPermissions() {
        val ctx = requireContext(); val pm  = ctx.getSystemService(android.content.Context.POWER_SERVICE) as android.os.PowerManager
        if (!permissionManager.hasForegroundPermission()) { permissionManager.checkAndRequestAll {}; return }
        if (!pm.isIgnoringBatteryOptimizations(ctx.packageName)) {
            try { startActivity(android.content.Intent(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply { data = android.net.Uri.parse("package:${ctx.packageName}") }) }
            catch (_: Exception) {}
            return
        }
        if (!permissionManager.hasBackgroundPermission()) { permissionManager.checkAndRequestAll {}; return }
        val prefs = ctx.getSharedPreferences(AndroidMain.PREFS_TRACK, android.content.Context.MODE_PRIVATE)
        if (!prefs.getBoolean("autostart_asked", false)) { prefs.edit().putBoolean("autostart_asked", true).apply(); showTrackingSetupDialogIfNeeded() }
    }

    override fun onPause() { osmMap?.onPause(); mapLocationHandler.removeCallbacks(mapLocationRunnable); super.onPause() }

    override fun onDestroyView() {
        mapLocationHandler.removeCallbacks(mapLocationRunnable); osmMap?.onDetach(); osmMap = null; empDotMarker = null; cameraInitialized = false
        super.onDestroyView(); _b = null
    }

    private fun showTrackingSetupDialogIfNeeded() {
        val prefs = requireContext().getSharedPreferences(AndroidMain.PREFS_TRACK, android.content.Context.MODE_PRIVATE)
        if (prefs.getBoolean("tracking_setup_shown", false)) return
        prefs.edit().putBoolean("tracking_setup_shown", true).apply()
        val brand = android.os.Build.MANUFACTURER.lowercase()
        val title: String; val steps: String
        when {
            brand.contains("xiaomi") || brand.contains("redmi") || brand.contains("poco") -> { title = "📍 Enable AutoStart for Tracking"; steps = """To ensure location tracking works when app is closed:\n\n1. Open Phone Settings\n2. Go to Apps → KrishiHR → Battery Saver\n3. Select No restrictions\n4. Go back → AutoStart → Enable ✅\n\nWithout this, tracking stops after closing the app.""" }
            brand.contains("oppo") || brand.contains("realme") -> { title = "📍 Allow Background Activity"; steps = """To ensure location tracking works when app is closed:\n\n1. Open Phone Settings\n2. Go to Battery → App Battery Management\n3. Find KrishiHR → Allow background activity ✅\n\nWithout this, tracking stops after closing the app.""" }
            brand.contains("oneplus") -> { title = "📍 Disable Deep Optimization"; steps = """To ensure location tracking works when app is closed:\n\n1. Open Phone Settings\n2. Go to Battery → Battery Optimization\n3. Find KrishiHR → Don't optimize ✅\n\nWithout this, tracking stops after closing the app.""" }
            brand.contains("vivo") -> { title = "📍 Enable Background Location"; steps = """To ensure location tracking works when app is closed:\n\n1. Open iManager app\n2. Go to App Manager → KrishiHR\n3. Enable Background Power Consumption ✅\n\nWithout this, tracking stops after closing the app.""" }
            brand.contains("huawei") || brand.contains("honor") -> { title = "📍 Enable AutoLaunch"; steps = """To ensure location tracking works when app is closed:\n\n1. Open Phone Settings\n2. Go to Apps → KrishiHR → Battery\n3. Enable Auto Launch & Run in background ✅\n\nWithout this, tracking stops after closing the app.""" }
            brand.contains("samsung") -> { title = "📍 Remove Battery Restriction"; steps = """To ensure location tracking works when app is closed:\n\n1. Open Phone Settings\n2. Go to Apps → KrishiHR → Battery\n3. Select Unrestricted ✅\n\nWithout this, Samsung stops tracking after ~10 minutes.""" }
            else -> { title = "📍 Allow Background Location"; steps = """To ensure location tracking works when app is closed:\n\n1. Open Phone Settings\n2. Go to Apps → KrishiHR → Battery\n3. Select Unrestricted or No restrictions ✅\n\nThis ensures your attendance is tracked correctly.""" }
        }
        val brandIntent = LocationTrackingService.getBrandBatteryIntent()
        android.app.AlertDialog.Builder(requireContext()).setTitle(title).setMessage(steps).setPositiveButton(if (brandIntent != null) "Open Settings" else "Got it") { _, _ ->
            if (brandIntent != null) { try { startActivity(brandIntent) } catch (e: Exception) { LocationTrackingService.requestBatteryExemption(requireContext()) } }
        }.setNegativeButton("Later", null).setCancelable(false).show()
    }
}


// ── CALENDAR TAB ──────────────────────────────────────────────────────────────
class AttendanceCalendarFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density
        val sv = androidx.core.widget.NestedScrollView(ctx).apply { setBackgroundColor(ctx.getColor(R.color.background)) }
        val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((8*dp).toInt(),(8*dp).toInt(),(8*dp).toInt(),(80*dp).toInt()) }
        sv.addView(root)
        val cal = Calendar.getInstance(); var displayYear  = cal.get(Calendar.YEAR); var displayMonth = cal.get(Calendar.MONTH)
        val tvMonthYear = TextView(ctx).apply { textSize = 18f; setTypeface(null, android.graphics.Typeface.BOLD); gravity = android.view.Gravity.CENTER; setTextColor(ctx.getColor(R.color.text_primary)) }
        val calGrid = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL }
        var records: List<AttendanceRecord> = emptyList(); var holidays: List<com.krishihr.app.data.models.Holiday> = emptyList()
        fun buildCalendar() {
            calGrid.removeAllViews(); val monthCal = Calendar.getInstance().also { it.set(displayYear, displayMonth, 1) }
            val monthName = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(monthCal.time); tvMonthYear.text = monthName
            val sessionMgr = com.krishihr.app.utils.SessionManager(requireContext()); val satPolicy = sessionMgr.getEmployee()?.saturdayPolicy ?: "2nd_4th_off"
            val daysInMonth = monthCal.getActualMaximum(Calendar.DAY_OF_MONTH); val firstDow    = monthCal.get(Calendar.DAY_OF_WEEK) - 1
            val headerRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL }
            listOf("Su","Mo","Tu","We","Th","Fr","Sa").forEach { d ->
                headerRow.addView(TextView(ctx).apply { text = d; textSize = 11f; gravity = android.view.Gravity.CENTER; setTextColor(ctx.getColor(if (d == "Su" || d == "Sa") R.color.accent_red else R.color.text_secondary)); setTypeface(null, android.graphics.Typeface.BOLD); layoutParams = LinearLayout.LayoutParams(0, (28*dp).toInt(), 1f) })
            }
            calGrid.addView(headerRow)
            val recMap = records.associate { r -> (r.displayDate ?: r.date?.take(10) ?: "") to r }
            val holMap = holidays.associate { h -> h.date to h.name }; val today  = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            var dayOfMonth = 1
            for (week in 0..5) {
                if (dayOfMonth > daysInMonth) break
                val weekRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL }
                for (dow in 0..6) {
                    val cellDate = if (week == 0 && dow < firstDow || dayOfMonth > daysInMonth) null else dayOfMonth++
                    val dateStr = if (cellDate != null) String.format("%04d-%02d-%02d", displayYear, displayMonth+1, cellDate) else null
                    val rec     = recMap[dateStr]; val holName = holMap[dateStr]; val isToday    = dateStr == today; val isSunday   = dow == 0; val isSaturday = dow == 6
                    val satNum = if (isSaturday && cellDate != null) { var count = 0; for (d in 1..cellDate) { val tmp = Calendar.getInstance(); tmp.set(displayYear, displayMonth, d); if (tmp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) count++ }; count } else 0
                    val is2nd4thSat = isSaturday && satPolicy == "2nd_4th_off" && (satNum == 2 || satNum == 4)
                    val cell = LinearLayout(ctx).apply {
                        orientation = LinearLayout.VERTICAL; gravity = android.view.Gravity.TOP or android.view.Gravity.CENTER_HORIZONTAL; layoutParams = LinearLayout.LayoutParams(0, (70*dp).toInt(), 1f).also { it.setMargins(1,1,1,1) }; setPadding((3*dp).toInt(),(4*dp).toInt(),(3*dp).toInt(),(2*dp).toInt())
                        val bgColor = when {
                            cellDate == null -> android.graphics.Color.TRANSPARENT; holName != null  -> android.graphics.Color.parseColor("#FFF8E1"); isToday -> android.graphics.Color.parseColor("#E8F5E9"); is2nd4thSat -> android.graphics.Color.parseColor("#FFF8E1"); isSunday -> android.graphics.Color.parseColor("#F5F5F5")
                            rec?.status == "present" || rec?.status == "regularized" -> android.graphics.Color.parseColor("#F1F8F2"); rec?.status == "absent" -> android.graphics.Color.parseColor("#FFEBEE"); rec?.status == "late" -> android.graphics.Color.parseColor("#FFF3E0")
                            rec?.status == "half-day"|| rec?.status == "half_day" -> android.graphics.Color.parseColor("#E3F2FD"); rec?.status == "od" -> android.graphics.Color.parseColor("#E8EAF6"); rec?.status == "wfh" -> android.graphics.Color.parseColor("#F3E5F5"); rec?.status == "missing_punch_out" -> android.graphics.Color.parseColor("#FFF3E0"); rec?.status?.contains("leave") == true -> android.graphics.Color.parseColor("#FCE4EC"); else -> android.graphics.Color.parseColor("#FAFAFA")
                        }
                        setBackgroundColor(bgColor)
                        if (isToday) background = android.graphics.drawable.GradientDrawable().apply { setColor(android.graphics.Color.parseColor("#E8F5E9")); setStroke((2*dp).toInt(), android.graphics.Color.parseColor("#2E7D45")); cornerRadius = 8*dp }
                    }
                    if (cellDate != null) {
                        cell.addView(TextView(ctx).apply { text = cellDate.toString(); textSize = 13f; setTypeface(null, if (isToday) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL); setTextColor(ctx.getColor(when { isToday -> R.color.primary; is2nd4thSat -> R.color.accent_amber; isSunday -> R.color.accent_red; else -> R.color.text_primary })); gravity = android.view.Gravity.CENTER_HORIZONTAL })
                        if (holName != null) cell.addView(TextView(ctx).apply { text = holName.take(10); textSize = 7f; setTextColor(android.graphics.Color.parseColor("#E65100")); setTypeface(null, android.graphics.Typeface.BOLD); gravity = android.view.Gravity.CENTER_HORIZONTAL; maxLines = 2 })
                        val statusLabel = when { is2nd4thSat && rec?.status?.lowercase() == "absent" -> when (satNum) { 2 -> "2nd Sat"; 4 -> "4th Sat"; else -> "Sat Off" }; isSunday && rec?.status?.lowercase() == "absent" -> "Sun"
                            else -> when (rec?.status?.lowercase()) { "present" -> "P"; "regularized" -> "REG"; "absent" -> "A"; "late" -> "L"; "half-day","half_day" -> "H"; "od" -> "OD"; "wfh" -> "WFH"; "on-leave","leave" -> "LV"; "missing_punch_out" -> "MPO"
                                else -> when { is2nd4thSat && satNum == 2 -> "2nd Sat"; is2nd4thSat && satNum == 4 -> "4th Sat"; isSunday -> "Sun"; else -> "" } }
                        }
                        if (statusLabel.isNotEmpty()) {
                            val pillColor = when { is2nd4thSat && rec?.status?.lowercase() == "absent" -> android.graphics.Color.parseColor("#E65100"); isSunday && rec?.status?.lowercase() == "absent" -> android.graphics.Color.parseColor("#90A4AE")
                                else -> when (rec?.status?.lowercase()) { "present","regularized" -> android.graphics.Color.parseColor("#2E7D45"); "absent" -> android.graphics.Color.parseColor("#C62828"); "late" -> android.graphics.Color.parseColor("#E65100"); "half-day","half_day" -> android.graphics.Color.parseColor("#1565C0"); "od" -> android.graphics.Color.parseColor("#283593"); "wfh" -> android.graphics.Color.parseColor("#6A1B9A"); "on-leave","leave" -> android.graphics.Color.parseColor("#AD1457"); "missing_punch_out" -> android.graphics.Color.parseColor("#E65100")
                                    else -> when { is2nd4thSat -> android.graphics.Color.parseColor("#E65100"); isSunday -> android.graphics.Color.parseColor("#90A4AE"); else -> android.graphics.Color.parseColor("#90A4AE") } }
                            }
                            cell.addView(TextView(ctx).apply { text = statusLabel; textSize = 8f; setTextColor(android.graphics.Color.WHITE); setTypeface(null, android.graphics.Typeface.BOLD); setPadding((4*dp).toInt(),(1*dp).toInt(),(4*dp).toInt(),(1*dp).toInt()); background = android.graphics.drawable.GradientDrawable().apply { setColor(pillColor); cornerRadius = 10*dp }; gravity = android.view.Gravity.CENTER_HORIZONTAL; layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.gravity = android.view.Gravity.CENTER_HORIZONTAL; it.topMargin = (2*dp).toInt() } })
                        }
                        if (rec?.displayPunchIn != null && !isSunday && !is2nd4thSat) cell.addView(TextView(ctx).apply { text = rec.displayPunchIn; textSize = 7f; setTextColor(ctx.getColor(R.color.text_hint)); gravity = android.view.Gravity.CENTER_HORIZONTAL })
                    }
                    weekRow.addView(cell)
                }
                calGrid.addView(weekRow)
            }
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
        val navRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; gravity = android.view.Gravity.CENTER_VERTICAL; setPadding((8*dp).toInt(),(8*dp).toInt(),(8*dp).toInt(),(4*dp).toInt()) }
        val btnPrev = com.google.android.material.button.MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply { text = "‹"; textSize = 18f; layoutParams = LinearLayout.LayoutParams((40*dp).toInt(), (40*dp).toInt()); setOnClickListener { if (displayMonth == 0) { displayMonth = 11; displayYear-- } else displayMonth--; loadData() } }
        val btnNext = com.google.android.material.button.MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply { text = "›"; textSize = 18f; layoutParams = LinearLayout.LayoutParams((40*dp).toInt(), (40*dp).toInt()); setOnClickListener { if (displayMonth == 11) { displayMonth = 0; displayYear++ } else displayMonth++; loadData() } }
        tvMonthYear.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f); navRow.addView(btnPrev); navRow.addView(tvMonthYear); navRow.addView(btnNext); root.addView(navRow); root.addView(calGrid)
        root.addView(LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL; setPadding((8*dp).toInt(),(8*dp).toInt(),(8*dp).toInt(),0); gravity = android.view.Gravity.CENTER_VERTICAL
            fun legendItem(label: String, color: String) {
                addView(View(ctx).apply { layoutParams = LinearLayout.LayoutParams((10*dp).toInt(),(10*dp).toInt()).also { it.marginEnd = (3*dp).toInt() }; background = android.graphics.drawable.GradientDrawable().apply { setColor(android.graphics.Color.parseColor(color)); cornerRadius = 5*dp } })
                addView(TextView(ctx).apply { text = label; textSize = 10f; setTextColor(ctx.getColor(R.color.text_secondary)); layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.marginEnd = (8*dp).toInt() } })
            }
            legendItem("P", "#2E7D45"); legendItem("A", "#C62828"); legendItem("L", "#E65100"); legendItem("H", "#1565C0"); legendItem("WFH", "#6A1B9A"); legendItem("Hol", "#E65100")
        })
        loadData(); return sv
    }
}

// ── HISTORY TAB ───────────────────────────────────────────────────────────────
class AttendanceHistoryFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp  = ctx.resources.displayMetrics.density; val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(ctx.getColor(R.color.background)) }
        val cal = Calendar.getInstance(); val months = arrayOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"); val years  = (cal.get(Calendar.YEAR) downTo cal.get(Calendar.YEAR)-2).map { it.toString() }.toTypedArray()
        val filterRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL; setPadding((16*dp).toInt(), (12*dp).toInt(), (16*dp).toInt(), (8*dp).toInt()) }
        val spMonth = Spinner(ctx).apply { adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, months); setSelection(cal.get(Calendar.MONTH)); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f) }
        val spYear = Spinner(ctx).apply { adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, years); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).also { it.marginStart = (12*dp).toInt() } }
        filterRow.addView(spMonth); filterRow.addView(spYear); root.addView(filterRow)
        val tvSummary = TextView(ctx).apply { setPadding((16*dp).toInt(), 0, (16*dp).toInt(), (8*dp).toInt()); textSize = 12f; setTextColor(ctx.getColor(R.color.text_secondary)) }; root.addView(tvSummary)
        val rv = RecyclerView(ctx).apply { layoutManager = LinearLayoutManager(ctx); setPadding((8*dp).toInt(), 0, (8*dp).toInt(), (80*dp).toInt()); clipToPadding = false }; root.addView(rv, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f))
        fun load(month: Int, year: Int) {
            val apiMonth = month + 1; lifecycleScope.launch {
                try {
                    tvSummary.text = "Loading..."; val res = RetrofitClient.instance.getAttendance(apiMonth, year)
                    if (res.isSuccessful) {
                        val allRecords = res.body()?.data ?: emptyList(); val sessionMgr = com.krishihr.app.utils.SessionManager(requireContext()); val satPolicy  = sessionMgr.getEmployee()?.saturdayPolicy ?: "2nd_4th_off"
                        fun satNumOf(y: Int, m: Int, d: Int): Int { var count = 0; for (i in 1..d) { val t = Calendar.getInstance(); t.set(y, m - 1, i); if (t.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) count++ }; return count }
                        val records = allRecords.filter { rec ->
                            val dStr = rec.displayDate ?: rec.date?.take(10) ?: return@filter true; val p = dStr.split("-"); if (p.size < 3) return@filter true
                            val y = p[0].toInt(); val m = p[1].toInt(); val d = p[2].toInt(); val tmp = Calendar.getInstance(); tmp.set(y, m - 1, d); val dow = tmp.get(Calendar.DAY_OF_WEEK)
                            if (dow == Calendar.SUNDAY) return@filter false
                            if (dow == Calendar.SATURDAY && rec.status == "absent" && satPolicy == "2nd_4th_off") { val sn = satNumOf(y, m, d); if (sn == 2 || sn == 4) return@filter false }
                            true
                        }
                        val present = records.count { it.status in listOf("present","late","half-day","regularized","od","wfh") }; val absent  = records.count { it.status == "absent" }
                        tvSummary.text = "Present: $present  |  Absent: $absent  |  Total: ${records.size} days"; rv.adapter = AttendanceHistoryAdapter(records.sortedByDescending { it.displayDate ?: it.date })
                    } else tvSummary.text = "Error loading data"
                } catch (_: Exception) { tvSummary.text = "Network error" }
            }
        }
        var isInitialized = false; val selListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(a: AdapterView<*>?, v: View?, pos: Int, id: Long) { if (!isInitialized) return; load(spMonth.selectedItemPosition, years[spYear.selectedItemPosition].toInt()) }
            override fun onNothingSelected(a: AdapterView<*>?) {}
        }
        spMonth.onItemSelectedListener = selListener; spYear.onItemSelectedListener  = selListener; isInitialized = true; load(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)); return root
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
            val bgColor = when(st) { "present", "regularized" -> android.graphics.Color.parseColor("#2E7D45"); "od" -> android.graphics.Color.parseColor("#1565C0"); "wfh" -> android.graphics.Color.parseColor("#6A1B9A"); "absent" -> android.graphics.Color.parseColor("#C62828"); "late" -> android.graphics.Color.parseColor("#E65100"); "half-day","half_day" -> android.graphics.Color.parseColor("#1565C0"); else -> android.graphics.Color.parseColor("#90A4AE") }
            text = label; textSize = 11f; setPadding((10*h.card.context.resources.displayMetrics.density).toInt(), (4*h.card.context.resources.displayMetrics.density).toInt(), (10*h.card.context.resources.displayMetrics.density).toInt(), (4*h.card.context.resources.displayMetrics.density).toInt()); setTextColor(android.graphics.Color.WHITE); setTypeface(null, android.graphics.Typeface.BOLD); background = android.graphics.drawable.GradientDrawable().apply { setColor(bgColor); cornerRadius = 20 * h.card.context.resources.displayMetrics.density }
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
        val it = items[pos]; val ctx = h.root.context; val card = (h.root.getChildAt(0) as androidx.cardview.widget.CardView); val ll   = card.getChildAt(0) as LinearLayout; ll.removeAllViews()
        fun tv(t: String, size: Float = 13f, bold: Boolean = false, color: Int = ctx.getColor(R.color.text_primary)) = TextView(ctx).apply { text = t; textSize = size; if (bold) setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(color) }
        ll.addView(tv(it.date?.toDisplayDate() ?: "—", 14f, true)); ll.addView(tv("In: ${it.requestedPunchIn?.take(5) ?: "--"}  Out: ${it.requestedPunchOut?.take(5) ?: "--"}", 12f, color = ctx.getColor(R.color.text_secondary))); ll.addView(tv(it.reason ?: "—", 12f, color = ctx.getColor(R.color.text_hint)))
        val stColor2 = when(it.status?.lowercase()) { "approved" -> android.graphics.Color.parseColor("#2E7D45"); "rejected" -> android.graphics.Color.parseColor("#C62828"); else -> android.graphics.Color.parseColor("#E65100") }
        ll.addView(TextView(ctx).apply { text = it.status?.replaceFirstChar { c -> c.uppercase() } ?: "Pending"; textSize = 11f; setPadding((10*h.root.context.resources.displayMetrics.density).toInt(),(3*h.root.context.resources.displayMetrics.density).toInt(),(10*h.root.context.resources.displayMetrics.density).toInt(),(3*h.root.context.resources.displayMetrics.density).toInt()); setTextColor(android.graphics.Color.WHITE); setTypeface(null, android.graphics.Typeface.BOLD); background = android.graphics.drawable.GradientDrawable().apply { setColor(stColor2); cornerRadius = 20*h.root.context.resources.displayMetrics.density } })
    }
}

// ── OD/WFH TAB ────────────────────────────────────────────────────────────────
class ODWFHFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density; val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(ctx.getColor(R.color.background)); setPadding((16*dp).toInt(), (16*dp).toInt(), (16*dp).toInt(), (16*dp).toInt()) }
        val btnRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL }; val btnOD  = MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonStyle).apply { text = "Apply OD"; setBackgroundColor(ctx.getColor(R.color.accent_orange)); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).also { it.marginEnd = (8*dp).toInt() } }; val btnWFH = MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonStyle).apply { text = "Apply WFH"; setBackgroundColor(ctx.getColor(R.color.accent_purple)); layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f) }; btnRow.addView(btnOD); btnRow.addView(btnWFH); root.addView(btnRow, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.bottomMargin = (16*dp).toInt() })
        val rvOD = RecyclerView(ctx).apply { layoutManager = LinearLayoutManager(ctx) }; root.addView(TextView(ctx).apply { text = "OD Requests"; textSize = 14f; setTypeface(null, android.graphics.Typeface.BOLD); setPadding(0, 0, 0, (8*dp).toInt()) }); root.addView(rvOD)
        fun load() { lifecycleScope.launch { try { val od = RetrofitClient.instance.getMyODRequests(); val wfh = RetrofitClient.instance.getMyWFHRequests(); rvOD.adapter = LeaveListAdapter((od.body()?.data ?: emptyList()) + (wfh.body()?.data ?: emptyList()), showName = false, showAction = false) {} } catch (_: Exception) {} } }
        btnOD.setOnClickListener { ODWFHBottomSheet("OD") { load() }.show(childFragmentManager, "od") }; btnWFH.setOnClickListener { ODWFHBottomSheet("WFH") { load() }.show(childFragmentManager, "wfh") }; load(); return root
    }
}

// ── BOTTOM SHEETS ─────────────────────────────────────────────────────────────
class RegularizeBottomSheet(private val onSuccess: () -> Unit) : BottomSheetDialogFragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        val ctx = requireContext(); val dp = ctx.resources.displayMetrics.density; val root = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setPadding((20*dp).toInt(), (20*dp).toInt(), (20*dp).toInt(), (20*dp).toInt()) }
        fun tv(t: String) = TextView(ctx).apply { text = t; textSize = 14f; setPadding(0, 0, 0, (4*dp).toInt()) };
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
        val cal = Calendar.getInstance(); var fromDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time); var toDate   = fromDate
        root.addView(TextView(ctx).apply { text = "FROM DATE *"; textSize = 11f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(ctx.getColor(R.color.text_secondary)); setPadding(0, 0, 0, (4*dp).toInt()) })
        val tvFromDate = TextView(ctx).apply { text = fromDate; textSize = 14f; setPadding((12*dp).toInt(), (10*dp).toInt(), (12*dp).toInt(), (10*dp).toInt()); setBackgroundColor(ctx.getColor(R.color.background)); setTextColor(ctx.getColor(R.color.text_primary)) }
        root.addView(MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply { text = fromDate; layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (48*dp).toInt()).also { it.bottomMargin = (8*dp).toInt() }; setOnClickListener { DatePickerDialog(ctx, { _, y, m, d -> fromDate = String.format("%04d-%02d-%02d", y, m+1, d); text = fromDate; if (toDate < fromDate) toDate = fromDate }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show() } })
        root.addView(TextView(ctx).apply { text = "TO DATE *"; textSize = 11f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(ctx.getColor(R.color.text_secondary)); setPadding(0, 0, 0, (4*dp).toInt()) })
        root.addView(MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply { text = toDate; layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (48*dp).toInt()).also { it.bottomMargin = (12*dp).toInt() }; setOnClickListener { DatePickerDialog(ctx, { _, y, m, d -> toDate = String.format("%04d-%02d-%02d", y, m+1, d); text = toDate }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show() } })
        var etLocation: EditText? = null; if (type == "OD") { root.addView(TextView(ctx).apply { text = "LOCATION / DESTINATION"; textSize = 11f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(ctx.getColor(R.color.text_secondary)); setPadding(0, 0, 0, (4*dp).toInt()) }); etLocation = EditText(ctx).apply { hint = "e.g. Client Site, Field Visit, Mumbai..."; setBackgroundColor(ctx.getColor(R.color.background)); setPadding((12*dp).toInt(), (10*dp).toInt(), (12*dp).toInt(), (10*dp).toInt()); layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.bottomMargin = (12*dp).toInt() } }; root.addView(etLocation) }
        root.addView(TextView(ctx).apply { text = "REASON *"; textSize = 11f; setTypeface(null, android.graphics.Typeface.BOLD); setTextColor(ctx.getColor(R.color.text_secondary)); setPadding(0, 0, 0, (4*dp).toInt()) }); val etReason = EditText(ctx).apply { hint = "Purpose of ${type.lowercase()} duty..."; setBackgroundColor(ctx.getColor(R.color.background)); setPadding((12*dp).toInt(), (10*dp).toInt(), (12*dp).toInt(), (10*dp).toInt()); minLines = 3; gravity = android.view.Gravity.TOP; layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also { it.bottomMargin = (12*dp).toInt() } }; root.addView(etReason); val progress = ProgressBar(ctx).apply { visibility = View.GONE }; root.addView(progress)
        root.addView(MaterialButton(ctx, null, com.google.android.material.R.attr.materialButtonStyle).apply { text = "Submit $type"; setBackgroundColor(ctx.getColor(if (type == "OD") R.color.accent_orange else R.color.accent_purple)); layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (52*dp).toInt()).also { it.topMargin = (4*dp).toInt() }; setOnClickListener { val reason = etReason.text.toString().trim(); if (reason.isEmpty()) { toast("Enter reason"); return@setOnClickListener }; if (toDate < fromDate) { toast("To date cannot be before From date"); return@setOnClickListener }; progress.visibility = View.VISIBLE; isEnabled = false; lifecycleScope.launch { try { val location = etLocation?.text?.toString()?.trim()?.ifEmpty { "Outdoor Duty" } ?: "Outdoor Duty"; val res = if (type == "OD") RetrofitClient.instance.applyOD(ODRequest(from_date = fromDate, to_date = toDate, reason = reason, location = location)) else RetrofitClient.instance.applyWFH(WFHRequest(from_date = fromDate, to_date = toDate, reason = reason)); if (res.isSuccessful && res.body()?.success == true) { toast("$type applied ✅"); onSuccess(); dismiss() } else toast(res.body()?.message ?: "Failed") } catch (_: Exception) { toast("Network error") } finally { progress.visibility = View.GONE; isEnabled = true } } } })
        return root
    }
}