package com.riskcare.app.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\u0012\u0010\u0019\u001a\u00020\u00182\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0002J\u0018\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020 J\u000e\u0010!\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010\"\u001a\u00020\u0018J\b\u0010#\u001a\u00020\u0018H\u0016J\u0012\u0010$\u001a\u00020\u00182\b\u0010%\u001a\u0004\u0018\u00010&H\u0014J\b\u0010\'\u001a\u00020\u0018H\u0014J\b\u0010(\u001a\u00020\u0018H\u0002J\u0010\u0010)\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001bH\u0014J\b\u0010*\u001a\u00020\u0018H\u0014J\u0006\u0010+\u001a\u00020\u0018J\b\u0010,\u001a\u00020\u0018H\u0002J\b\u0010-\u001a\u00020\u0018H\u0002J\b\u0010.\u001a\u00020\u0018H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0080.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006/"}, d2 = {"Lcom/riskcare/app/ui/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/riskcare/app/databinding/ActivityMainBinding;", "getBinding$app_debug", "()Lcom/riskcare/app/databinding/ActivityMainBinding;", "setBinding$app_debug", "(Lcom/riskcare/app/databinding/ActivityMainBinding;)V", "dateChangeReceiver", "Landroid/content/BroadcastReceiver;", "lastKnownDate", "", "midnightHandler", "Landroid/os/Handler;", "midnightRunnable", "Ljava/lang/Runnable;", "sessionManager", "Lcom/riskcare/app/utils/SessionManager;", "getSessionManager", "()Lcom/riskcare/app/utils/SessionManager;", "setSessionManager", "(Lcom/riskcare/app/utils/SessionManager;)V", "clearAttendanceCache", "", "handleDeepLink", "intent", "Landroid/content/Intent;", "loadFragment", "fragment", "Landroidx/fragment/app/Fragment;", "addToBack", "", "loadTab", "logout", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onMidnight", "onNewIntent", "onResume", "refreshDashboardBadge", "registerDateChangeReceiver", "scheduleMidnightLogout", "setupBottomNav", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    public com.riskcare.app.databinding.ActivityMainBinding binding;
    public com.riskcare.app.utils.SessionManager sessionManager;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler midnightHandler = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable midnightRunnable = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.BroadcastReceiver dateChangeReceiver = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String lastKnownDate = "";
    
    public MainActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.databinding.ActivityMainBinding getBinding$app_debug() {
        return null;
    }
    
    public final void setBinding$app_debug(@org.jetbrains.annotations.NotNull()
    com.riskcare.app.databinding.ActivityMainBinding p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.utils.SessionManager getSessionManager() {
        return null;
    }
    
    public final void setSessionManager(@org.jetbrains.annotations.NotNull()
    com.riskcare.app.utils.SessionManager p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void scheduleMidnightLogout() {
    }
    
    private final void onMidnight() {
    }
    
    private final void registerDateChangeReceiver() {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final void clearAttendanceCache() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @java.lang.Override()
    protected void onNewIntent(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    private final void handleDeepLink(android.content.Intent intent) {
    }
    
    private final void setupBottomNav() {
    }
    
    public final void loadFragment(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment fragment, boolean addToBack) {
    }
    
    public final void loadTab(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment fragment) {
    }
    
    /**
     * Called when user opens NotificationsFragment — instantly clears the bell badge
     */
    public final void refreshDashboardBadge() {
    }
    
    public final void logout() {
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
}