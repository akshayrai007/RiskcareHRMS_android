package com.riskcare.app.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u000b\u001a\u00020\u00032\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001c\u0010\u0012\u001a\u00020\u00032\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nH\u00a7@\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006\u0013"}, d2 = {"Lcom/riskcare/app/data/local/LocationQueueDao;", "", "deleteSynced", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLastRecord", "Lcom/riskcare/app/data/local/LocationRecord;", "getPendingCount", "", "getUnsynced", "", "incrementRetry", "ids", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "record", "(Lcom/riskcare/app/data/local/LocationRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markSynced", "app_debug"})
@androidx.room.Dao()
public abstract interface LocationQueueDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.riskcare.app.data.local.LocationRecord record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM location_queue WHERE synced = 0 ORDER BY timestamp ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnsynced(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.riskcare.app.data.local.LocationRecord>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM location_queue ORDER BY timestamp DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLastRecord(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.riskcare.app.data.local.LocationRecord> $completion);
    
    @androidx.room.Query(value = "UPDATE location_queue SET synced = 1 WHERE id IN (:ids)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markSynced(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Long> ids, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM location_queue WHERE synced = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSynced(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE location_queue SET retryCount = retryCount + 1 WHERE id IN (:ids)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object incrementRetry(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Long> ids, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM location_queue WHERE synced = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPendingCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}