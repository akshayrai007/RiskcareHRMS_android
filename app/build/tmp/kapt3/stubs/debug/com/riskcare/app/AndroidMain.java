package com.riskcare.app;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║                       Android_main.kt                               ║
 * ║           Single Central Config — RiskcareHRMS Android              ║
 * ║                                                                      ║
 * ║  ⚠️  THIS IS THE ONLY FILE YOU NEED TO EDIT FOR WHITE-LABELLING     ║
 * ║  Change brand name, colors, URL, logo ref — all in one place.       ║
 * ║  No other file should contain any of these hardcoded values.        ║
 * ║                                                                      ║
 * ║  SOURCE OF TRUTH: backend/Main_file.js (RiskcareHRMS repo)          ║
 * ║  Keep this file in sync with that file when backend values change.  ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\"\n\u0002\u0010\t\n\u0002\bF\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u007f\u001a\u000f\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0080\u00012\u0007\u0010\u0081\u0001\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u00109\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010R\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u0017\u0010U\u001a\b\u0012\u0004\u0012\u00020\u00040\r\u00a2\u0006\b\n\u0000\u001a\u0004\bV\u0010\u000fR\u000e\u0010W\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u0011\u0010X\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\bY\u0010ZR\u0011\u0010[\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\\\u0010ZR\u000e\u0010]\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010^\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010_\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010a\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010c\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010d\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010e\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010f\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010g\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010h\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010i\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010j\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010k\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010l\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010m\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010o\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010p\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010q\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010r\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010s\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010t\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010u\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010v\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010w\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010x\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010y\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010z\u001a\u00020{X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010|\u001a\u000204X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010}\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010~\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0082\u0001"}, d2 = {"Lcom/riskcare/app/AndroidMain;", "", "()V", "ACCOUNTS_EMPLOYEE_CODE", "", "ACTION_LOCATION_UPDATE", "ACTION_STOP_TRACKING", "APP_NAME", "APP_TAGLINE", "APP_VERSION", "BASE_URL", "CHAT_ATTACHMENTS_FOLDER", "CHAT_BLOCKED_EXTENSIONS", "", "getCHAT_BLOCKED_EXTENSIONS", "()Ljava/util/List;", "CHAT_FILE_MAX_SIZE_MB", "", "CHAT_FILE_SAVED_TOAST", "COLOR_ACCENT_CYAN", "COLOR_ACCENT_NAVY", "COLOR_BACKGROUND", "COLOR_PRIMARY", "COLOR_PRIMARY_DARK", "COLOR_PRIMARY_LIGHT", "COLOR_PRIMARY_ULTRA_LIGHT", "COLOR_WHITE", "COMPANY_CIN", "COMPANY_CITY", "COMPANY_CORPORATE_ADDR", "COMPANY_CORPORATE_TEL", "COMPANY_EMAIL", "COMPANY_FULL_NAME", "COMPANY_NAME", "COMPANY_OFFICE_ADDR", "COMPANY_SHORT_NAME", "COMPANY_TEL", "COMPANY_WEBSITE", "COMPOFF_EXPIRY_DAYS", "CURRENCY", "CURRENCY_SYMBOL", "DATE_FORMAT", "DEEP_LINK_SCHEME", "DEFAULT_REGION", "DOWNLOADS_FOLDER", "EMP_CODE_PREFIX", "EMP_CODE_PREFIX_CONTRACT", "FILE_PROFILE_PHOTO", "FORM16_ADDR_FULL", "FORM16_CONTACT_LINE", "FORM16_LOGO_DATA_URI", "GEOFENCE_LOCATION_REQUEST_MS", "", "GEOFENCE_MAX_RADIUS_M", "GEOFENCE_VALIDATE_COOLDOWN_MS", "GK_QUESTION_TIMER_MS", "GOOGLE_MAPS_API_KEY", "HEALTH_PING_INTERVAL_MS", "HEALTH_PING_MAX_ATTEMPTS", "HEALTH_PING_TIMEOUT_SEC", "KEY_DEVICE_ID", "KEY_EMPLOYEE", "KEY_IS_LOGGED_IN", "KEY_TOKEN", "LOGO_DRAWABLE", "MD_EMPLOYEE_CODE", "MISSING_PUNCH_OUT_HOUR", "MOVEMENT_LIVE_REFRESH_MS", "MOVEMENT_LOG_RETENTION_DAYS", "NOTICE_DAYS_DEFAULT", "NOTICE_DAYS_MANAGER", "NOTICE_DAYS_SENIOR", "NOTIF_CHANNEL_ID", "NOTIF_CHANNEL_NAME", "NOTIF_ID", "NOTIF_TEXT", "NOTIF_TITLE", "OD_TRACKING_WINDOW_END", "OD_TRACKING_WINDOW_START", "PAYSLIP_ADDR_LINE1", "PAYSLIP_ADDR_LINE2", "PAYSLIP_LOCATION_CITY", "PDF_FOOTER_PREFIX", "PDF_SAVED_TOAST", "PERFORMANCE_DEFAULT_WEIGHT", "PERFORMANCE_FINAL_RATINGS", "getPERFORMANCE_FINAL_RATINGS", "PERFORMANCE_RATING_SCALE", "PERMISSION_BACKGROUND_SETTINGS_HINT", "getPERMISSION_BACKGROUND_SETTINGS_HINT", "()Ljava/lang/String;", "PERMISSION_LOCATION_RATIONALE", "getPERMISSION_LOCATION_RATIONALE", "PREFS_ATT_CACHE", "PREFS_DASH_CACHE", "PREFS_LEAVE_CACHE", "PREFS_MAIN", "PREFS_TRACK", "PUNCH_IN_CUTOFF_HOUR", "PUNCH_IN_CUTOFF_MINUTE", "PUNCH_OUT_CUTOFF_HOUR", "PUNCH_OUT_CUTOFF_MINUTE", "PUNCH_RETRY_DELAY_MS", "PUNCH_TOAST_DELAY_MS", "SECONDARY_REGION", "SEE_ALL_MOVEMENT_CODE", "SPLASH_CONNECTED_DELAY_MS", "SPLASH_INITIAL_DELAY_MS", "SPLASH_MSG_ALMOST", "SPLASH_MSG_CONNECTING", "SPLASH_MSG_LOADING", "SPLASH_MSG_STARTING", "SPLASH_MSG_WAKING", "SPLASH_SLOW_MSG_DELAY_MS", "SPLASH_WORKSPACE_DELAY_MS", "TIMEOUT_CONNECT_SEC", "TIMEOUT_READ_SEC", "TIMEOUT_WRITE_SEC", "TIMEZONE", "TRACK_INTERVAL_MOVING_MS", "TRACK_INTERVAL_STATIONARY_MS", "TRACK_LOCATION_CACHE_MS", "TRACK_STATIONARY_THRESHOLD_M", "", "TRACK_WAKELOCK_TIMEOUT_MS", "WAKELOCK_TAG_GPS", "WAKELOCK_TAG_RECEIVER", "getBatteryOptSteps", "Lkotlin/Pair;", "brand", "app_debug"})
public final class AndroidMain {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMPANY_NAME = "Risk Care Insurance Broking Pvt. Ltd.";
    
    /**
     * Short app name shown in notifications, dialogs, permission screens
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_NAME = "RiskCareHR";
    
    /**
     * Full legal company name shown on payslips and PDF headers
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMPANY_FULL_NAME = "Risk Care Insurance Broking Services Pvt. Ltd.";
    
    /**
     * Short company name used in logo alt-text and headers
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMPANY_SHORT_NAME = "Risk Care Insurance";
    
    /**
     * tagline shown on splash / login screen
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_TAGLINE = "Servicing All Risks";
    
    /**
     * CIN for compliance/legal documents
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMPANY_CIN = "U51109MH2005PTC199431";
    
    /**
     * Registered office address
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMPANY_OFFICE_ADDR = "708, 7th Floor, Hubtown Viva, Western Express Highway, Shankarwadi, Jogeshwari (East), Mumbai - 400060";
    
    /**
     * Corporate address (same as office for Riskcare)
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMPANY_CORPORATE_ADDR = "708, 7th Floor, Hubtown Viva, Western Express Highway, Shankarwadi, Jogeshwari (East), Mumbai - 400060";
    
    /**
     * Landline shown on formal documents
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMPANY_TEL = "+912261473232";
    
    /**
     * Mobile / corporate contact
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMPANY_CORPORATE_TEL = "+919004028426";
    
    /**
     * City — used in tax / regional logic
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMPANY_CITY = "Mumbai";
    
    /**
     * Support / HR email shown on Form16 and official PDFs
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMPANY_EMAIL = "support@riskcare.co.in";
    
    /**
     * Website shown on Form16 and official PDFs
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMPANY_WEBSITE = "riskcareinsure.com";
    
    /**
     * Footer text on generated PDFs/HTML reports
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PDF_FOOTER_PREFIX = "Generated by RiskCareHR";
    
    /**
     * Folder name under Downloads where PDFs are saved
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DOWNLOADS_FOLDER = "RiskCareHR";
    
    /**
     * Toast message shown after PDF is saved
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PDF_SAVED_TOAST = "\u2705 PDF saved to Downloads/RiskCareHR";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LOGO_DRAWABLE = "ic_logo";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COLOR_PRIMARY = "#E8303A";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COLOR_PRIMARY_DARK = "#C0272D";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COLOR_PRIMARY_LIGHT = "#FF5252";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COLOR_PRIMARY_ULTRA_LIGHT = "#FFEBEE";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COLOR_ACCENT_CYAN = "#00AEEF";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COLOR_ACCENT_NAVY = "#1A2B5A";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COLOR_WHITE = "#FFFFFF";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COLOR_BACKGROUND = "#F5F8FB";
    
    /**
     * Base URL for ALL Retrofit API calls. Must end with /api/
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BASE_URL = "https://riskcarehrms.onrender.com/api/";
    
    /**
     * OkHttp timeouts (seconds)
     */
    public static final long TIMEOUT_CONNECT_SEC = 30L;
    public static final long TIMEOUT_READ_SEC = 60L;
    public static final long TIMEOUT_WRITE_SEC = 120L;
    
    /**
     * URI scheme for push-notification deep links e.g. riskcarehr://approvals?tab=1
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEEP_LINK_SCHEME = "riskcarehr";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREFS_MAIN = "RiskCareHR_Prefs";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREFS_ATT_CACHE = "RiskCareHR_AttCache";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREFS_LEAVE_CACHE = "RiskCareHR_LeaveCache";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREFS_DASH_CACHE = "RiskCareHR_DashCache";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREFS_TRACK = "RiskCareHR_TrackPrefs";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_TOKEN = "auth_token";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_EMPLOYEE = "employee_data";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_IS_LOGGED_IN = "is_logged_in";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_DEVICE_ID = "device_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FILE_PROFILE_PHOTO = "profile_photo.b64";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NOTIF_CHANNEL_ID = "riskcarehr_tracking_channel";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NOTIF_CHANNEL_NAME = "Location Tracking";
    public static final int NOTIF_ID = 9001;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NOTIF_TITLE = "RiskCareHR \u2014 Tracking Active";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NOTIF_TEXT = "Location recorded every 30 seconds";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_LOCATION_UPDATE = "com.riskcare.app.LOCATION_UPDATE";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_STOP_TRACKING = "com.riskcare.app.STOP_TRACKING";
    public static final long TRACK_INTERVAL_MOVING_MS = 30000L;
    public static final long TRACK_INTERVAL_STATIONARY_MS = 60000L;
    public static final float TRACK_STATIONARY_THRESHOLD_M = 50.0F;
    public static final long TRACK_LOCATION_CACHE_MS = 60000L;
    public static final long TRACK_WAKELOCK_TIMEOUT_MS = 60000L;
    public static final long MOVEMENT_LIVE_REFRESH_MS = 30000L;
    public static final int GEOFENCE_MAX_RADIUS_M = 50000;
    public static final long GEOFENCE_VALIDATE_COOLDOWN_MS = 60000L;
    public static final long GEOFENCE_LOCATION_REQUEST_MS = 5000L;
    public static final long PUNCH_TOAST_DELAY_MS = 3000L;
    public static final long PUNCH_RETRY_DELAY_MS = 15000L;
    public static final long HEALTH_PING_TIMEOUT_SEC = 8L;
    public static final int HEALTH_PING_MAX_ATTEMPTS = 18;
    public static final long HEALTH_PING_INTERVAL_MS = 5000L;
    public static final long SPLASH_INITIAL_DELAY_MS = 600L;
    public static final long SPLASH_SLOW_MSG_DELAY_MS = 800L;
    public static final long SPLASH_CONNECTED_DELAY_MS = 300L;
    public static final long SPLASH_WORKSPACE_DELAY_MS = 300L;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WAKELOCK_TAG_GPS = "RiskCareHR::GPS";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WAKELOCK_TAG_RECEIVER = "RiskCareHR::LayerOneReceiver";
    
    /**
     * COO / Super-admin who can see ALL movement data regardless of role
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SEE_ALL_MOVEMENT_CODE = "RC001";
    
    /**
     * MD employee code
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MD_EMPLOYEE_CODE = "RC01";
    
    /**
     * Accounts lead employee code
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACCOUNTS_EMPLOYEE_CODE = "RC002";
    
    /**
     * Prefix used when generating permanent employee codes
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EMP_CODE_PREFIX = "RC";
    
    /**
     * Prefix used when generating contractual employee codes
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EMP_CODE_PREFIX_CONTRACT = "CT";
    public static final int NOTICE_DAYS_SENIOR = 90;
    public static final int NOTICE_DAYS_MANAGER = 45;
    public static final int NOTICE_DAYS_DEFAULT = 30;
    public static final int PUNCH_IN_CUTOFF_HOUR = 10;
    public static final int PUNCH_IN_CUTOFF_MINUTE = 30;
    public static final int PUNCH_OUT_CUTOFF_HOUR = 18;
    public static final int PUNCH_OUT_CUTOFF_MINUTE = 30;
    public static final int MISSING_PUNCH_OUT_HOUR = 21;
    
    /**
     * OD tracking window — start as HHMM int (e.g. 930 = 09:30)
     */
    public static final int OD_TRACKING_WINDOW_START = 930;
    public static final int OD_TRACKING_WINDOW_END = 1830;
    public static final int MOVEMENT_LOG_RETENTION_DAYS = 3;
    public static final int COMPOFF_EXPIRY_DAYS = 30;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_VERSION = "1.0.0";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TIMEZONE = "Asia/Kolkata";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CURRENCY = "INR";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CURRENCY_SYMBOL = "\u20b9";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATE_FORMAT = "DD/MM/YYYY";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_REGION = "south_west";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SECONDARY_REGION = "north";
    public static final int CHAT_FILE_MAX_SIZE_MB = 1024;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> CHAT_BLOCKED_EXTENSIONS = null;
    public static final int PERFORMANCE_RATING_SCALE = 5;
    public static final int PERFORMANCE_DEFAULT_WEIGHT = 20;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> PERFORMANCE_FINAL_RATINGS = null;
    public static final long GK_QUESTION_TIMER_MS = 30000L;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String GOOGLE_MAPS_API_KEY = "AIzaSyDDQDYyVMhS5UWkzuW403F1ilKfxd2yETM";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHAT_ATTACHMENTS_FOLDER = "RiskCareHR/Attachments";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHAT_FILE_SAVED_TOAST = "\u2705 Saved: Downloads/RiskCareHR/Attachments";
    
    /**
     * Address line 1 shown on payslip PDF header
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PAYSLIP_ADDR_LINE1 = "Office Address: 708, 7th Floor, Hubtown Viva, Western Express Highway,";
    
    /**
     * Address line 2 shown on payslip PDF header
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PAYSLIP_ADDR_LINE2 = "Shankarwadi, Jogeshwari (East), Mumbai \u2013 400060.";
    
    /**
     * Single-line full address for Form16 HTML
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FORM16_ADDR_FULL = "708, 7th Floor, Hubtown Viva, Western Express Highway, Shankarwadi, Jogeshwari (East), Mumbai \u2013 400060";
    
    /**
     * Contact line shown below address on Form16
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FORM16_CONTACT_LINE = "support@riskcare.co.in | riskcareinsure.com";
    
    /**
     * Logo embedded as base64 data URI in Form16 PDF HTML.
     * Replace with new client's logo by converting their logo PNG to base64:
     *  python3 -c "import base64; print('data:image/png;base64,' + base64.b64encode(open('logo.png','rb').read()).decode())"
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FORM16_LOGO_DATA_URI = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIgAAABMCAYAAAC2w//wAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAABc+SURBVHhe7V0JdBRV1gZBRYVBZV9lFxhQkOFXgSCQsA2OgCiICIisivzjIDvDpqKOjgjyixtn3BBEBwghQAJh3wMSZJN937o7na27s9Srqu8/36vel0BIiMT0Pec73elX71XVre/de99791VKICxhyUVK+P8QlrB4S5ggYclVwgQJS65yfYIoAkhJA66YgUum4oHLJuCqBUizAarqr5EbkhwNsOTouJSp4cJthitZGtKEDs3/ooNIaIJkZUNP2Alt6jyIvmMhIl+BiBhUPPDUYIioYVAHTYL23pfQk44Cuu6voaBCYmww5+DNQzZ03pGKRzda0XiDFY0Tbg802WDFX7akoM+eNHxwwoFjNhW53VlwgmTYoU74N0TTv0HUjjLwUGeI2sUE8l6jIGpFQtTpAtHsb9Dmfic7TW6Somh4/UAGKq2x4I5VZpSIMaMEP29HxJhxZ6wZTROSEXM19H0FEsTugPrqLIhqTxmKCkNCqR0Jdc5/ADW4YbarOgbvT8cdtzMpQqBMtAk/XMyCCGJKfAmSo0CdPh+iapgcgYiCUr8r9JiNPiqjUK/vHHegdBEkhwu11lqw2aIEuBsfgui7DhhuJUA5YRAKSdK4B/TkVG+1YV+aQNkiTA6Clm/wvnTY/MyIhyBChTr7Cyh1AhUThheqd4D24xq32qjOyYdtt3e8cYOovS4ZJ+y+ozYPQRiYDp5iBGb+SgnDgzpdoI77ENCMWCRD1dFtZ2qAsosi7ooxI+aab8DqIYglBerTr4UJcgNQh0wFsnOk2i5maXhic0qAsoskVprw6dksL3p4E8RkhdptJEQt55A2jJBQB00GMo2exomn//kDEWTOGYcXPcIEuSn4ECQrTJAw/BAmSBClhOFBmCBBlBKGB2GCBFFKsUXNTlCqtIeoHAGlylPyu/rCm0CmEe2Hg9TiCC7U1esK5fF+UMe8A239DsBsNUjhyATsmcYMWdiCFD/IRbnuI6Et/C905oVcZ5k/TJDigjqdodTsCDFkMnD+SgAx0oUmp6F/SRM4lKHiarYmD6GLeTxMkEICcy9c8C+7EXAdSdYPUnYdcCFOHTUDEMKtkkxVx75UgaFJGbifC1orTCgRbULJFSbcvcKExhutmHXMjibrrYHKLoooFILUjoSo1QlKACI9iUf+4O8RA6H+3w9Q5/8Adeg/5aJYwHGhUDsKas8xUOcvkvVFx5dDnysYeOzw6UZ6pVPsQsf4wzZUWWMJvRAXY0bJlWaUDFVe1HDLCVKjE8TTr0Id/Q7U195yQ7z2NsTgSVDa9IdSuY0cGfjUqxkJtd+b7kvRv14OUaldYPuhUCsS2tRPjMBRB9SBE2WbAceFQtuXoB855T6/Q9URsTUFd/or8I+OW06QShHQvo32OYGP0Gmn26C+swBKkx6eeiRI37Ey5YDQ/7Ps5gjiFPWlGycILZs2+wt33UxNx6j96SgRYwpU4B8dhUKQb1a4m9RT02VyjZ5ug253GASQXVyFtnydkXtC8JyP94c6ZR7UKXOh9v0HRI2Oge2HQn4IUr2DEZQ6Jd6cg8pxyYHKKw4obIKoL46DaPk8ROt+EK1fgDpsGnRuoaAIAXXwZCguIriShEPEKooLtaOc36M8yU03S5BaUVD7/N1nxDIkidYjiPLyC9mmJfD3vEK247JuBdCeNwqbIFq3ET7tKVWfgjr+X57yJWugVGxrlNfrCtGyD0TL5yAedmbT83d+NvwrlNZ9oEYNg+g8FKJdfygte0M0edo4JjeCkETNehntEq5MfZZVfQra0rXueooO1Min9Si9yowacRa0jEtGxEYrOmxOQZsEKxrFJeNuv2NLrTKjZpwF9ddZUHGNBXetMuPhtRZZ77EEq4eoMWbcywz0OKOsw8YUPBZvRYXcAuibwe9OEAanvUa7y/W1W6FUaGMc02Gw4ZJS0qDN/NRInOaDbNEb2s9xMnaRPZ2gq0q3Q/v4W7fVCUYQWhnRbRj0/UehW9Ogn74AddRMT1JUlfbQDxxz1zucoaJkdBDF3ShWmtF1ZxqS0oXMcmfOGW0T0zwvZ2mYc8KBOvHJ7odaYU0yYq/mwJSjYf6pTEw4aMO1bB2qDuxKVeRwmsc2TbBi4dlMXMvRZBnbZPtrr2Wj0/bUghtF/a4EqRMFpWpbaN+vdJerny2BUrGdcUzkEE+9fy2EqNweomZHuXlLqoTE4DR3ht34FCq0FQmGpQhCEJIxp24U9DMX3cTSZn/uO0dSs5Oxk84p6y05KBmdj+B0pQlvHrTJtjiHwt1rhKIZLowfC89moexqwzVUWpOM7RZFlqUqmhxaU/ixNUWR8y4PxSdji/MYVddx1K7i13Th3qJw0q6i2QYva5MfFDZBxIDXIFr2gGjVC6LzK9CXxUN3bWVMSYOIeNGII4IRpFIERLsBgM24YH39NihV2kCp0BZK5XYQj/SSU+Ku2MWXIBOgNHsG+lWz8UN2NrTPFkOp7HRnLtTo6BOgJuSXIDFmRO1IRbsdaSgfY0ap5YQJUdtScdZh3Pdph4pHNhoTa94EobXhMROO2FAr3iKz5OmCSDi6PhJi9jEH7o42oVS0GWN/tckRF3+f+ZtduraA68krCpsgyMoxlsWZv+lM8pWSkgZ16lyPqQ9FkC7DnAtjOvT4TVBaPgulUlsoNTrJQNVT35cg2j/ehbZpj2GLs7KhzV8EUa194PXSxSQedNc751BRamUQxeUFnDyLNqH8agse2ZQitznSrXx/3lj9TVZ0tN9qTM17E+Ripor2W1Klm3K1VXaVGeud5XRRjddYUHq5CaVXmNAiPhmnbQbpoq9k4wGnVcoXCp0grpjBJUJAWxILpf0gKAxKXfWCEYQupsnTgDXVWDjl/MjZS9A3J0L95zzDmriCTT+C4IrJvelaX7oGSsNugddKVOsA9YulnvPqkHtXAxSXB5SKNuHdEw4cyVBxKUvD5SwVZxwqrNy0K7dn6uiwzciC9yZIUppA/fVeAXKsGRViLbicbdSjtdiXomCP1UBSipBujLLTqshg1/9a8ozCJog6aKIxS/nLEc9v7y2AUtNvGj0YQZiDwaD2mdegnzoPnZbIm2zpGRA9grsY9x5aXYM6eLwRrPpfK8G67Qf5bKecdMR209sny8SY8eXZTOPUzrjiHHfTZ2vIdsYhIQmSHkiQqmstsNK/eAW7wZCYKlCbwW+Qa8oTCp0gXYdDVO8I5Yl+0M9fNn5UFKivzTCm5V31QhFElnWCaN4L6v/Ohvrvr6HvPeR2V/qOJIjmPX0JogPaopXQmb/BP09fgOg+PPBanVCqREBP+s19bq7WNvB+UHnAk1tScD7TuDYGkn33pqH+RmM3fdw1Y5vEDRNklRmVV1tgdloeBqMTD9rkWwMMZEiMO2TDgH3pKFcUXYx7FMORxqAJgCPLcBenzkPtNMhTLxhB6GK4BE/IY5zxRuvnoW/ZI4+jy1E6vRJgQdSBE6C+/6Ukks745eAx5Dz4JJRgq8Rc6Pv7u26XJHQd8047UIJDTH8FXgfPJaZJs09j8e5xB0q5LFG0GQtOGzFIXghyf4wFB9ONBUTGIHU4R8M2gyHI9eQZvxtB2FMbdIP2XbSn9/+8FqKe84EFIwgtSP2uEE26QTTvDfHosxAtnoXoSJd12GjjyCmINi8GEoTD3CptocVucrslffs+iLq0WkHur0UfaJsT3fUpbx6xoXxsECXmgp6705DhHH+uuJyFOvEWVImzoNOmFBxONwgoCRIkSA1GEE6czT3lkISj1pZczEKztcmyzYrxFjkh13C1BdVWW1AyyPXkGb8nQaS/f7I/9ONnjUK6mhnzoFRtb5T5E6RSBNQeo4wg9eQ56LuSoO8+ICe83Md9sRRK3cAg1T1RVrMjtLVbnL/q0D5fDNGwe+B10zp1HQ79WrK7DcqPF7PQmslAy4wJqwCFyiGnSQ5H+Xej9VYcSDN6POPHQ+kC26yK3BAtnDFIqqKj07YbIwjRYoNVTuBR2EJyjobtVkUOyQ+kC5hydHx80iHJ5F83z7jlBKnYDtqS1e4m1Z6jPa5Brpx2MoauriFvZjbEiOkQVTtA7TLUXU/78GvZliBBOCnmL5oO7auf5OsYpEUgQabPdxczn0TO2vK8bV+EfuCoUeDIgjrtE+QwZ8Xv2plqKFr3hX7hqvdppGln/NAnMQ33UonLTHICi6OdOaczsepqjnyLEBVMonTdkSrnLbxlb4qCmUft8jtd0F93GC6m2loLdiUbBDmcIdAoIZAgfBFNI06WOY/zF17jR0WGIETdLhD1u0E06BY0s4vL63JyjMcQDY1lfxkfuH5jGzyesUujv0J9ZSq0dz6H9u5X0Jhb0vRv0vLI2MR93q6e+t7n5flooeo5yxr18AyP/a+Nn21ehL51n0/ykEv43GkZ+FBcHAjISY0xo/xKE57bm4YJR+3osDMVZVaa5TpMjVgLqsWaUcZ5LB9+pVgLqsfy05zr+0VIyrpxFgxKSsfEo3aMPWJD193pqL3Wkv+5GxcKhSC3ArQG1ToY6zN5SQO4CcjMt8Y9oE2ZBy1+h3t7QygJIIgLzCdZGcI15QckA9u9JW0XVYL8HqDlebgHRJsBhgVbsAT6yk3QVm2CHrfNnbsakiBFEWGC3ATkWlGkMapiXFSxHdQXxrkn48IECSMAxtZL5866MEHC8Ed4b24QpYThQdiCBFFKGB6ECRJEKWF4oA6c9Ifd3f/RqVAESU6F2vN1n5nPMIJDHTZdvnSYwqX8Nlv+OAT5ypnY5BIPQeyZUEcwoTdsQXJFnc5Qp813Lxcwkaf37rRAZRdBcCkhwWykJbjEQxBNhzp/sbH45a+UMNxQqkYYM6xe8tEJh0+qYJFEjBnNEqw478yddYmHIFxrOHne2JsSRDFhEJEQTw10vyPVJXQzVQsiYed3BNeBxh+yuTPfXOJDELk14PuVUGrd2vWOIonakVBaPQscOu6jMpcsZ+JwbBElSYwZrTZYcSwjcIHSlyAUbolc+HNg3mgxhlK3C5TG3aEzASmEME/o41MOlPNX/u2OlSY0ijcy1/yyFKQEEoTCTUn/jYdo9TyUBt2N5fFaUcZKJ5Xmn7rHwNYJhUv0TnB7gsw9LargvTXobqQA7P7VWOfPRUiSny5lo2FcMsrFOt8bcpuCuSuVVpvRd3eaTKwOJcEJQmGq3hUztMWxcg+L+vo7EK++Zbz7Y8xsiDHvQrxufDfeB/K2UT5yFsTImXJEpA6fATFsuhwWEmLoNKhDp7k/fcBjhk73HO9VJl4pZPD8o9+G+tYC6Ks2Gbv5blBIIb6e6rvzWRh30IYhe9PRPzEdL9wmeDExHSP2p2PaERvWmXMC/v2Hv4QmiEtIFCbPcO3Bwbf8OcGJIvnWPw90Zn/J75nQueEpCORrIIL97Xp7oLNuqOO8283tu3/9PIP3yz3A13mJXShhLUUzXkbDPNXbCcxquw4v3HJ9goSlWEuYILeBZGVlYe7cufj+++/9i9xit9vx4YcfYuHChf5Ft1SKDEEuXLiAQ4cO+f/sFiEEDhw4AJvN2F1fGLLWIjDjVDbOZOo46dAw/ng2Djgz0L1l7969iIqKwqOPPupGnz59sHnzZlluMplw//33o3Hjxv5V3cJjqlatiieeeMK/6JZKgREkOzsbK1asQKtWrVClShXUqlULQ4Z4tjLkRywWC7p3744777wT58+f9y+WsmPHDlSsWBH9+vXzL8q3LLoi0PNAFmac8p0gm3U6B7W22rEjVcVGq8B9G2xYZgqcS9iyZYvUR5kyZdC8eXM8/PDD8l5atGiBEydOQFVVSe5Tpzwv0vOXIk+QdevW4cEHH0SDBg0wceJEjB07FuPHj/c/7KaE5Fu8eDHeeustZGYG2QbB2cwrV/Dee+8hLi7OvyhfwhFgs50OPLjJhppb7eBShStuzStBSGBe/9WrV1GhQgVpNUjsnJwcxMbGYuNG4z9q8n737duH5cuXY9OmTdIq+hPk3LlziI6OxurVq+FwOJCRkSGPZZ39+/dD836bQj6kwAiyYMEC3H333ejVqxcURZEmnxftkjlz5sjePXDgQBw9auxT+eyzzySR6FcHDBiAmTNnYsyYMdLf8ga//vprDB06FL/88gvmzZsnLVJamrFxav369XjppZfw3HPP4aOPPsKZM2fwxhtv4Msvv8S1a9dkW5MmTcKPP/4oz0uyushFZQ8bNgz9+/fHqlWr5DliYmLc1+otG6wqym20oUdSliTD3Aue/Sk3SxCev2zZsnjooYeQlJSE1NRUaVUiIyMlOaZOnYo6derI46tXry5dkTdB2Marr76KSpUqyXs8fvw4evToIY9lHbZbUK62wAhCJdSsWRN33XWXJAn9Lh80iTJ69Gjcd9996NmzJ2rXri198NmzZ/H888/L42vUqIG6deti1KhRKFmypFQIHzL99gMPPICLFy/imWeeQYkSJXDp0iV88803sl61atXQpEkT+aD37NkjFcTvVBjPce+996Jp06aoXLmyrPv555/DarXK8/F6+FDYBstmzZrlf0tyu+Mbx7JRfqMdK8wq2iU60HynQ/77dUpeCcLroQ4Ya9CCTJs2TQaoKSkp8joef/xxJCcny+ulnr799lsZvNL9eBMkPj4ef/rTn9C5c2dJqK+++kreT+vWrbFkyRLZGaj7gpACIwj9KE0egy+aznLlymHKlCmy91MZDRs2lJbi5ZdfRvny5bFs2TJJECrtgw8+kCTYtm2bVAIJxZ7FB0l3xR7mIggDVcYjd9xxB3bt2iWtFZUXjCC8DrPZLC0Vj6d1WrRokfw+ePBgqcSlS5eGJMi5LA0RiQ603OWAQwOGHcmSZNmcYpDgZggSEREhHy5jNVfM4U2Q9PR0+aCpP+rSRWoXQRo1aiQJVr9+femGKHQzjPuIESNG4Oeff/a7gpuXAiOIS+gCaK75cOrVqyfNPxVCUjzyyCOyR7PX01eSIOwtjF8ormCUZvK7776TZphK9CbIzp07pQJZxp37LglGEFolCslYqlQpjBw5UsYptFKzZ8+WZadPnw5JkPhkgapb7Ki82Y6/7HZIMpTdYMPkk9lyIiyvBGFH4f3wvhmwjhs3LsCC8J6OHDkiOwY7CIny/vvvuwny2GOPSdCykhgUtsH4g26a5yDWrPH8b9/8SIERhBe4YcMGqfDExETpH0kQspk3zx7DB8dgkm6CMQYVRdYznnAJH1zp0qXRpk0bSRb6W2+CUHl0Yfz+008/yWCN8ceNEoTBLi1I79695QiCsUowgpB7E0/QvdgkOVy4f6MNUfscuJKj55kgrhjk2LFj8jsJQHfqTRB2MLoIumhaN452hg8f7uNi6HLYsXh/1CdHj7Q0Bw8elBaEdebP9+xVzo8UGEGoeFoNPiR+8gZmzJghI2xaEf7OEQ57f9++faV5D0YQPmj2mnvuuUcGpiSSN0EuX74sRyq0QrQiNLU0xTdKEFqpLl26yICadRncsd23337b624g8yIYb7RN9B019TpgBKtbUtSbJgiFcQPvkYEpO4yLILxXDoVpYVjOOIwjHW+CMO6YPHmydFnUy6effirJRmLwGAblHCkVhBQYQThi+fXXX6W7YK9g73Qpg3HCb7/9Ji0MwUkvmlJ+sjd5B1QManksRzr0xxTGN5z/OHz4sCwnaVh369atSEhIkGWunslYhsNGmnJeA4Xt0PKQXBSSZPv27dJdMSYhQfjAvIULt8ccmsxa95ZrOTp+s2vg++PMOTqO2zUZnzAR66hdQ5B5Mnl/JC2vzzX85DXyHvkbHzjLaQlZzvthGcFgnS6EsRQ7EnVDIZH4nfUYo/B+eY8nT550j/QKQgqMIEVJaLk4Evrkk0+kK/zzn/8sg+LbVdgpOGwNNQd0K6VYEoTB3JNPPilHFIMGDZLDRj6EsARKsSQI3SF9NP16Qc0X/FGlWBIkLDcuYYKEJVcJEyQsucr/A6H5PWR+y3SaAAAAAElFTkSuQmCC";
    
    /**
     * City shown in Location field on payslip
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PAYSLIP_LOCATION_CITY = "Mumbai";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SPLASH_MSG_CONNECTING = "Connecting\u2026";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SPLASH_MSG_WAKING = "Waking up server\u2026";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SPLASH_MSG_ALMOST = "Almost ready\u2026";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SPLASH_MSG_LOADING = "Loading your workspace...";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SPLASH_MSG_STARTING = "Starting up\u2026";
    @org.jetbrains.annotations.NotNull()
    public static final com.riskcare.app.AndroidMain INSTANCE = null;
    
    private AndroidMain() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getCHAT_BLOCKED_EXTENSIONS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getPERFORMANCE_FINAL_RATINGS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPERMISSION_LOCATION_RATIONALE() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPERMISSION_BACKGROUND_SETTINGS_HINT() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.Pair<java.lang.String, java.lang.String> getBatteryOptSteps(@org.jetbrains.annotations.NotNull()
    java.lang.String brand) {
        return null;
    }
}