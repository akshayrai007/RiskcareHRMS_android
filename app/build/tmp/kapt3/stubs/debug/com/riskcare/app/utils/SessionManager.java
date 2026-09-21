package com.riskcare.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u000b\u0018\u0000 $2\u00020\u0001:\u0001$B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\f\u001a\u00020\rJ\b\u0010\u0013\u001a\u0004\u0018\u00010\u000fJ\u0006\u0010\u0014\u001a\u00020\u000fJ\b\u0010\u0015\u001a\u0004\u0018\u00010\u000fJ\u0006\u0010\u0016\u001a\u00020\u0017J\n\u0010\u0018\u001a\u0004\u0018\u00010\u000fH\u0002J\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00110\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00110\u001aJ\u0016\u0010\u001c\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u000fJ\u000e\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u000fJ\u0016\u0010\u001f\u001a\u00020\n2\u0006\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\u0011J\u000e\u0010\"\u001a\u00020\n2\u0006\u0010!\u001a\u00020\u0011J\u000e\u0010#\u001a\u00020\n2\u0006\u0010 \u001a\u00020\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/riskcare/app/utils/SessionManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "gson", "Lcom/google/gson/Gson;", "prefs", "Landroid/content/SharedPreferences;", "clearSession", "", "deleteEmployeePhoto", "employeeId", "", "getDeviceId", "", "getEmployee", "Lcom/riskcare/app/data/models/Employee;", "getEmployeePhoto", "getPhotoFromFile", "getRole", "getToken", "isLoggedIn", "", "loadPhotoFromFile", "mergeEmployeesWithCachedPhotos", "", "employees", "saveEmployeePhoto", "base64", "savePhotoToFile", "saveSession", "token", "employee", "updateEmployee", "updateToken", "Companion", "app_debug"})
public final class SessionManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.riskcare.app.utils.SessionManager.Companion Companion = null;
    
    public SessionManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Returns a stable unique ID for this device installation.
     * Generated once using UUID and stored permanently in SharedPrefs.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeviceId() {
        return null;
    }
    
    public final void saveSession(@org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.Employee employee) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getToken() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.riskcare.app.data.models.Employee getEmployee() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRole() {
        return null;
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    public final void updateEmployee(@org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.models.Employee employee) {
    }
    
    public final void updateToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
    
    /**
     * BUG FIX: Use commit() instead of apply() here.
     *
     * apply() is asynchronous — it queues the write and returns immediately.
     * If the user logs out and the OS kills the process before the write finishes
     * (e.g. they reopen the app very quickly), SplashActivity reads stale prefs
     * and sees is_logged_in=true + a valid token → routes to MainActivity instead
     * of LoginActivity, showing the previous user's data.
     *
     * commit() blocks until the write is complete, so by the time clearSession()
     * returns the prefs are guaranteed clean. Logout is a rare, user-triggered
     * action so the tiny sync overhead on the main thread is completely acceptable.
     */
    public final void clearSession() {
    }
    
    public final void saveEmployeePhoto(int employeeId, @org.jetbrains.annotations.NotNull()
    java.lang.String base64) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEmployeePhoto(int employeeId) {
        return null;
    }
    
    public final void deleteEmployeePhoto(int employeeId) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.riskcare.app.data.models.Employee> mergeEmployeesWithCachedPhotos(@org.jetbrains.annotations.NotNull()
    java.util.List<com.riskcare.app.data.models.Employee> employees) {
        return null;
    }
    
    public final void savePhotoToFile(@org.jetbrains.annotations.NotNull()
    java.lang.String base64) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPhotoFromFile() {
        return null;
    }
    
    private final java.lang.String loadPhotoFromFile() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\u0006R\u0014\u0010\r\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u0006\u00a8\u0006\u000f"}, d2 = {"Lcom/riskcare/app/utils/SessionManager$Companion;", "", "()V", "KEY_DEVICE_ID", "", "getKEY_DEVICE_ID", "()Ljava/lang/String;", "KEY_EMPLOYEE", "getKEY_EMPLOYEE", "KEY_IS_LOGGED_IN", "getKEY_IS_LOGGED_IN", "KEY_TOKEN", "getKEY_TOKEN", "PHOTO_FILE", "getPHOTO_FILE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        private final java.lang.String getKEY_TOKEN() {
            return null;
        }
        
        private final java.lang.String getKEY_EMPLOYEE() {
            return null;
        }
        
        private final java.lang.String getKEY_IS_LOGGED_IN() {
            return null;
        }
        
        private final java.lang.String getPHOTO_FILE() {
            return null;
        }
        
        private final java.lang.String getKEY_DEVICE_ID() {
            return null;
        }
    }
}