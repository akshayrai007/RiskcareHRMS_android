package com.riskcare.app.service;

/**
 * LocationUpdateReceiver — Layer 1 background location delivery.
 *
 * The OS delivers location here every 30 seconds via the PendingIntent registered
 * in LocationTrackingService.startBackgroundLocationUpdates().
 *
 * This fires even when the app is COMPLETELY KILLED — the OS wakes this receiver
 * directly. This is the most reliable background tracking method on Android.
 *
 * Uses goAsync() to perform async work (Room + network) without ANR risk.
 * WakeLock acquired to keep CPU awake during the network call.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u0004*\u00060\nR\u00020\u000bH\u0002\u00a8\u0006\r"}, d2 = {"Lcom/riskcare/app/service/LocationUpdateReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "ctx", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "safeRelease", "Landroid/os/PowerManager$WakeLock;", "Landroid/os/PowerManager;", "Companion", "app_debug"})
public final class LocationUpdateReceiver extends android.content.BroadcastReceiver {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "LocationUpdateReceiver";
    private static final float MAX_ACCURACY = 500.0F;
    private static final long WAKELOCK_MS = 15000L;
    @org.jetbrains.annotations.NotNull()
    public static final com.riskcare.app.service.LocationUpdateReceiver.Companion Companion = null;
    
    public LocationUpdateReceiver() {
        super();
    }
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context ctx, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    private final void safeRelease(android.os.PowerManager.WakeLock $this$safeRelease) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/riskcare/app/service/LocationUpdateReceiver$Companion;", "", "()V", "ACTION_LOCATION_UPDATE", "", "getACTION_LOCATION_UPDATE", "()Ljava/lang/String;", "MAX_ACCURACY", "", "TAG", "WAKELOCK_MS", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getACTION_LOCATION_UPDATE() {
            return null;
        }
    }
}