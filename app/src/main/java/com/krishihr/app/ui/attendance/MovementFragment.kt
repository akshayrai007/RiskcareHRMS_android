package com.krishihr.app.ui.attendance
import com.krishihr.app.AndroidMain

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.webkit.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.krishihr.app.R
import com.krishihr.app.data.api.RetrofitClient
import com.krishihr.app.data.models.Employee
import com.krishihr.app.data.models.MovementPoint
import com.krishihr.app.utils.Roles
import com.krishihr.app.utils.SessionManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MovementFragment : Fragment() {

    private lateinit var session: SessionManager
    private var employees: List<Employee> = emptyList()

    private lateinit var spinnerEmp: Spinner
    private lateinit var btnDate: Button
    private lateinit var btnSearch: Button
    private lateinit var tvStatus: TextView
    private lateinit var llSummaryCards: LinearLayout
    private lateinit var webMap: WebView
    private lateinit var llTimeline: LinearLayout
    private lateinit var cardMap: CardView
    private lateinit var cardTimeline: CardView
    private lateinit var tvLiveTag: TextView

    private var selectedDate: String = todayIST()
    private var isLiveMode: Boolean = false       // true when viewing today
    private var lastPointCount: Int = 0           // detect new points on refresh
    private var currentEmpId: Int = -1
    private var currentEmpName: String = ""

    // Live refresh — polls every 30 sec when viewing today's route
    private val liveHandler = Handler(Looper.getMainLooper())
    private val liveRunnable = object : Runnable {
        override fun run() {
            if (isLiveMode && currentEmpId != -1) {
                refreshLiveRoute()
            }
            liveHandler.postDelayed(this, AndroidMain.MOVEMENT_LIVE_REFRESH_MS)
        }
    }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        session = SessionManager(requireContext())
        return buildUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadEmployees()
    }

    override fun onResume() {
        super.onResume()
        if (isLiveMode && currentEmpId != -1) {
            liveHandler.post(liveRunnable)
        }
    }

    override fun onPause() {
        super.onPause()
        liveHandler.removeCallbacks(liveRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        liveHandler.removeCallbacks(liveRunnable)
        if (::webMap.isInitialized) webMap.destroy()
    }

    // ── UI Build ──────────────────────────────────────────────────────────────

    private fun buildUI(): View {
        val ctx = requireContext()
        val dp  = ctx.resources.displayMetrics.density

        val scroll = ScrollView(ctx).apply {
            setBackgroundColor(ContextCompat.getColor(ctx, R.color.background))
            layoutParams = ViewGroup.LayoutParams(MATCH, MATCH)
        }
        val outer = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(px(12,dp), px(12,dp), px(12,dp), px(80,dp))
        }
        scroll.addView(outer)

        // ── Filter card ───────────────────────────────────────────────────────
        val filterCard = makeCard(ctx, dp)
        val filterLL   = vLL(ctx).apply { setPadding(px(16,dp), px(14,dp), px(16,dp), px(14,dp)) }
        filterCard.addView(filterLL); outer.addView(filterCard)

        // Header row with LIVE badge
        val headerRow = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER_VERTICAL
            setPadding(0,0,0,px(10,dp))
        }
        headerRow.addView(TextView(ctx).apply {
            text = "📍 Employee Movement Tracker"; textSize = 15f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(ctx, R.color.text_primary))
            layoutParams = llp(0, WRAP, 1f)
        })
        tvLiveTag = TextView(ctx).apply {
            text = "🔴 LIVE"
            textSize = 10f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(android.graphics.Color.WHITE)
            setBackgroundColor(android.graphics.Color.parseColor("#DC2626"))
            setPadding(px(6,dp), px(3,dp), px(6,dp), px(3,dp))
            background = android.graphics.drawable.GradientDrawable().apply {
                setColor(android.graphics.Color.parseColor("#DC2626"))
                cornerRadius = 20f
            }
            visibility = View.GONE
        }
        headerRow.addView(tvLiveTag)
        filterLL.addView(headerRow)

        filterLL.addView(lbl(ctx,"Employee",dp))
        spinnerEmp = Spinner(ctx).apply {
            layoutParams = llp(MATCH, px(44,dp)).also { it.bottomMargin = px(10,dp) }
        }
        filterLL.addView(spinnerEmp)

        filterLL.addView(lbl(ctx,"Date",dp))
        btnDate = Button(ctx).apply {
            text = "📅  $selectedDate"; textSize = 13f
            layoutParams = llp(MATCH, px(44,dp)).also { it.bottomMargin = px(12,dp) }
            setBackgroundColor(ContextCompat.getColor(ctx, R.color.primary_ultra_light))
            setTextColor(ContextCompat.getColor(ctx, R.color.primary))
            setOnClickListener { pickDate() }
        }
        filterLL.addView(btnDate)

        btnSearch = Button(ctx).apply {
            text = "🔍  Search"; textSize = 14f
            layoutParams = llp(MATCH, px(46,dp))
            setBackgroundColor(ContextCompat.getColor(ctx, R.color.primary))
            setTextColor(ContextCompat.getColor(ctx, R.color.white))
            setOnClickListener { doSearch() }
        }
        filterLL.addView(btnSearch)

        // ── Status ────────────────────────────────────────────────────────────
        tvStatus = TextView(ctx).apply {
            text = "Select an employee and date, then tap Search"
            textSize = 13f; gravity = android.view.Gravity.CENTER
            setTextColor(ContextCompat.getColor(ctx, R.color.text_secondary))
            setPadding(0, px(20,dp), 0, px(20,dp))
        }
        outer.addView(tvStatus)

        // ── Stat cards row ────────────────────────────────────────────────────
        llSummaryCards = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = llp(MATCH, WRAP).also { it.bottomMargin = px(12,dp) }
            visibility = View.GONE
        }
        outer.addView(llSummaryCards)

        // ── Map card ──────────────────────────────────────────────────────────
        cardMap = makeCard(ctx, dp).apply {
            visibility = View.GONE
            (layoutParams as LinearLayout.LayoutParams).bottomMargin = px(12,dp)
        }
        val mapLL = vLL(ctx).apply { setPadding(px(14,dp), px(12,dp), px(14,dp), px(12,dp)) }
        cardMap.addView(mapLL); outer.addView(cardMap)

        mapLL.addView(TextView(ctx).apply {
            text = "🗺️  Route Map  (tap points for details)"; textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(ctx, R.color.text_primary))
            setPadding(0,0,0,px(8,dp))
        })

        webMap = WebView(ctx).apply {
            layoutParams = llp(MATCH, px(420,dp))
            settings.javaScriptEnabled   = true
            settings.domStorageEnabled   = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort     = true
            webChromeClient = WebChromeClient()
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }
        mapLL.addView(webMap)

        // ── Timeline card ─────────────────────────────────────────────────────
        cardTimeline = makeCard(ctx, dp).apply { visibility = View.GONE }
        val tlLL = vLL(ctx).apply { setPadding(px(14,dp), px(12,dp), px(14,dp), px(12,dp)) }
        cardTimeline.addView(tlLL); outer.addView(cardTimeline)

        tlLL.addView(TextView(ctx).apply {
            text = "📋  Point Timeline"; textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(ctx, R.color.text_primary))
            setPadding(0,0,0,px(8,dp))
        })
        llTimeline = vLL(ctx)
        tlLL.addView(llTimeline)

        return scroll
    }

    // ── Load employees ────────────────────────────────────────────────────────

    private fun loadEmployees() {
        val emp  = session.getEmployee() ?: return
        val role = session.getRole()
        val seeAll = emp.employeeCode == AndroidMain.SEE_ALL_MOVEMENT_CODE
                || role == Roles.SUPER_ADMIN
                || role == Roles.HR

        lifecycleScope.launch {
            try {
                val res = RetrofitClient.instance.getEmployees()
                if (res.isSuccessful && res.body()?.success == true) {
                    val all = res.body()!!.data ?: emptyList()
                    employees = if (seeAll) all
                    else all.filter { it.reportingManagerId == emp.id }

                    val names = mutableListOf("— Select Employee —")
                    names.addAll(employees.map { "${it.firstName} ${it.lastName} (${it.employeeCode})" })
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, names)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerEmp.adapter = adapter
                }
            } catch (_: Exception) {}
        }
    }

    // ── Date picker ───────────────────────────────────────────────────────────

    private fun pickDate() {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"))
        DatePickerDialog(requireContext(), { _, y, m, d ->
            selectedDate = "%04d-%02d-%02d".format(y, m+1, d)
            btnDate.text = "📅  $selectedDate"
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    // ── Search ────────────────────────────────────────────────────────────────

    private fun doSearch() {
        val pos = spinnerEmp.selectedItemPosition
        if (pos == 0) { Toast.makeText(context,"Please select an employee",Toast.LENGTH_SHORT).show(); return }
        val emp = employees[pos - 1]
        currentEmpId   = emp.id
        currentEmpName = "${emp.firstName} ${emp.lastName}"

        // Determine if this is today → live mode
        isLiveMode = (selectedDate == todayIST())

        tvStatus.text = "⏳  Loading…"; tvStatus.visibility = View.VISIBLE
        llSummaryCards.visibility = View.GONE
        cardMap.visibility        = View.GONE
        cardTimeline.visibility   = View.GONE
        llTimeline.removeAllViews(); llSummaryCards.removeAllViews()
        lastPointCount = 0

        if (isLiveMode) {
            tvLiveTag.visibility = View.VISIBLE
            liveHandler.removeCallbacks(liveRunnable)
            liveHandler.post(liveRunnable)          // start polling immediately
        } else {
            tvLiveTag.visibility = View.GONE
            liveHandler.removeCallbacks(liveRunnable)
            fetchAndRender(emp, selectedDate, animate = true)
        }
    }

    // ── Live refresh (every 30 sec for today) ─────────────────────────────────

    private fun refreshLiveRoute() {
        val pos = spinnerEmp.selectedItemPosition
        if (pos == 0 || pos > employees.size) return
        val emp = employees[pos - 1]
        fetchAndRender(emp, todayIST(), animate = false, silent = true)
    }

    // ── Fetch from API and render ─────────────────────────────────────────────

    private fun fetchAndRender(
        emp: Employee,
        date: String,
        animate: Boolean,
        silent: Boolean = false
    ) {
        lifecycleScope.launch {
            try {
                val res = RetrofitClient.instance.getMovementHistory(
                    employeeId = emp.id, date = date
                )
                if (res.isSuccessful && res.body()?.success == true) {
                    val pts = res.body()!!.data ?: emptyList()
                    if (pts.isEmpty()) {
                        if (!silent) {
                            tvStatus.text =
                                "⚠️  No GPS points found for ${emp.firstName} on $date.\n\n" +
                                        "Employee must be punched in. OD tracking starts as soon as OD is applied (approval not required)."
                            tvStatus.visibility = View.VISIBLE
                        }
                    } else {
                        // Only re-render if new points arrived (avoid flicker on live refresh)
                        if (pts.size != lastPointCount) {
                            lastPointCount = pts.size
                            tvStatus.visibility = View.GONE
                            renderResults(emp, pts, animate)
                        }
                    }
                } else {
                    if (!silent) tvStatus.text = "❌  Failed to load. Please try again."
                }
            } catch (e: Exception) {
                if (!silent) tvStatus.text = "❌  ${e.message}"
            }
        }
    }

    // ── Render map + stats + timeline ─────────────────────────────────────────

    private fun renderResults(emp: Employee, pts: List<MovementPoint>, animate: Boolean) {
        val ctx = requireContext()
        val dp  = ctx.resources.displayMetrics.density

        // ── KM calculation ────────────────────────────────────────────────────
        var totalKm = 0.0
        for (i in 1 until pts.size)
            totalKm += haversine(pts[i-1].lat, pts[i-1].lng, pts[i].lat, pts[i].lng)
        totalKm = (totalKm * 100).toLong() / 100.0

        // ── Speed (avg km/h between last 2 points) ────────────────────────────
        val avgSpeedKmh: Double = if (pts.size >= 2) {
            try {
                val sdf  = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val last = sdf.parse(pts.last().loggedAt)
                val prev = sdf.parse(pts[pts.size-2].loggedAt)
                val diffH = (last!!.time - prev!!.time) / 3_600_000.0
                val segKm = haversine(pts[pts.size-2].lat, pts[pts.size-2].lng, pts.last().lat, pts.last().lng)
                if (diffH > 0) segKm / diffH else 0.0
            } catch (_: Exception) { 0.0 }
        } else 0.0

        // ── Stat cards ────────────────────────────────────────────────────────
        llSummaryCards.removeAllViews()
        llSummaryCards.visibility = View.VISIBLE
        data class S(val v: String, val l: String, val c: Int, val bg: Int)
        listOf(
            S("%.2f km".format(totalKm),           "Distance",   R.color.primary,     R.color.primary_ultra_light),
            S("${pts.size}",                         "GPS Points", R.color.accent_blue, R.color.accent_blue_light),
            S(pts.first().timeLabel,                 "Start",      R.color.primary,     R.color.primary_ultra_light),
            S(pts.last().timeLabel,                  if (isLiveMode) "Last Ping" else "End", R.color.accent_red, R.color.accent_red_light),
            S("%.1f km/h".format(avgSpeedKmh),      "Avg Speed",  R.color.accent_blue, R.color.accent_blue_light)
        ).forEach { s ->
            val c = makeCard(ctx, dp).apply {
                layoutParams = LinearLayout.LayoutParams(0, WRAP, 1f)
                    .also { it.marginEnd = px(4,dp) }
                setCardBackgroundColor(ContextCompat.getColor(ctx, s.bg))
            }
            val ll = vLL(ctx).apply {
                gravity = android.view.Gravity.CENTER
                setPadding(px(4,dp), px(10,dp), px(4,dp), px(10,dp))
            }
            ll.addView(TextView(ctx).apply {
                text = s.v; textSize = 11f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(ContextCompat.getColor(ctx, s.c))
                gravity = android.view.Gravity.CENTER
            })
            ll.addView(TextView(ctx).apply {
                text = s.l; textSize = 9f
                setTextColor(ContextCompat.getColor(ctx, s.c))
                gravity = android.view.Gravity.CENTER
            })
            c.addView(ll); llSummaryCards.addView(c)
        }

        // ── Build Leaflet map HTML with animated route ─────────────────────────
        cardMap.visibility = View.VISIBLE
        val center    = pts[pts.size / 2]
        val ptsJson   = pts.joinToString(",") { "{lat:${it.lat},lng:${it.lng},t:'${it.timeLabel}'}" }

        // Heading arrow between last two points
        val headingDeg: Double = if (pts.size >= 2) {
            bearing(pts[pts.size-2].lat, pts[pts.size-2].lng, pts.last().lat, pts.last().lng)
        } else 0.0

        val animateJs = if (animate) "true" else "false"

        val html = """<!DOCTYPE html><html><head>
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<style>
*{margin:0;padding:0;box-sizing:border-box}
body{background:#0f172a;font-family:sans-serif}
#bar{background:#1e293b;color:#fff;padding:8px 14px;font-size:11px;
     display:flex;gap:14px;flex-wrap:wrap;align-items:center;min-height:38px}
.chip{display:flex;flex-direction:column;align-items:center;min-width:40px}
.chip b{font-size:13px;color:#60a5fa;line-height:1.2}
.chip span{font-size:9px;color:#94a3b8;text-transform:uppercase;letter-spacing:.5px}
#map{width:100%;height:375px}
#live-dot{display:inline-block;width:8px;height:8px;border-radius:50%;
          background:#ef4444;animation:pulse 1.4s infinite;vertical-align:middle;margin-right:4px}
@keyframes pulse{0%,100%{opacity:1;transform:scale(1)}50%{opacity:.5;transform:scale(1.4)}}
.walk-icon{font-size:26px;line-height:1;display:flex;align-items:center;justify-content:center}
</style>
</head><body>
<div id="bar">
  <div class="chip"><b id="b-km">${"%.2f km".format(totalKm)}</b><span>Distance</span></div>
  <div class="chip"><b id="b-pts">${pts.size}</b><span>Points</span></div>
  <div class="chip"><b>${pts.first().timeLabel}</b><span>Start</span></div>
  <div class="chip"><b id="b-last">${pts.last().timeLabel}</b><span>${if (isLiveMode) "Last Ping" else "End"}</span></div>
  <div class="chip"><b>${"%.1f".format(avgSpeedKmh)} km/h</b><span>Speed</span></div>
  ${if (isLiveMode) "<div><span id='live-dot'></span><span style='color:#ef4444;font-size:10px;font-weight:700'>LIVE</span></div>" else ""}
</div>
<div id="map"></div>
<script>
var allPts = [$ptsJson];
var animate = $animateJs;
var map = L.map('map', {zoomControl:true, attributionControl:false})
           .setView([${center.lat},${center.lng}], 14);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',{maxZoom:19}).addTo(map);

// ── Gradient polyline (dark blue → bright blue → cyan) ──────────────────────
var segments = [];
function colorForIndex(i, total) {
  var t = total <= 1 ? 1 : i / (total - 1);
  // interpolate #1e3a5f → #3b82f6 → #06b6d4
  if (t < 0.5) {
    var r = Math.round(30  + (59  - 30)  * t * 2);
    var g = Math.round(58  + (130 - 58)  * t * 2);
    var b = Math.round(95  + (246 - 95)  * t * 2);
    return 'rgb('+r+','+g+','+b+')';
  } else {
    var r2 = Math.round(59  + (6   - 59)  * (t-0.5) * 2);
    var g2 = Math.round(130 + (182 - 130) * (t-0.5) * 2);
    var b2 = Math.round(246 + (212 - 246) * (t-0.5) * 2);
    return 'rgb('+r2+','+g2+','+b2+')';
  }
}

// Draw route — animated segment by segment if animate=true, instant if false
function drawRoute(pts, startFrom) {
  startFrom = startFrom || 0;
  if (pts.length < 2) return;
  var delay = animate ? 60 : 0;
  for (var i = Math.max(1, startFrom); i < pts.length; i++) {
    (function(idx) {
      setTimeout(function() {
        var seg = L.polyline(
          [[pts[idx-1].lat, pts[idx-1].lng],[pts[idx].lat, pts[idx].lng]],
          { color: colorForIndex(idx, pts.length), weight: 6,
            opacity: 0.95, lineJoin:'round', lineCap:'round' }
        ).addTo(map);
        segments.push(seg);
      }, animate ? idx * delay : 0);
    })(i);
  }
}

// ── Markers ───────────────────────────────────────────────────────────────────
var startIcon = L.divIcon({
  html: '<div style="width:14px;height:14px;border-radius:50%;background:#16a34a;border:3px solid #fff;box-shadow:0 2px 8px rgba(0,0,0,.5)"></div>',
  iconSize:[14,14], iconAnchor:[7,7], className:''
});

// Walking man icon — rotates to show movement direction
var carIcon = L.divIcon({
  html: '<div style="font-size:26px;filter:drop-shadow(0 2px 4px rgba(0,0,0,.5));transform:rotate(${headingDeg.toInt()}deg);transform-origin:center;line-height:1">🚶</div>',
  iconSize:[32,32], iconAnchor:[16,16], className:''
});

var endIcon = L.divIcon({
  html: '<div style="width:14px;height:14px;border-radius:50%;background:#dc2626;border:3px solid #fff;box-shadow:0 2px 8px rgba(0,0,0,.5)"></div>',
  iconSize:[14,14], iconAnchor:[7,7], className:''
});

// Start marker — always static
var startMarker = L.marker([allPts[0].lat, allPts[0].lng], {icon: startIcon})
  .addTo(map)
  .bindPopup('<b>🟢 START</b><br>🕐 ' + allPts[0].t);

// Moving car marker — always at latest point
var carMarker = L.marker([allPts[allPts.length-1].lat, allPts[allPts.length-1].lng], {icon: carIcon})
  .addTo(map);

// Intermediate dot markers + bearing helper for live updates
function bearingBetween(p1, p2) {
  var lat1 = p1.lat * Math.PI/180, lat2 = p2.lat * Math.PI/180;
  var dLng = (p2.lng - p1.lng) * Math.PI/180;
  var y = Math.sin(dLng) * Math.cos(lat2);
  var x = Math.cos(lat1)*Math.sin(lat2) - Math.sin(lat1)*Math.cos(lat2)*Math.cos(dLng);
  return (Math.atan2(y, x) * 180/Math.PI + 360) % 360;
}
for (var i = 1; i < allPts.length - 1; i++) {
  (function(idx) {
    L.circleMarker([allPts[idx].lat, allPts[idx].lng], {
      radius:5, color:'#fff', weight:1.5,
      fillColor: colorForIndex(idx, allPts.length), fillOpacity:1
    }).addTo(map).bindPopup('<b>Point '+(idx+1)+'</b><br>🕐 '+allPts[idx].t);
  })(i);
}

// End marker (if not live / not last point being tracked)
${if (!isLiveMode) """
var endMarker = L.marker([allPts[allPts.length-1].lat, allPts[allPts.length-1].lng], {icon: endIcon})
  .addTo(map)
  .bindPopup('<b>🔴 END</b><br>🕐 ' + allPts[allPts.length-1].t);
""" else ""}

// Draw all segments
drawRoute(allPts, 1);

// Fit map to full route
var latlngs = allPts.map(function(p){ return [p.lat, p.lng]; });
map.fitBounds(L.latLngBounds(latlngs), {padding:[20,20]});

// ── Live update function (called by Android via evaluateJavascript) ───────────
function addNewPoints(newPtsJson) {
  var newPts = JSON.parse(newPtsJson);
  if (newPts.length <= allPts.length) return; // no new points
  var prevLen = allPts.length;
  allPts = newPts;

  // Move walking man marker to latest point, update rotation bearing
  var latest = allPts[allPts.length - 1];
  var prev   = allPts[allPts.length - 2];
  var newDeg = prev ? bearingBetween(prev, latest) : 0;
  var newIcon = L.divIcon({
    html: '<div style="font-size:26px;filter:drop-shadow(0 2px 4px rgba(0,0,0,.5));transform:rotate(' + newDeg + 'deg);transform-origin:center;line-height:1">🚶</div>',
    iconSize:[32,32], iconAnchor:[16,16], className:''
  });
  carMarker.setLatLng([latest.lat, latest.lng]);
  carMarker.setIcon(newIcon);
  carMarker.bindPopup('<b>📍 Current</b><br>🕐 ' + latest.t);

  // Draw only the new segments (from where we left off)
  drawRoute(allPts, prevLen);

  // Pan map to latest point smoothly
  map.panTo([latest.lat, latest.lng], {animate:true, duration:0.8});
}
</script>
</body></html>"""

        webMap.loadDataWithBaseURL("https://unpkg.com", html, "text/html", "UTF-8", null)

        // ── Timeline ──────────────────────────────────────────────────────────
        cardTimeline.visibility = View.VISIBLE
        llTimeline.removeAllViews()
        var cumKm = 0.0

        pts.forEachIndexed { i, pt ->
            val isFirst = i == 0; val isLast = i == pts.size - 1
            val segKm   = if (i > 0) haversine(pts[i-1].lat, pts[i-1].lng, pt.lat, pt.lng) else 0.0
            cumKm += segKm

            val dotColor = when {
                isFirst -> ContextCompat.getColor(ctx, R.color.primary)
                isLast  -> ContextCompat.getColor(ctx, R.color.accent_red)
                else    -> ContextCompat.getColor(ctx, R.color.accent_blue)
            }

            val row = LinearLayout(ctx).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(px(4,dp), px(8,dp), px(4,dp), px(8,dp))
                setBackgroundColor(
                    if (i%2==0) android.graphics.Color.WHITE
                    else ContextCompat.getColor(ctx, R.color.background)
                )
            }

            val dotCol = LinearLayout(ctx).apply {
                orientation = LinearLayout.VERTICAL
                gravity = android.view.Gravity.TOP or android.view.Gravity.CENTER_HORIZONTAL
                layoutParams = llp(px(20,dp), WRAP)
                setPadding(0, px(3,dp), 0, 0)
            }
            dotCol.addView(View(ctx).apply {
                val sz = if (isFirst||isLast) px(12,dp) else px(8,dp)
                layoutParams = LinearLayout.LayoutParams(sz,sz)
                background = android.graphics.drawable.GradientDrawable().apply {
                    shape = android.graphics.drawable.GradientDrawable.OVAL
                    setColor(dotColor)
                }
            })
            if (!isLast) dotCol.addView(View(ctx).apply {
                layoutParams = llp(px(2,dp), px(24,dp)).also {
                    it.topMargin = px(2,dp)
                    it.gravity = android.view.Gravity.CENTER_HORIZONTAL
                }
                setBackgroundColor(ContextCompat.getColor(ctx, R.color.divider))
            })
            row.addView(dotCol)

            val info = vLL(ctx).apply {
                layoutParams = llp(0, WRAP, 1f)
                setPadding(px(8,dp), 0, 0, 0)
            }
            val badge = when {
                isFirst -> "🟢 START"
                isLast && isLiveMode -> "📍 NOW"
                isLast -> "🔴 END"
                else -> "·  Point ${i+1}"
            }
            info.addView(LinearLayout(ctx).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = android.view.Gravity.CENTER_VERTICAL
                addView(TextView(ctx).apply {
                    text = pt.timeLabel; textSize = 13f
                    setTypeface(null, android.graphics.Typeface.BOLD)
                    setTextColor(ContextCompat.getColor(ctx, R.color.text_primary))
                    layoutParams = llp(0, WRAP, 1f)
                })
                addView(TextView(ctx).apply {
                    text = badge; textSize = 10f; setTextColor(dotColor)
                    setTypeface(null, android.graphics.Typeface.BOLD)
                })
            })
            info.addView(TextView(ctx).apply {
                text = if (i==0) "Starting point"
                else "+${"%.3f".format(segKm)} km  ·  total ${"%.2f".format(cumKm)} km"
                textSize = 11f
                setTextColor(ContextCompat.getColor(ctx, R.color.text_secondary))
                setPadding(0, px(2,dp), 0, 0)
            })
            pt.accuracy?.let {
                info.addView(TextView(ctx).apply {
                    text = "GPS accuracy ±${"%.0f".format(it)}m"
                    textSize = 10f
                    setTextColor(ContextCompat.getColor(ctx, R.color.text_hint))
                })
            }
            row.addView(info)
            llTimeline.addView(row)
        }
    }

    // ── Maths ─────────────────────────────────────────────────────────────────

    private fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat/2).let{it*it} +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon/2).let{it*it}
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
    }

    /** Compass bearing in degrees from point 1 → point 2 */
    private fun bearing(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val dLon = Math.toRadians(lon2 - lon1)
        val lat1R = Math.toRadians(lat1)
        val lat2R = Math.toRadians(lat2)
        val y = Math.sin(dLon) * Math.cos(lat2R)
        val x = Math.cos(lat1R) * Math.sin(lat2R) - Math.sin(lat1R) * Math.cos(lat2R) * Math.cos(dLon)
        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360
    }

    // ── Layout helpers ────────────────────────────────────────────────────────

    private fun px(n: Int, dp: Float) = (n * dp).toInt()
    private fun vLL(ctx: android.content.Context) = LinearLayout(ctx).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = llp(MATCH, WRAP)
    }
    private fun llp(w: Int, h: Int, weight: Float = 0f) = LinearLayout.LayoutParams(w, h, weight)
    private fun lbl(ctx: android.content.Context, t: String, dp: Float) =
        TextView(ctx).apply {
            text = t; textSize = 11f
            setTextColor(ContextCompat.getColor(ctx, R.color.text_secondary))
            setPadding(0, 0, 0, px(3,dp))
        }
    private fun makeCard(ctx: android.content.Context, dp: Float) = CardView(ctx).apply {
        radius = 14 * dp; cardElevation = 3 * dp
        setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.surface))
        layoutParams = llp(MATCH, WRAP).also { it.bottomMargin = px(12,dp) }
    }
    private fun todayIST() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        .also { it.timeZone = TimeZone.getTimeZone("Asia/Kolkata") }.format(Date())

    companion object {
        private const val MATCH = ViewGroup.LayoutParams.MATCH_PARENT
        private const val WRAP  = ViewGroup.LayoutParams.WRAP_CONTENT
    }
}