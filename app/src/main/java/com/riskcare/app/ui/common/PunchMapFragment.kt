package com.riskcare.app.ui.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
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
import com.riskcare.app.AndroidMain
import com.riskcare.app.R
import com.riskcare.app.data.api.RetrofitClient
import com.riskcare.app.data.models.BufferRuleResponse
import com.riskcare.app.data.models.MyGeofenceLocation
import com.riskcare.app.data.models.ValidateBufferRequest
import com.riskcare.app.databinding.FragmentPunchMapBinding
import com.riskcare.app.utils.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

// Self-contained mini-map that shows the employee's live location and their
// geofence buffer — the same visualization used on the Attendance page, but
// with no punch-button coupling. Hosted transiently on the dashboard while a
// punch is in progress.
class PunchMapFragment : Fragment(), OnMapReadyCallback {

    private var _b: FragmentPunchMapBinding? = null
    private val binding get() = _b!!

    private var googleMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null
    private var mapReady = false
    private var geofenceLocations: List<MyGeofenceLocation> = emptyList()
    private var lastEmployeeLat: Double? = null
    private var lastEmployeeLng: Double? = null
    private var empDotMarker: Marker? = null
    private var cameraInitialized = false
    private var isUserTouching = false
    private var userHasInteracted = false
    private var lastValidatTime = 0L
    private var boundaryDrawn = false
    private var lastDistrictRing: List<LatLng>? = null
    private var employeeBufferRule: BufferRuleResponse? = null

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

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentPunchMapBinding.inflate(i, c, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMapFragment()
        loadGeofenceMap()
    }

    private fun initMapFragment() {
        mapFragment = SupportMapFragment.newInstance(
            GoogleMapOptions()
                .mapType(GoogleMap.MAP_TYPE_HYBRID)
                .zoomControlsEnabled(false)
                .compassEnabled(false)
                .liteMode(false)
        )
        childFragmentManager.beginTransaction()
            .replace(R.id.punch_map_container, mapFragment!!)
            .commitNow()
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        mapReady  = true
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        map.uiSettings.isZoomControlsEnabled   = true
        map.uiSettings.isZoomGesturesEnabled   = true
        map.uiSettings.isScrollGesturesEnabled = true
        map.uiSettings.isTiltGesturesEnabled   = false
        map.uiSettings.isRotateGesturesEnabled = true
        map.setMinZoomPreference(5f)
        map.setMaxZoomPreference(21f)
        map.setOnCameraMoveStartedListener { reason ->
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                isUserTouching = true
                userHasInteracted = true
            }
        }
        map.setOnCameraIdleListener { isUserTouching = false }
        if (geofenceLocations.isNotEmpty() || lastDistrictRing != null) drawGeofenceOnMap()
        lastEmployeeLat?.let { lat -> lastEmployeeLng?.let { lng -> placeEmployeeDot(lat, lng) } }
    }

    private fun loadGeofenceMap() {
        lifecycleScope.launch {
            val empId = SessionManager(requireContext()).getEmployee()?.id ?: 0
            repeat(2) { attempt ->
                if (employeeBufferRule == null) {
                    try { employeeBufferRule = RetrofitClient.instance.getBufferRule(empId).body()?.data }
                    catch (_: Exception) { if (attempt == 0) delay(1500) }
                }
            }
            try { geofenceLocations = RetrofitClient.instance.getMyGeofenceLocations().body()?.data ?: emptyList() }
            catch (_: Exception) { geofenceLocations = emptyList() }
            if (_b == null) return@launch
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
                binding.tvPunchMapStatus.text = "📍 District Boundary"
                binding.tvPunchMapStatus.setTextColor(Color.parseColor("#FF9800"))
                binding.tvPunchMapDistance.text = if (rule.district != null && rule.state != null)
                    "Checking if you are in ${rule.district}, ${rule.state}…" else "Waiting for location signal..."
            }
            "state" -> {
                binding.tvPunchMapStatus.text = "🗺️ State Boundary"
                binding.tvPunchMapStatus.setTextColor(Color.parseColor("#FF9800"))
                binding.tvPunchMapDistance.text = if (rule.state != null)
                    "Checking if you are in ${rule.state}…" else "Waiting for location signal..."
            }
            "universal" -> {
                binding.tvPunchMapStatus.text = "✅ Universal access"
                binding.tvPunchMapStatus.setTextColor(Color.parseColor("#4CAF50"))
                binding.tvPunchMapDistance.text = "Punch allowed from anywhere"
            }
            else -> {
                binding.tvPunchMapStatus.text = "📡 Getting GPS..."
                binding.tvPunchMapStatus.setTextColor(Color.parseColor("#FF9800"))
                binding.tvPunchMapDistance.text = "Waiting for location signal..."
            }
        }
        if (mapReady) drawGeofenceOnMap()
    }

    private fun clearMapOverlays() {
        mapPolygons.forEach  { it.remove() }; mapPolygons.clear()
        mapPolylines.forEach { it.remove() }; mapPolylines.clear()
        mapCircles.forEach   { it.remove() }; mapCircles.clear()
        mapMarkers.forEach   { it.remove() }; mapMarkers.clear()
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
                    binding.tvPunchMapStatus.text = "✅ Universal access"
                    binding.tvPunchMapStatus.setTextColor(Color.parseColor("#4CAF50"))
                    binding.tvPunchMapDistance.text = "Punch allowed from anywhere"
                }
            }
            null -> {
                drawOfficeCircles(map)
                lifecycleScope.launch {
                    delay(AndroidMain.PUNCH_TOAST_DELAY_MS)
                    if (employeeBufferRule != null && _b != null) { clearMapOverlays(); boundaryDrawn = false; drawGeofenceOnMap() }
                }
            }
            else -> { drawOfficeCircles(map); boundaryDrawn = true }
        }
    }

    private fun drawOfficeCircles(map: GoogleMap) {
        clearMapOverlays()
        val userLat = lastEmployeeLat; val userLng = lastEmployeeLng
        var closestDist = Double.MAX_VALUE; var closestEdgeLat = 0.0; var closestEdgeLng = 0.0
        for (loc in geofenceLocations) {
            if (loc.radiusMeters > AndroidMain.GEOFENCE_MAX_RADIUS_M) continue
            val center = LatLng(loc.latitude, loc.longitude)
            mapCircles += map.addCircle(
                CircleOptions().center(center).radius(loc.radiusMeters.toDouble())
                    .fillColor(Color.argb(38, 236, 72, 153))
                    .strokeColor(Color.argb(255, 29, 78, 216)).strokeWidth(5f)
            )
            if (userLat != null && userLng != null) {
                val r = FloatArray(1)
                Location.distanceBetween(userLat, userLng, loc.latitude, loc.longitude, r)
                val distToEdge = Math.abs(r[0].toDouble() - loc.radiusMeters)
                if (distToEdge < closestDist) {
                    closestDist = distToEdge
                    val bearing = Math.toDegrees(Math.atan2(
                        (userLng - loc.longitude) * Math.cos(Math.toRadians(loc.latitude)), userLat - loc.latitude))
                    closestEdgeLat = Math.toDegrees(Math.toRadians(loc.latitude) + (loc.radiusMeters / 6371000.0) * Math.cos(Math.toRadians(bearing)))
                    closestEdgeLng = Math.toDegrees(Math.toRadians(loc.longitude) + (loc.radiusMeters / 6371000.0) * Math.sin(Math.toRadians(bearing)) / Math.cos(Math.toRadians(loc.latitude)))
                }
            }
        }
        if (userLat != null && userLng != null && closestDist < Double.MAX_VALUE) {
            mapPolylines += map.addPolyline(
                PolylineOptions().add(LatLng(userLat, userLng), LatLng(closestEdgeLat, closestEdgeLng))
                    .color(Color.argb(200, 29, 78, 216)).width(4f).pattern(listOf(Dash(20f), Gap(15f)))
            )
            if (_b != null) {
                val distText = if (closestDist < 1000) "${closestDist.toInt()} m from boundary"
                else "${"%.1f".format(closestDist / 1000)} km from boundary"
                binding.tvPunchMapDistance.text = "📏 $distText"
            }
        }
        zoomToOfficeBuffer()
    }

    private suspend fun drawDistrictPolygon(map: GoogleMap, state: String, district: String) {
        try {
            val data = RetrofitClient.instance.getBoundary(state, district).body()?.data ?: return
            if (data.coordinates.isEmpty()) return
            val ring = data.coordinates[0].map { pt -> LatLng(pt[1], pt[0]) }
            lastDistrictRing = ring
            withContext(kotlinx.coroutines.Dispatchers.Main) {
                if (_b == null || googleMap == null) return@withContext
                clearMapOverlays()
                mapPolygons += map.addPolygon(
                    PolygonOptions().addAll(ring)
                        .fillColor(Color.argb(89, 236, 72, 153))
                        .strokeColor(Color.argb(255, 29, 78, 216)).strokeWidth(8f)
                )
                val userLat = lastEmployeeLat; val userLng = lastEmployeeLng
                val boundsBuilder = LatLngBounds.Builder(); ring.forEach { boundsBuilder.include(it) }
                if (userLat != null && userLng != null) {
                    boundsBuilder.include(LatLng(userLat, userLng))
                    val nearestPt = ring.minByOrNull { pt ->
                        Math.sqrt(Math.pow(pt.latitude - userLat, 2.0) + Math.pow(pt.longitude - userLng, 2.0)) }
                    if (nearestPt != null) {
                        mapPolylines += map.addPolyline(PolylineOptions().add(LatLng(userLat, userLng), nearestPt)
                            .color(Color.argb(200, 29, 78, 216)).width(4f).pattern(listOf(Dash(20f), Gap(15f))))
                    }
                }
                try { if (!userHasInteracted) map.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 60)) } catch (_: Exception) {}
            }
        } catch (_: Exception) {}
    }

    private suspend fun drawStatePolygons(map: GoogleMap, state: String) {
        try {
            val districts = RetrofitClient.instance.getStateBoundary(state).body()?.data ?: return
            val boundsBuilder = LatLngBounds.Builder(); var hasPoints = false
            withContext(kotlinx.coroutines.Dispatchers.Main) {
                if (_b == null || googleMap == null) return@withContext
                clearMapOverlays()
                for (d in districts) {
                    if (d.coordinates.isEmpty()) continue
                    val ring = d.coordinates[0].map { pt -> LatLng(pt[1], pt[0]) }
                    if (ring.isEmpty()) continue
                    mapPolygons += map.addPolygon(PolygonOptions().addAll(ring)
                        .fillColor(Color.argb(25, 236, 72, 153))
                        .strokeColor(Color.argb(210, 29, 78, 216)).strokeWidth(3f))
                    ring.forEach { boundsBuilder.include(it); hasPoints = true }
                }
                if (hasPoints) {
                    try { if (!userHasInteracted) map.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 60)) } catch (_: Exception) {}
                }
            }
        } catch (_: Exception) {}
    }

    private fun createBlueDotBitmap(sizePx: Int): Bitmap {
        val bmp = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val cx = sizePx / 2f; val cy = sizePx / 2f
        canvas.drawCircle(cx, cy, sizePx / 2f, Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.argb(60, 33, 150, 243); style = Paint.Style.FILL })
        canvas.drawCircle(cx, cy, sizePx / 2f * 0.62f, Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; style = Paint.Style.FILL })
        canvas.drawCircle(cx, cy, sizePx / 2f * 0.44f, Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.argb(255, 33, 150, 243); style = Paint.Style.FILL })
        return bmp
    }

    private fun placeEmployeeDot(lat: Double, lng: Double) {
        val map = googleMap ?: return
        val pos = LatLng(lat, lng)
        if (empDotMarker == null) {
            empDotMarker = map.addMarker(MarkerOptions().position(pos).title("You")
                .icon(BitmapDescriptorFactory.fromBitmap(createBlueDotBitmap(52))).anchor(0.5f, 0.5f))
        } else empDotMarker!!.position = pos
    }

    private fun zoomToOfficeBuffer() {
        val map = googleMap ?: return
        if (userHasInteracted) return
        if (geofenceLocations.isEmpty()) return
        val target = if (lastEmployeeLat != null && lastEmployeeLng != null) {
            geofenceLocations.minByOrNull { loc ->
                val r = FloatArray(1); Location.distanceBetween(lastEmployeeLat!!, lastEmployeeLng!!, loc.latitude, loc.longitude, r); r[0]
            } ?: geofenceLocations[0]
        } else geofenceLocations[0]
        val radiusM = target.radiusMeters.toDouble().coerceAtLeast(50.0)
        val cosLat  = Math.cos(Math.toRadians(target.latitude))
        val zoom    = (Math.log(156543.0 * cosLat / (radiusM * 2.0)) / Math.log(2.0)).coerceIn(15.0, 20.0).toFloat()
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(target.latitude, target.longitude), zoom), 600, null)
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
                if (boundaryDrawn) {
                    mapPolylines.forEach { it.remove() }; mapPolylines.clear()
                    when (employeeBufferRule?.ruleType) {
                        "office", null -> {
                            var closestDist = Double.MAX_VALUE; var ceLat = 0.0; var ceLng = 0.0
                            for (loc2 in geofenceLocations) {
                                if (loc2.radiusMeters > AndroidMain.GEOFENCE_MAX_RADIUS_M) continue
                                val r = FloatArray(1)
                                Location.distanceBetween(empLat, empLng, loc2.latitude, loc2.longitude, r)
                                val distToEdge = Math.abs(r[0].toDouble() - loc2.radiusMeters)
                                if (distToEdge < closestDist) {
                                    closestDist = distToEdge
                                    val bearing = Math.toDegrees(Math.atan2((empLng - loc2.longitude) * Math.cos(Math.toRadians(loc2.latitude)), empLat - loc2.latitude))
                                    ceLat = Math.toDegrees(Math.toRadians(loc2.latitude) + (loc2.radiusMeters / 6371000.0) * Math.cos(Math.toRadians(bearing)))
                                    ceLng = Math.toDegrees(Math.toRadians(loc2.longitude) + (loc2.radiusMeters / 6371000.0) * Math.sin(Math.toRadians(bearing)) / Math.cos(Math.toRadians(loc2.latitude)))
                                }
                            }
                            if (closestDist < Double.MAX_VALUE) {
                                mapPolylines += map.addPolyline(PolylineOptions().add(LatLng(empLat, empLng), LatLng(ceLat, ceLng))
                                    .color(Color.argb(200, 29, 78, 216)).width(4f).pattern(listOf(Dash(20f), Gap(15f))))
                            }
                        }
                        "district" -> {
                            val ring2 = lastDistrictRing
                            if (ring2 != null) {
                                val nearestPt = ring2.minByOrNull { pt -> Math.sqrt(Math.pow(pt.latitude - empLat, 2.0) + Math.pow(pt.longitude - empLng, 2.0)) }
                                if (nearestPt != null) {
                                    mapPolylines += map.addPolyline(PolylineOptions().add(LatLng(empLat, empLng), nearestPt)
                                        .color(Color.argb(200, 29, 78, 216)).width(4f).pattern(listOf(Dash(20f), Gap(15f))))
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }
            if (_b == null) return
            val now = System.currentTimeMillis()
            val rule = employeeBufferRule
            if (rule?.ruleType == "universal") {
                binding.tvPunchMapStatus.text = "✅ Universal access"
                binding.tvPunchMapStatus.setTextColor(Color.parseColor("#4CAF50"))
                binding.tvPunchMapDistance.text = "Punch allowed from anywhere"
                if (!cameraInitialized) { zoomToOfficeBuffer(); cameraInitialized = true }
                return
            }
            if (now - lastValidatTime < AndroidMain.GEOFENCE_VALIDATE_COOLDOWN_MS) return
            lastValidatTime = now
            try {
                val bufData = RetrofitClient.instance.validateBuffer(ValidateBufferRequest(latitude = empLat, longitude = empLng)).body()
                val inside = bufData?.valid ?: true
                if (inside) {
                    binding.tvPunchMapStatus.text = "✅ Inside boundary"
                    binding.tvPunchMapStatus.setTextColor(Color.parseColor("#4CAF50"))
                    binding.tvPunchMapDistance.text = bufData?.message ?: "Boundary verified"
                } else {
                    binding.tvPunchMapStatus.text = "⛔ Outside boundary"
                    binding.tvPunchMapStatus.setTextColor(Color.parseColor("#F44336"))
                    binding.tvPunchMapDistance.text = bufData?.message ?: "Outside your assigned boundary"
                }
            } catch (_: Exception) {
                binding.tvPunchMapStatus.text = "⚠ Validation unavailable"
                binding.tvPunchMapStatus.setTextColor(Color.parseColor("#FF9800"))
                binding.tvPunchMapDistance.text = "Could not verify boundary"
            }
            if (!cameraInitialized && !userHasInteracted) {
                val rule2 = employeeBufferRule
                if ((rule2?.ruleType == "district" || rule2?.ruleType == "state") && lastDistrictRing != null) {
                    val bb = LatLngBounds.Builder(); lastDistrictRing!!.forEach { bb.include(it) }; bb.include(LatLng(empLat, empLng))
                    try { googleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bb.build(), 60)) } catch (_: Exception) {}
                } else {
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(empLat, empLng), 15f))
                }
                cameraInitialized = true
            }
        } catch (_: Exception) {}
    }

    private suspend fun getLocation(): Location? {
        val client = LocationServices.getFusedLocationProviderClient(requireActivity())
        return try {
            val last = suspendCancellableCoroutine<Location?> { cont ->
                try { client.lastLocation.addOnSuccessListener { cont.resume(it) }.addOnFailureListener { cont.resume(null) } }
                catch (_: SecurityException) { cont.resume(null) }
            }
            if (last != null && (System.currentTimeMillis() - last.time) < 30_000L && last.accuracy < 100f) return last
            withTimeoutOrNull(10_000L) {
                suspendCancellableCoroutine<Location?> { cont ->
                    val cts = CancellationTokenSource(); cont.invokeOnCancellation { cts.cancel() }
                    try { client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token).addOnSuccessListener { cont.resume(it) }.addOnFailureListener { cont.resume(null) } }
                    catch (_: SecurityException) { cont.resume(null) }
                }
            } ?: last
        } catch (_: Exception) { null }
    }

    override fun onDestroyView() {
        mapLocationHandler.removeCallbacks(mapLocationRunnable)
        clearMapOverlays()
        empDotMarker?.remove(); empDotMarker = null
        googleMap = null; mapReady = false
        super.onDestroyView()
        _b = null
    }
}
