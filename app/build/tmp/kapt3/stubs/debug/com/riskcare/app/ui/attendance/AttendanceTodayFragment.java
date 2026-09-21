package com.riskcare.app.ui.attendance;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00e0\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020!2\u0006\u0010;\u001a\u00020!H\u0002J\u001f\u0010<\u001a\u0004\u0018\u00010=2\u0006\u0010:\u001a\u00020!2\u0006\u0010;\u001a\u00020!H\u0002\u00a2\u0006\u0002\u0010>J\b\u0010?\u001a\u00020@H\u0002J\b\u0010A\u001a\u00020@H\u0002J\b\u0010B\u001a\u00020@H\u0002J\u0010\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FH\u0002J\b\u0010G\u001a\u00020@H\u0002J&\u0010H\u001a\u00020@2\u0006\u0010I\u001a\u00020\u00142\u0006\u0010J\u001a\u0002092\u0006\u0010K\u001a\u000209H\u0082@\u00a2\u0006\u0002\u0010LJ\b\u0010M\u001a\u00020@H\u0002J\u0010\u0010N\u001a\u00020@2\u0006\u0010I\u001a\u00020\u0014H\u0002J\u001e\u0010O\u001a\u00020@2\u0006\u0010I\u001a\u00020\u00142\u0006\u0010J\u001a\u000209H\u0082@\u00a2\u0006\u0002\u0010PJ\u0018\u0010Q\u001a\u00020@2\b\b\u0002\u0010R\u001a\u00020\nH\u0082@\u00a2\u0006\u0002\u0010SJ\u0010\u0010T\u001a\u0004\u0018\u00010UH\u0082@\u00a2\u0006\u0002\u0010VJ\b\u0010W\u001a\u00020@H\u0002J\b\u0010X\u001a\u00020@H\u0002J\u0006\u0010Y\u001a\u00020@J\u0012\u0010Z\u001a\u00020@2\b\u0010[\u001a\u0004\u0018\u00010\\H\u0016J$\u0010]\u001a\u00020^2\u0006\u0010_\u001a\u00020`2\b\u0010a\u001a\u0004\u0018\u00010b2\b\u0010c\u001a\u0004\u0018\u00010\\H\u0016J\b\u0010d\u001a\u00020@H\u0016J\u0010\u0010e\u001a\u00020@2\u0006\u0010I\u001a\u00020\u0014H\u0016J\b\u0010f\u001a\u00020@H\u0016J\b\u0010g\u001a\u00020@H\u0016J\u001a\u0010h\u001a\u00020@2\u0006\u0010i\u001a\u00020^2\b\u0010[\u001a\u0004\u0018\u00010\\H\u0016J\u0018\u0010j\u001a\u00020@2\u0006\u0010k\u001a\u00020!2\u0006\u0010l\u001a\u00020!H\u0002J\u000e\u0010m\u001a\u00020@H\u0082@\u00a2\u0006\u0002\u0010VJ\b\u0010n\u001a\u00020@H\u0002J\b\u0010o\u001a\u00020@H\u0002J\b\u0010p\u001a\u00020@H\u0002J\u0010\u0010q\u001a\u00020@2\u0006\u0010r\u001a\u00020\nH\u0002J\b\u0010s\u001a\u00020@H\u0002J\b\u0010t\u001a\u00020@H\u0002J\u0012\u0010u\u001a\u00020@2\b\u0010v\u001a\u0004\u0018\u00010wH\u0002J\b\u0010x\u001a\u00020@H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0015\u001a\u0010\u0012\f\u0012\n \u0018*\u0004\u0018\u00010\u00170\u00170\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\"R\u0012\u0010#\u001a\u0004\u0018\u00010!X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\"R\u000e\u0010$\u001a\u00020%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020(0\'X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010/\u001a\b\u0012\u0004\u0012\u00020\r0\'X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00100\u001a\b\u0012\u0004\u0012\u0002010\'X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00102\u001a\b\u0012\u0004\u0012\u0002030\'X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000206X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006y"}, d2 = {"Lcom/riskcare/app/ui/attendance/AttendanceTodayFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "()V", "_b", "Lcom/riskcare/app/databinding/FragmentAttendanceTodayBinding;", "binding", "getBinding", "()Lcom/riskcare/app/databinding/FragmentAttendanceTodayBinding;", "boundaryDrawn", "", "cameraInitialized", "empDotMarker", "Lcom/google/android/gms/maps/model/Marker;", "employeeBufferRule", "Lcom/riskcare/app/data/models/BufferRuleResponse;", "geofenceLocations", "", "Lcom/riskcare/app/data/models/MyGeofenceLocation;", "googleMap", "Lcom/google/android/gms/maps/GoogleMap;", "gpsSettingsLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroidx/activity/result/IntentSenderRequest;", "kotlin.jvm.PlatformType", "hasPunchedIn", "hasPunchedOut", "isInsideBuffer", "isPunching", "isUserTouching", "lastDistrictRing", "Lcom/google/android/gms/maps/model/LatLng;", "lastEmployeeLat", "", "Ljava/lang/Double;", "lastEmployeeLng", "lastValidatTime", "", "mapCircles", "", "Lcom/google/android/gms/maps/model/Circle;", "mapFragment", "Lcom/google/android/gms/maps/SupportMapFragment;", "mapLocationHandler", "Landroid/os/Handler;", "mapLocationRunnable", "Ljava/lang/Runnable;", "mapMarkers", "mapPolygons", "Lcom/google/android/gms/maps/model/Polygon;", "mapPolylines", "Lcom/google/android/gms/maps/model/Polyline;", "mapReady", "permissionManager", "Lcom/riskcare/app/permission/PermissionManager;", "userHasInteracted", "buildDistanceLabel", "", "empLat", "empLng", "calcDistanceToDistrictBoundary", "", "(DD)Ljava/lang/Float;", "checkAndRequestAllPermissions", "", "checkGpsAndPunch", "clearMapOverlays", "createBlueDotBitmap", "Landroid/graphics/Bitmap;", "sizePx", "", "doPunch", "drawDistrictPolygon", "map", "state", "district", "(Lcom/google/android/gms/maps/GoogleMap;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "drawGeofenceOnMap", "drawOfficeCircles", "drawStatePolygons", "(Lcom/google/android/gms/maps/GoogleMap;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "executePunch", "noLocation", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLocation", "Landroid/location/Location;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "initMapFragment", "loadGeofenceMap", "loadToday", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "i", "Landroid/view/LayoutInflater;", "c", "Landroid/view/ViewGroup;", "s", "onDestroyView", "onMapReady", "onPause", "onResume", "onViewCreated", "view", "placeEmployeeDot", "lat", "lng", "refreshEmployeeDotOnMap", "setupLateNotice", "setupMapUiLabels", "showLateNoticeDialog", "showOnsiteTrackingPrompt", "isOd", "showTrackingSetupDialogIfNeeded", "updatePunchButtons", "updateUI", "att", "Lcom/riskcare/app/data/models/AttendanceRecord;", "zoomToOfficeBuffer", "app_debug"})
public final class AttendanceTodayFragment extends androidx.fragment.app.Fragment implements com.google.android.gms.maps.OnMapReadyCallback {
    @org.jetbrains.annotations.Nullable()
    private com.riskcare.app.databinding.FragmentAttendanceTodayBinding _b;
    private boolean hasPunchedIn = false;
    private boolean hasPunchedOut = false;
    private boolean isPunching = false;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.GoogleMap googleMap;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.SupportMapFragment mapFragment;
    private boolean mapReady = false;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.riskcare.app.data.models.MyGeofenceLocation> geofenceLocations;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Double lastEmployeeLat;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Double lastEmployeeLng;
    private boolean isInsideBuffer = false;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.model.Marker empDotMarker;
    private boolean cameraInitialized = false;
    private boolean isUserTouching = false;
    private boolean userHasInteracted = false;
    private long lastValidatTime = 0L;
    private boolean boundaryDrawn = false;
    @org.jetbrains.annotations.Nullable()
    private java.util.List<com.google.android.gms.maps.model.LatLng> lastDistrictRing;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.google.android.gms.maps.model.Polygon> mapPolygons = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.google.android.gms.maps.model.Polyline> mapPolylines = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.google.android.gms.maps.model.Circle> mapCircles = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.google.android.gms.maps.model.Marker> mapMarkers = null;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler mapLocationHandler = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable mapLocationRunnable = null;
    private com.riskcare.app.permission.PermissionManager permissionManager;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<androidx.activity.result.IntentSenderRequest> gpsSettingsLauncher = null;
    @org.jetbrains.annotations.Nullable()
    private com.riskcare.app.data.models.BufferRuleResponse employeeBufferRule;
    
    public AttendanceTodayFragment() {
        super();
    }
    
    private final com.riskcare.app.databinding.FragmentAttendanceTodayBinding getBinding() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
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
    
    private final void setupLateNotice() {
    }
    
    private final void showLateNoticeDialog() {
    }
    
    private final void initMapFragment() {
    }
    
    @java.lang.Override()
    public void onMapReady(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap map) {
    }
    
    private final void loadGeofenceMap() {
    }
    
    private final void setupMapUiLabels() {
    }
    
    private final void clearMapOverlays() {
    }
    
    private final void drawGeofenceOnMap() {
    }
    
    private final void drawOfficeCircles(com.google.android.gms.maps.GoogleMap map) {
    }
    
    private final java.lang.Object drawDistrictPolygon(com.google.android.gms.maps.GoogleMap map, java.lang.String state, java.lang.String district, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object drawStatePolygons(com.google.android.gms.maps.GoogleMap map, java.lang.String state, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final android.graphics.Bitmap createBlueDotBitmap(int sizePx) {
        return null;
    }
    
    private final void placeEmployeeDot(double lat, double lng) {
    }
    
    private final void zoomToOfficeBuffer() {
    }
    
    private final java.lang.Object refreshEmployeeDotOnMap(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Float calcDistanceToDistrictBoundary(double empLat, double empLng) {
        return null;
    }
    
    private final java.lang.String buildDistanceLabel(double empLat, double empLng) {
        return null;
    }
    
    public final void loadToday() {
    }
    
    private final void updateUI(com.riskcare.app.data.models.AttendanceRecord att) {
    }
    
    private final void updatePunchButtons() {
    }
    
    private final void doPunch() {
    }
    
    private final void checkGpsAndPunch() {
    }
    
    private final java.lang.Object executePunch(boolean noLocation, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object getLocation(kotlin.coroutines.Continuation<? super android.location.Location> $completion) {
        return null;
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    private final void checkAndRequestAllPermissions() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    private final void showOnsiteTrackingPrompt(boolean isOd) {
    }
    
    private final void showTrackingSetupDialogIfNeeded() {
    }
}