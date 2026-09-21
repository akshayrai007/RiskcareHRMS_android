package com.riskcare.app.service;

/**
 * ─────────────────────────────────────────────────────────────────────────────
 * LocationTrackingService — Production Foreground Service
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * Architecture (punch-in only mode):
 *  Single location fetch triggered on punch-in.
 *  No background service, no boot resume, no persistent notification.
 *  Geofence tracking is unaffected — it uses its own flow.
 *
 *  LAYER 3 (SAFETY NET): SyncWorker (WorkManager, 15-min periodic)
 *                       Uploads any records stuck in offline queue
 *
 * Adaptive tracking:
 *  - Stationary (< 50m movement in last 3 pings): reduce to 60-sec interval
 *  - Moving (> 50m): use 30-sec interval
 *  - OD mode: always 30-sec (employee must be tracked precisely)
 *
 * OD auto-stop:
 *  - Schedules AlarmManager to stop at configured time (default 6:30 PM)
 *  - Checks shouldOdStopNow() on every ping
 * ─────────────────────────────────────────────────────────────────────────────
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\n\u0018\u0000 52\u00020\u0001:\u00015B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\u0018\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001dH\u0002J\b\u0010\u001f\u001a\u00020\u0018H\u0002J\u000e\u0010 \u001a\u00020\u0018H\u0082@\u00a2\u0006\u0002\u0010!J\u0018\u0010\"\u001a\u0004\u0018\u00010\r2\u0006\u0010#\u001a\u00020\u0006H\u0082@\u00a2\u0006\u0002\u0010$J\u0014\u0010%\u001a\u0004\u0018\u00010&2\b\u0010\'\u001a\u0004\u0018\u00010(H\u0016J\b\u0010)\u001a\u00020\u0018H\u0016J\b\u0010*\u001a\u00020\u0018H\u0016J\"\u0010+\u001a\u00020,2\b\u0010\'\u001a\u0004\u0018\u00010(2\u0006\u0010-\u001a\u00020,2\u0006\u0010.\u001a\u00020,H\u0016J\b\u0010/\u001a\u00020\u0018H\u0002J\b\u00100\u001a\u00020\u0018H\u0002J\u0010\u00101\u001a\u00020\u00182\u0006\u00102\u001a\u00020\rH\u0002J\u0010\u00103\u001a\u00020\u00182\u0006\u00104\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0018\u00010\u0015R\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00066"}, d2 = {"Lcom/riskcare/app/service/LocationTrackingService;", "Landroid/app/Service;", "()V", "currentIntervalMs", "", "fusedClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "isFirstPing", "", "pingJob", "Lkotlinx/coroutines/Job;", "recentLocations", "Lkotlin/collections/ArrayDeque;", "Landroid/location/Location;", "repository", "Lcom/riskcare/app/data/repository/LocationRepository;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "trackingManager", "Lcom/riskcare/app/domain/usecase/TrackingManager;", "wakeLock", "Landroid/os/PowerManager$WakeLock;", "Landroid/os/PowerManager;", "acquireWakeLock", "", "buildNotification", "Landroid/app/Notification;", "buildNotificationWithText", "title", "", "text", "createNotificationChannel", "doPing", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBestLocation", "client", "(Lcom/google/android/gms/location/FusedLocationProviderClient;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "", "flags", "startId", "releaseWakeLock", "startPingLoop", "updateAdaptiveInterval", "newLoc", "updateNotification", "loc", "Companion", "app_debug"})
public final class LocationTrackingService extends android.app.Service {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "LocationTrackingService";
    private static final long GPS_TIMEOUT_MS = 12000L;
    private static final float MAX_ACCURACY_M = 50.0F;
    private static final int STATIONARY_PING_COUNT = 3;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job pingJob;
    @org.jetbrains.annotations.Nullable()
    private android.os.PowerManager.WakeLock wakeLock;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.location.FusedLocationProviderClient fusedClient;
    private com.riskcare.app.data.repository.LocationRepository repository;
    private com.riskcare.app.domain.usecase.TrackingManager trackingManager;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.collections.ArrayDeque<android.location.Location> recentLocations = null;
    private long currentIntervalMs;
    private boolean isFirstPing = true;
    @org.jetbrains.annotations.NotNull()
    public static final com.riskcare.app.service.LocationTrackingService.Companion Companion = null;
    
    public LocationTrackingService() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    /**
     * Ping loop with adaptive interval:
     * - Immediate ping on start
     * - Adapts interval based on movement (30s moving, 60s stationary)
     * - Checks OD auto-stop condition on every cycle
     */
    private final void startPingLoop() {
    }
    
    private final java.lang.Object doPing(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Adaptive interval logic:
     * Track last N locations. If displacement between oldest and newest < threshold → stationary.
     * Stationary = reduce ping frequency to save battery.
     * OD mode always uses normal interval.
     */
    private final void updateAdaptiveInterval(android.location.Location newLoc) {
    }
    
    private final java.lang.Object getBestLocation(com.google.android.gms.location.FusedLocationProviderClient client, kotlin.coroutines.Continuation<? super android.location.Location> $completion) {
        return null;
    }
    
    private final void updateNotification(android.location.Location loc) {
    }
    
    private final void acquireWakeLock() {
    }
    
    private final void releaseWakeLock() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    private final void createNotificationChannel() {
    }
    
    private final android.app.Notification buildNotification() {
        return null;
    }
    
    private final android.app.Notification buildNotificationWithText(java.lang.String title, java.lang.String text) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u0004\u0018\u00010 J\u0010\u0010!\u001a\u00020\"2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010#\u001a\u00020\"2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u000e\u0010$\u001a\u00020%2\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010&\u001a\u00020%2\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\'\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0018\u0010(\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020*H\u0002J\u0018\u0010+\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010,\u001a\u00020%J\u0010\u0010-\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0018\u0010.\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010/\u001a\u000200J\u0010\u00101\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\n8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\n8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0013X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u00118BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lcom/riskcare/app/service/LocationTrackingService$Companion;", "", "()V", "ACTION_STOP", "", "getACTION_STOP", "()Ljava/lang/String;", "CHANNEL_ID", "getCHANNEL_ID", "GPS_TIMEOUT_MS", "", "INTERVAL_NORMAL_MS", "getINTERVAL_NORMAL_MS", "()J", "INTERVAL_STATIONARY_MS", "getINTERVAL_STATIONARY_MS", "MAX_ACCURACY_M", "", "NOTIF_ID", "", "getNOTIF_ID", "()I", "STATIONARY_PING_COUNT", "STATIONARY_THRESHOLD_M", "getSTATIONARY_THRESHOLD_M", "()F", "TAG", "cancelOdAutoStop", "", "ctx", "Landroid/content/Context;", "getBrandBatteryIntent", "Landroid/content/Intent;", "getOdStopPendingIntent", "Landroid/app/PendingIntent;", "getPendingIntent", "hasLocationPermission", "", "isRunning", "requestBatteryExemption", "scheduleOdAutoStop", "manager", "Lcom/riskcare/app/domain/usecase/TrackingManager;", "start", "isOd", "startBackgroundLocationUpdates", "stop", "reason", "Lcom/riskcare/app/domain/model/StopReason;", "stopBackgroundLocationUpdates", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        private final java.lang.String getCHANNEL_ID() {
            return null;
        }
        
        private final int getNOTIF_ID() {
            return 0;
        }
        
        private final java.lang.String getACTION_STOP() {
            return null;
        }
        
        private final long getINTERVAL_NORMAL_MS() {
            return 0L;
        }
        
        private final long getINTERVAL_STATIONARY_MS() {
            return 0L;
        }
        
        private final float getSTATIONARY_THRESHOLD_M() {
            return 0.0F;
        }
        
        public final void start(@org.jetbrains.annotations.NotNull()
        android.content.Context ctx, boolean isOd) {
        }
        
        public final void stop(@org.jetbrains.annotations.NotNull()
        android.content.Context ctx, @org.jetbrains.annotations.NotNull()
        com.riskcare.app.domain.model.StopReason reason) {
        }
        
        public final boolean isRunning(@org.jetbrains.annotations.NotNull()
        android.content.Context ctx) {
            return false;
        }
        
        private final void startBackgroundLocationUpdates(android.content.Context ctx) {
        }
        
        private final void stopBackgroundLocationUpdates(android.content.Context ctx) {
        }
        
        private final android.app.PendingIntent getPendingIntent(android.content.Context ctx) {
            return null;
        }
        
        private final void scheduleOdAutoStop(android.content.Context ctx, com.riskcare.app.domain.usecase.TrackingManager manager) {
        }
        
        private final void cancelOdAutoStop(android.content.Context ctx) {
        }
        
        private final android.app.PendingIntent getOdStopPendingIntent(android.content.Context ctx) {
            return null;
        }
        
        public final boolean hasLocationPermission(@org.jetbrains.annotations.NotNull()
        android.content.Context ctx) {
            return false;
        }
        
        public final void requestBatteryExemption(@org.jetbrains.annotations.NotNull()
        android.content.Context ctx) {
        }
        
        /**
         * Brand-specific battery/autostart settings page
         */
        @org.jetbrains.annotations.Nullable()
        public final android.content.Intent getBrandBatteryIntent() {
            return null;
        }
    }
}