package com.riskcare.app.data.repository;

/**
 * LocationRepository — single source of truth for location operations.
 *
 * Strategy:
 * 1. Always save to local Room DB first (offline-safe)
 * 2. Attempt immediate upload to backend
 * 3. If upload fails → leave in DB (unsynced)
 * 4. SyncWorker picks up unsynced records every 15 minutes
 *
 * Gate rules (applied ONLY for regular pings, NOT for punch-in):
 * 1. Dedup    : at least 25 seconds since last saved point
 * 2. Accuracy : GPS reading must be <= 50m
 * 3. Distance gate REMOVED — ping every 30s regardless of movement.
 *    Stationary employees must also be tracked (office, meetings etc.)
 *
 * Punch-in point (isPunchIn = true) bypasses ALL gates — it must
 * always be saved as the anchor point for the day's route.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u001cB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ:\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\r2\b\b\u0002\u0010\u0014\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u0015J\u000e\u0010\u0016\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u000bJ6\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\rH\u0082@\u00a2\u0006\u0002\u0010\u001bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/riskcare/app/data/repository/LocationRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "dao", "Lcom/riskcare/app/data/local/LocationQueueDao;", "tag", "", "getPendingCount", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveAndSync", "", "lat", "", "lng", "accuracy", "", "isOd", "isPunchIn", "(DDFZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncPending", "Lcom/riskcare/app/data/repository/LocationRepository$SyncResult;", "tryUploadSingle", "id", "", "(JDDFZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "SyncResult", "app_debug"})
public final class LocationRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.riskcare.app.data.local.LocationQueueDao dao = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tag = "LocationRepository";
    
    public LocationRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Save a location point locally and immediately attempt upload.
     *
     * @param isPunchIn  Set true for the punch-in first point — bypasses all
     *                  gates so the anchor is always recorded even indoors.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveAndSync(double lat, double lng, float accuracy, boolean isOd, boolean isPunchIn, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Upload a single record to backend. Marks synced on success.
     */
    private final java.lang.Object tryUploadSingle(long id, double lat, double lng, float accuracy, boolean isOd, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Batch sync all pending records — called by SyncWorker every 15 min.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncPending(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.riskcare.app.data.repository.LocationRepository.SyncResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPendingCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/riskcare/app/data/repository/LocationRepository$SyncResult;", "", "uploaded", "", "failed", "(II)V", "getFailed", "()I", "getUploaded", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class SyncResult {
        private final int uploaded = 0;
        private final int failed = 0;
        
        public SyncResult(int uploaded, int failed) {
            super();
        }
        
        public final int getUploaded() {
            return 0;
        }
        
        public final int getFailed() {
            return 0;
        }
        
        public final int component1() {
            return 0;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.riskcare.app.data.repository.LocationRepository.SyncResult copy(int uploaded, int failed) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}