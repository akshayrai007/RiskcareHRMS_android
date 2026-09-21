package com.riskcare.app.ui.attendance;

/**
 * BeatPlanFragment — Feature #7 Beat Plan / PJP
 *
 * Shows:
 * - Employee + date selector
 * - Planned stops list (numbered pins on map)
 * - Actual GPS route overlaid on the same map
 * - Coverage summary: X/Y stops visited, coverage %
 *
 * Map layers:
 * • Numbered blue markers   — planned stops
 * • Green/red dot per stop  — visited (green) or missed (red)
 * • Orange polyline         — actual GPS route
 * • Coverage bar            — % of planned stops reached
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00ce\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u0000 ^2\u00020\u00012\u00020\u0002:\u0001^B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010+\u001a\u00020,H\u0002J\u0016\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\f2\u0006\u0010/\u001a\u00020#H\u0002J\b\u00100\u001a\u000201H\u0002J$\u00102\u001a\b\u0012\u0004\u0012\u00020.0\f2\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u000204H\u0082@\u00a2\u0006\u0002\u00106J\b\u00107\u001a\u000201H\u0002J \u00108\u001a\u00020*2\u0006\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020#2\u0006\u0010<\u001a\u00020=H\u0002J\"\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020A2\b\b\u0002\u0010C\u001a\u00020=H\u0002J\b\u0010D\u001a\u000201H\u0002J\u0018\u0010E\u001a\u00020\b2\u0006\u00109\u001a\u00020:2\u0006\u0010<\u001a\u00020=H\u0002J$\u0010F\u001a\u00020,2\u0006\u0010G\u001a\u00020H2\b\u0010I\u001a\u0004\u0018\u00010J2\b\u0010K\u001a\u0004\u0018\u00010LH\u0016J\b\u0010M\u001a\u000201H\u0016J\u0010\u0010N\u001a\u0002012\u0006\u0010O\u001a\u00020\u000fH\u0016J\u001a\u0010P\u001a\u0002012\u0006\u0010Q\u001a\u00020,2\b\u0010R\u001a\u0004\u0018\u00010LH\u0016J\b\u0010S\u001a\u000201H\u0002J\u0018\u0010T\u001a\u00020A2\u0006\u0010U\u001a\u00020A2\u0006\u0010<\u001a\u00020=H\u0002J\u0010\u0010V\u001a\u0002012\u0006\u0010W\u001a\u00020!H\u0002J\u0010\u0010X\u001a\u0002012\u0006\u0010W\u001a\u00020!H\u0002J\u0010\u0010Y\u001a\u0002012\u0006\u0010W\u001a\u00020!H\u0002J\u0010\u0010Z\u001a\u0002012\u0006\u0010[\u001a\u00020#H\u0002J\u0010\u0010\\\u001a\n $*\u0004\u0018\u00010#0#H\u0002J\u0010\u0010]\u001a\u00020\u00112\u0006\u00109\u001a\u00020:H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\"\u001a\n $*\u0004\u0018\u00010#0#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020(X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006_"}, d2 = {"Lcom/riskcare/app/ui/attendance/BeatPlanFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "()V", "btnDate", "Landroid/widget/Button;", "btnSearch", "cardMap", "Landroidx/cardview/widget/CardView;", "cardStops", "cardSummary", "employees", "", "Lcom/riskcare/app/data/models/Employee;", "googleMap", "Lcom/google/android/gms/maps/GoogleMap;", "llStops", "Landroid/widget/LinearLayout;", "llSummary", "mapCircles", "", "Lcom/google/android/gms/maps/model/Circle;", "mapFragment", "Lcom/google/android/gms/maps/SupportMapFragment;", "mapMarkers", "Lcom/google/android/gms/maps/model/Marker;", "mapPolylines", "Lcom/google/android/gms/maps/model/Polyline;", "mapReady", "", "mapScope", "Lkotlinx/coroutines/CoroutineScope;", "pendingCompare", "Lcom/riskcare/app/data/models/BeatPlanCompare;", "selectedDate", "", "kotlin.jvm.PlatformType", "session", "Lcom/riskcare/app/utils/SessionManager;", "spinnerEmp", "Landroid/widget/Spinner;", "tvStatus", "Landroid/widget/TextView;", "buildUI", "Landroid/view/View;", "decodePoly", "Lcom/google/android/gms/maps/model/LatLng;", "encoded", "doLoad", "", "fetchSegment", "from", "Lcom/riskcare/app/data/models/MovementPoint;", "to", "(Lcom/riskcare/app/data/models/MovementPoint;Lcom/riskcare/app/data/models/MovementPoint;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "initMap", "lbl", "ctx", "Landroid/content/Context;", "t", "dp", "", "llp", "Landroid/widget/LinearLayout$LayoutParams;", "w", "", "h", "weight", "loadEmployees", "makeCard", "onCreateView", "i", "Landroid/view/LayoutInflater;", "c", "Landroid/view/ViewGroup;", "s", "Landroid/os/Bundle;", "onDestroyView", "onMapReady", "map", "onViewCreated", "view", "savedInstanceState", "pickDate", "px", "n", "render", "compare", "renderStops", "renderSummary", "toast", "msg", "todayIST", "vLL", "Companion", "app_debug"})
public final class BeatPlanFragment extends androidx.fragment.app.Fragment implements com.google.android.gms.maps.OnMapReadyCallback {
    private com.riskcare.app.utils.SessionManager session;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.riskcare.app.data.models.Employee> employees;
    private java.lang.String selectedDate;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.GoogleMap googleMap;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.SupportMapFragment mapFragment;
    private boolean mapReady = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.google.android.gms.maps.model.Marker> mapMarkers = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.google.android.gms.maps.model.Polyline> mapPolylines = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.google.android.gms.maps.model.Circle> mapCircles = null;
    @org.jetbrains.annotations.Nullable()
    private com.riskcare.app.data.models.BeatPlanCompare pendingCompare;
    private android.widget.Spinner spinnerEmp;
    private android.widget.Button btnDate;
    private android.widget.Button btnSearch;
    private android.widget.TextView tvStatus;
    private androidx.cardview.widget.CardView cardMap;
    private androidx.cardview.widget.CardView cardSummary;
    private androidx.cardview.widget.CardView cardStops;
    private android.widget.LinearLayout llSummary;
    private android.widget.LinearLayout llStops;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope mapScope = null;
    private static final int MATCH = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
    private static final int WRAP = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
    @org.jetbrains.annotations.NotNull()
    public static final com.riskcare.app.ui.attendance.BeatPlanFragment.Companion Companion = null;
    
    public BeatPlanFragment() {
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
    public void onDestroyView() {
    }
    
    private final void initMap() {
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
    
    private final void doLoad() {
    }
    
    private final void renderSummary(com.riskcare.app.data.models.BeatPlanCompare compare) {
    }
    
    private final void render(com.riskcare.app.data.models.BeatPlanCompare compare) {
    }
    
    private final void renderStops(com.riskcare.app.data.models.BeatPlanCompare compare) {
    }
    
    private final java.lang.Object fetchSegment(com.riskcare.app.data.models.MovementPoint from, com.riskcare.app.data.models.MovementPoint to, kotlin.coroutines.Continuation<? super java.util.List<com.google.android.gms.maps.model.LatLng>> $completion) {
        return null;
    }
    
    private final java.util.List<com.google.android.gms.maps.model.LatLng> decodePoly(java.lang.String encoded) {
        return null;
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/riskcare/app/ui/attendance/BeatPlanFragment$Companion;", "", "()V", "MATCH", "", "WRAP", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}