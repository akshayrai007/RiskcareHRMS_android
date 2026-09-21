package com.riskcare.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0012B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\bJ\u0006\u0010\u000f\u001a\u00020\nJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u0005J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u0005R\u001c\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/riskcare/app/utils/AccessControl;", "", "()V", "cache", "", "", "Lcom/riskcare/app/data/models/EffectivePageAccess;", "loading", "", "ensureLoaded", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasAccess", "pageKey", "fallback", "invalidate", "levelFor", "scopeFor", "PageKeys", "app_debug"})
public final class AccessControl {
    @org.jetbrains.annotations.Nullable()
    private static java.util.Map<java.lang.String, com.riskcare.app.data.models.EffectivePageAccess> cache;
    private static boolean loading = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.riskcare.app.utils.AccessControl INSTANCE = null;
    
    private AccessControl() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object ensureLoaded(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void invalidate() {
    }
    
    /**
     * hasAccess: true if this page's effective access level isn't "none".
     * If the page isn't in the catalog (or the fetch hasn't completed/failed),
     * falls back to [fallback] — the old Roles.kt-based decision — so a screen
     * never definition-of-fails-locked before the fetch resolves.
     */
    public final boolean hasAccess(@org.jetbrains.annotations.NotNull()
    java.lang.String pageKey, boolean fallback) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String levelFor(@org.jetbrains.annotations.NotNull()
    java.lang.String pageKey) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String scopeFor(@org.jetbrains.annotations.NotNull()
    java.lang.String pageKey) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0016\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/riskcare/app/utils/AccessControl$PageKeys;", "", "()V", "ADVANCE", "", "ATTENDANCE", "BOARD", "CHAT", "DASHBOARD", "DOCUMENTS", "EMPLOYEES", "FORM16", "GEOFENCE", "IT_DECLARATION", "LEAVES", "MY_WORK", "OFFER_LETTER", "ORG_CHART", "PAYROLL", "PAYSLIP", "PROVISION", "REIMBURSEMENT", "RELIEVING_LETTER", "SEPARATION", "TASKS", "WORK_TRACKER", "app_debug"})
    public static final class PageKeys {
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String DASHBOARD = "dashboard.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String ATTENDANCE = "attendance.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String LEAVES = "leaves.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String CHAT = "chat.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String BOARD = "board.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String TASKS = "tasks.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String MY_WORK = "my-work.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String WORK_TRACKER = "work-tracker.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String DOCUMENTS = "documents.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String FORM16 = "form16.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String IT_DECLARATION = "it-declaration.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String PAYROLL = "payroll.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String PAYSLIP = "payslip.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String ADVANCE = "advance.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String REIMBURSEMENT = "reimbursement.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String PROVISION = "provision.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String OFFER_LETTER = "offer-letter.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String RELIEVING_LETTER = "relieving-letter.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String SEPARATION = "separation.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String EMPLOYEES = "employees.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String ORG_CHART = "org-chart.html";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String GEOFENCE = "geofence.html";
        @org.jetbrains.annotations.NotNull()
        public static final com.riskcare.app.utils.AccessControl.PageKeys INSTANCE = null;
        
        private PageKeys() {
            super();
        }
    }
}