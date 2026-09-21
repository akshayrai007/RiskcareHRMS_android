package com.riskcare.app.domain.usecase;

/**
 * TrackingManager — single authority for all tracking state.
 *
 * All reads/writes to TrackPrefs go through here.
 * No other class should directly access SharedPrefs for tracking state.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\fJ\u0006\u0010\u000e\u001a\u00020\fJ\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014J\u000e\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u0019\u001a\u00020\fJ\u0010\u0010\u001a\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\fJ\u0006\u0010\u001b\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/riskcare/app/domain/usecase/TrackingManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "clearPunchCache", "", "getSession", "Lcom/riskcare/app/domain/model/TrackingSession;", "isOd", "", "isPunchedIn", "isTracking", "millisUntilOdStop", "", "setOdFlag", "setOdStopTime", "hour", "", "minute", "setPunchInCache", "time", "", "shouldOdStopNow", "startTracking", "stopTracking", "app_debug"})
public final class TrackingManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    
    public TrackingManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final boolean isTracking() {
        return false;
    }
    
    public final boolean isOd() {
        return false;
    }
    
    public final boolean isPunchedIn() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.domain.model.TrackingSession getSession() {
        return null;
    }
    
    public final void startTracking(boolean isOd) {
    }
    
    public final void stopTracking() {
    }
    
    public final void setOdFlag(boolean isOd) {
    }
    
    public final void setOdStopTime(int hour, int minute) {
    }
    
    public final void setPunchInCache(@org.jetbrains.annotations.NotNull()
    java.lang.String time) {
    }
    
    public final void clearPunchCache() {
    }
    
    /**
     * Returns true if OD tracking should auto-stop right now.
     * Default: 6:30 PM. Can be configured via setOdStopTime().
     */
    public final boolean shouldOdStopNow() {
        return false;
    }
    
    /**
     * Returns milliseconds until OD stop time from now.
     * Used to schedule exact auto-stop alarm.
     */
    public final long millisUntilOdStop() {
        return 0L;
    }
}