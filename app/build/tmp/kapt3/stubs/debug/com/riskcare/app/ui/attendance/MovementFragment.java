package com.riskcare.app.ui.attendance;

/**
 * MovementFragment v2 — Full-featured employee movement tracking
 *
 * Features:
 * 1. Single employee route — road-snapped polyline via Google Directions API
 * 2. Speed heatmap — polyline segments coloured green/orange/red by speed
 * 3. Route replay animation — animated marker walks the route
 * 4. Multi-employee live map — all field staff dots on one map (admin view)
 * 5. Offline queue badge — shows how many points are pending sync
 * 6. Tracking transparency banner — "🟢 GPS Active" shown when service running
 * 7. Low battery / signal loss indicator on timeline
 *
 * Map layers (single employee mode):
 * • Speed-coloured polyline segments (green < 30 km/h, orange 30–80, red > 80)
 * • Blue circles  — real GPS points
 * • Orange circles — 500m road marks
 * • Green marker  — START
 * • Red marker    — END
 * • Cyan marker   — current position (live mode)
 * • Animated replay marker — route replay mode
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00f8\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001e\u0018\u0000 \u0090\u00012\u00020\u00012\u00020\u0002:\u0004\u0090\u0001\u0091\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010B\u001a\u00020CH\u0002J\b\u0010D\u001a\u00020EH\u0002J\u001c\u0010F\u001a\b\u0012\u0004\u0012\u00020G0\u00102\f\u0010H\u001a\b\u0012\u0004\u0012\u00020/0\u0010H\u0002J\u0016\u0010I\u001a\b\u0012\u0004\u0012\u00020J0\u00102\u0006\u0010K\u001a\u000206H\u0002J\b\u0010L\u001a\u00020EH\u0002J*\u0010M\u001a\u00020E2\u0006\u0010N\u001a\u00020\u00112\u0006\u0010O\u001a\u0002062\u0006\u0010P\u001a\u00020\u00182\b\b\u0002\u0010Q\u001a\u00020\u0018H\u0002J$\u0010R\u001a\b\u0012\u0004\u0012\u00020J0\u00102\u0006\u0010S\u001a\u00020/2\u0006\u0010T\u001a\u00020/H\u0082@\u00a2\u0006\u0002\u0010UJ(\u0010V\u001a\u00020G2\u0006\u0010W\u001a\u00020G2\u0006\u0010X\u001a\u00020G2\u0006\u0010Y\u001a\u00020G2\u0006\u0010Z\u001a\u00020GH\u0002J\u0018\u0010[\u001a\u00020G2\u0006\u0010\\\u001a\u00020J2\u0006\u0010]\u001a\u00020JH\u0002J\u0012\u0010^\u001a\u00020E2\b\u0010_\u001a\u0004\u0018\u00010`H\u0002J\u001c\u0010a\u001a\b\u0012\u0004\u0012\u00020J0\u00102\f\u0010b\u001a\b\u0012\u0004\u0012\u00020J0\u0010H\u0002J \u0010c\u001a\u00020<2\u0006\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u0002062\u0006\u0010g\u001a\u00020hH\u0002J\"\u0010i\u001a\u00020j2\u0006\u0010k\u001a\u00020\u000e2\u0006\u0010l\u001a\u00020\u000e2\b\b\u0002\u0010m\u001a\u00020hH\u0002J\b\u0010n\u001a\u00020EH\u0002J\u0018\u0010o\u001a\u00020\n2\u0006\u0010d\u001a\u00020e2\u0006\u0010g\u001a\u00020hH\u0002J$\u0010p\u001a\u00020C2\u0006\u0010q\u001a\u00020r2\b\u0010s\u001a\u0004\u0018\u00010t2\b\u0010u\u001a\u0004\u0018\u00010`H\u0016J\b\u0010v\u001a\u00020EH\u0016J\u0010\u0010w\u001a\u00020E2\u0006\u0010x\u001a\u00020\u0013H\u0016J\b\u0010y\u001a\u00020EH\u0016J\b\u0010z\u001a\u00020EH\u0016J\u001a\u0010{\u001a\u00020E2\u0006\u0010|\u001a\u00020C2\b\u0010}\u001a\u0004\u0018\u00010`H\u0016J\b\u0010~\u001a\u00020EH\u0002J\u0019\u0010\u007f\u001a\u00020\u000e2\u0007\u0010\u0080\u0001\u001a\u00020\u000e2\u0006\u0010g\u001a\u00020hH\u0002J\t\u0010\u0081\u0001\u001a\u00020EH\u0002J\t\u0010\u0082\u0001\u001a\u00020EH\u0002J\t\u0010\u0083\u0001\u001a\u00020EH\u0002J\t\u0010\u0084\u0001\u001a\u00020EH\u0002J\u001f\u0010\u0085\u0001\u001a\u00020E2\f\u0010H\u001a\b\u0012\u0004\u0012\u00020/0\u00102\u0006\u0010P\u001a\u00020\u0018H\u0002J\'\u0010\u0086\u0001\u001a\u00020E2\u0006\u0010N\u001a\u00020\u00112\f\u0010H\u001a\b\u0012\u0004\u0012\u00020/0\u00102\u0006\u0010P\u001a\u00020\u0018H\u0002J\u0012\u0010\u0087\u0001\u001a\u00020\u000e2\u0007\u0010\u0088\u0001\u001a\u00020GH\u0002J\u0012\u0010\u0089\u0001\u001a\u00020E2\u0007\u0010\u008a\u0001\u001a\u000206H\u0002J\u0012\u0010\u008b\u0001\u001a\u000b \u008c\u0001*\u0004\u0018\u00010606H\u0002J\t\u0010\u008d\u0001\u001a\u00020EH\u0002J\t\u0010\u008e\u0001\u001a\u00020EH\u0002J\u0011\u0010\u008f\u0001\u001a\u00020!2\u0006\u0010d\u001a\u00020eH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u001d\u001a\u00060\u001ej\u0002`\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\'X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020)0+X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010.\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u00100\u001a\b\u0012\u0004\u0012\u0002010\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00104\u001a\u0004\u0018\u00010)X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000206X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000208X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020:X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020<X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020<X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020<X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020<X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020AX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0092\u0001"}, d2 = {"Lcom/riskcare/app/ui/attendance/MovementFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "()V", "btnDate", "Landroid/widget/Button;", "btnMultiLive", "btnReplay", "btnSearch", "cardControls", "Landroidx/cardview/widget/CardView;", "cardMap", "cardTimeline", "currentEmpId", "", "employees", "", "Lcom/riskcare/app/data/models/Employee;", "googleMap", "Lcom/google/android/gms/maps/GoogleMap;", "gpsDots", "", "Lcom/google/android/gms/maps/model/Circle;", "isLiveMode", "", "isReplayRunning", "lastPointCount", "liveHandler", "Landroid/os/Handler;", "liveRunnable", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "llSummaryCards", "Landroid/widget/LinearLayout;", "llTimeline", "mapFragment", "Lcom/google/android/gms/maps/SupportMapFragment;", "mapReady", "mapScope", "Lkotlinx/coroutines/CoroutineScope;", "markers", "Lcom/google/android/gms/maps/model/Marker;", "multiDots", "", "orangeDots", "pendingAnimate", "pendingPoints", "Lcom/riskcare/app/data/models/MovementPoint;", "polylines", "Lcom/google/android/gms/maps/model/Polyline;", "replayAnimator", "Landroid/animation/ValueAnimator;", "replayMarker", "selectedDate", "", "session", "Lcom/riskcare/app/utils/SessionManager;", "spinnerEmp", "Landroid/widget/Spinner;", "tvLiveTag", "Landroid/widget/TextView;", "tvOfflineQueue", "tvStatus", "tvTrackingBanner", "viewMode", "Lcom/riskcare/app/ui/attendance/MovementFragment$ViewMode;", "buildUI", "Landroid/view/View;", "clearMapOverlays", "", "computeSegmentSpeeds", "", "pts", "decodePoly", "Lcom/google/android/gms/maps/model/LatLng;", "encoded", "doSearch", "fetchAndRender", "emp", "date", "animate", "silent", "fetchDirectionsSegment", "from", "to", "(Lcom/riskcare/app/data/models/MovementPoint;Lcom/riskcare/app/data/models/MovementPoint;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "haversine", "lat1", "lon1", "lat2", "lon2", "haversineM", "a", "b", "initMapFragment", "savedState", "Landroid/os/Bundle;", "interpolate500m", "coords", "lbl", "ctx", "Landroid/content/Context;", "t", "dp", "", "llp", "Landroid/widget/LinearLayout$LayoutParams;", "w", "h", "weight", "loadEmployees", "makeCard", "onCreateView", "i", "Landroid/view/LayoutInflater;", "c", "Landroid/view/ViewGroup;", "s", "onDestroyView", "onMapReady", "map", "onPause", "onResume", "onViewCreated", "view", "savedInstanceState", "pickDate", "px", "n", "refreshLiveRoute", "refreshMultiLiveMap", "refreshOfflineQueueBadge", "refreshTrackingBanner", "renderOnMap", "renderResults", "speedToColor", "kmh", "toast", "msg", "todayIST", "kotlin.jvm.PlatformType", "toggleMultiLiveMode", "toggleReplay", "vLL", "Companion", "ViewMode", "app_debug"})
public final class MovementFragment extends androidx.fragment.app.Fragment implements com.google.android.gms.maps.OnMapReadyCallback {
    @org.jetbrains.annotations.NotNull()
    private com.riskcare.app.ui.attendance.MovementFragment.ViewMode viewMode = com.riskcare.app.ui.attendance.MovementFragment.ViewMode.SINGLE;
    private com.riskcare.app.utils.SessionManager session;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.riskcare.app.data.models.Employee> employees;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String selectedDate;
    private boolean isLiveMode = false;
    private int lastPointCount = 0;
    private int currentEmpId = -1;
    private boolean isReplayRunning = false;
    @org.jetbrains.annotations.Nullable()
    private android.animation.ValueAnimator replayAnimator;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.GoogleMap googleMap;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.SupportMapFragment mapFragment;
    private boolean mapReady = false;
    @org.jetbrains.annotations.Nullable()
    private java.util.List<com.riskcare.app.data.models.MovementPoint> pendingPoints;
    private boolean pendingAnimate = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.google.android.gms.maps.model.Polyline> polylines = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.google.android.gms.maps.model.Circle> orangeDots = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.google.android.gms.maps.model.Circle> gpsDots = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.google.android.gms.maps.model.Marker> markers = null;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.model.Marker replayMarker;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.Integer, com.google.android.gms.maps.model.Marker> multiDots = null;
    private android.widget.Spinner spinnerEmp;
    private android.widget.Button btnDate;
    private android.widget.Button btnSearch;
    private android.widget.Button btnReplay;
    private android.widget.Button btnMultiLive;
    private android.widget.TextView tvStatus;
    private android.widget.TextView tvLiveTag;
    private android.widget.TextView tvTrackingBanner;
    private android.widget.TextView tvOfflineQueue;
    private android.widget.LinearLayout llSummaryCards;
    private android.widget.LinearLayout llTimeline;
    private androidx.cardview.widget.CardView cardMap;
    private androidx.cardview.widget.CardView cardTimeline;
    private androidx.cardview.widget.CardView cardControls;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler liveHandler = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable liveRunnable = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope mapScope = null;
    private static final int MATCH = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
    private static final int WRAP = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
    @org.jetbrains.annotations.NotNull()
    public static final com.riskcare.app.ui.attendance.MovementFragment.Companion Companion = null;
    
    public MovementFragment() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater i, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup c, @org.jetbrains.annotations.Nullable()
    android.os.Bundle s) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    private final void refreshTrackingBanner() {
    }
    
    private final void refreshOfflineQueueBadge() {
    }
    
    private final void initMapFragment(android.os.Bundle savedState) {
    }
    
    @java.lang.Override()
    public void onMapReady(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap map) {
    }
    
    private final android.view.View buildUI() {
        return null;
    }
    
    private final void loadEmployees() {
    }
    
    private final void pickDate() {
    }
    
    private final void toggleMultiLiveMode() {
    }
    
    private final void refreshMultiLiveMap() {
    }
    
    private final void doSearch() {
    }
    
    private final void refreshLiveRoute() {
    }
    
    private final void fetchAndRender(com.riskcare.app.data.models.Employee emp, java.lang.String date, boolean animate, boolean silent) {
    }
    
    private final void renderResults(com.riskcare.app.data.models.Employee emp, java.util.List<com.riskcare.app.data.models.MovementPoint> pts, boolean animate) {
    }
    
    private final int speedToColor(double kmh) {
        return 0;
    }
    
    private final java.util.List<java.lang.Double> computeSegmentSpeeds(java.util.List<com.riskcare.app.data.models.MovementPoint> pts) {
        return null;
    }
    
    private final void renderOnMap(java.util.List<com.riskcare.app.data.models.MovementPoint> pts, boolean animate) {
    }
    
    private final void toggleReplay() {
    }
    
    private final java.lang.Object fetchDirectionsSegment(com.riskcare.app.data.models.MovementPoint from, com.riskcare.app.data.models.MovementPoint to, kotlin.coroutines.Continuation<? super java.util.List<com.google.android.gms.maps.model.LatLng>> $completion) {
        return null;
    }
    
    private final java.util.List<com.google.android.gms.maps.model.LatLng> decodePoly(java.lang.String encoded) {
        return null;
    }
    
    private final java.util.List<com.google.android.gms.maps.model.LatLng> interpolate500m(java.util.List<com.google.android.gms.maps.model.LatLng> coords) {
        return null;
    }
    
    private final void clearMapOverlays() {
    }
    
    private final double haversine(double lat1, double lon1, double lat2, double lon2) {
        return 0.0;
    }
    
    private final double haversineM(com.google.android.gms.maps.model.LatLng a, com.google.android.gms.maps.model.LatLng b) {
        return 0.0;
    }
    
    private final int px(int n, float dp) {
        return 0;
    }
    
    private final android.widget.LinearLayout vLL(android.content.Context ctx) {
        return null;
    }
    
    private final android.widget.LinearLayout.LayoutParams llp(int w, int h, float weight) {
        return null;
    }
    
    private final android.widget.TextView lbl(android.content.Context ctx, java.lang.String t, float dp) {
        return null;
    }
    
    private final androidx.cardview.widget.CardView makeCard(android.content.Context ctx, float dp) {
        return null;
    }
    
    private final void toast(java.lang.String msg) {
    }
    
    private final java.lang.String todayIST() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/riskcare/app/ui/attendance/MovementFragment$Companion;", "", "()V", "MATCH", "", "WRAP", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/riskcare/app/ui/attendance/MovementFragment$ViewMode;", "", "(Ljava/lang/String;I)V", "SINGLE", "MULTI_LIVE", "app_debug"})
    static enum ViewMode {
        /*public static final*/ SINGLE /* = new SINGLE() */,
        /*public static final*/ MULTI_LIVE /* = new MULTI_LIVE() */;
        
        ViewMode() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.riskcare.app.ui.attendance.MovementFragment.ViewMode> getEntries() {
            return null;
        }
    }
}