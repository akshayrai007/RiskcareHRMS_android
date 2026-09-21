package com.riskcare.app.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\u001a\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00162\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\nR\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/riskcare/app/data/api/RetrofitClient;", "", "()V", "BASE_URL", "", "getBASE_URL", "()Ljava/lang/String;", "_instance", "Lcom/riskcare/app/data/api/ApiService;", "appContext", "Landroid/content/Context;", "authInterceptor", "Lokhttp3/Interceptor;", "gson", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "instance", "getInstance", "()Lcom/riskcare/app/data/api/ApiService;", "loggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "sessionManager", "Lcom/riskcare/app/utils/SessionManager;", "unauthorizedInterceptor", "buildClient", "Lokhttp3/OkHttpClient;", "init", "", "sm", "context", "app_debug"})
public final class RetrofitClient {
    @org.jetbrains.annotations.Nullable()
    private static com.riskcare.app.utils.SessionManager sessionManager;
    @org.jetbrains.annotations.Nullable()
    private static android.content.Context appContext;
    @org.jetbrains.annotations.Nullable()
    private static com.riskcare.app.data.api.ApiService _instance;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.Interceptor authInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.Interceptor unauthorizedInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.logging.HttpLoggingInterceptor loggingInterceptor = null;
    private static final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.riskcare.app.data.api.RetrofitClient INSTANCE = null;
    
    private RetrofitClient() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBASE_URL() {
        return null;
    }
    
    public final void init(@org.jetbrains.annotations.NotNull()
    com.riskcare.app.utils.SessionManager sm, @org.jetbrains.annotations.Nullable()
    android.content.Context context) {
    }
    
    private final okhttp3.OkHttpClient buildClient() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.riskcare.app.data.api.ApiService getInstance() {
        return null;
    }
}