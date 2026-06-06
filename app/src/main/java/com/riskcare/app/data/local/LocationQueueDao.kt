package com.riskcare.app.data.local

import androidx.room.*

@Dao
interface LocationQueueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: LocationRecord): Long

    @Query("SELECT * FROM location_queue WHERE synced = 0 ORDER BY timestamp ASC")
    suspend fun getUnsynced(): List<LocationRecord>

    @Query("SELECT * FROM location_queue ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastRecord(): LocationRecord?

    @Query("UPDATE location_queue SET synced = 1 WHERE id IN (:ids)")
    suspend fun markSynced(ids: List<Long>)

    @Query("DELETE FROM location_queue WHERE synced = 1")
    suspend fun deleteSynced()

    @Query("UPDATE location_queue SET retryCount = retryCount + 1 WHERE id IN (:ids)")
    suspend fun incrementRetry(ids: List<Long>)

    @Query("SELECT COUNT(*) FROM location_queue WHERE synced = 0")
    suspend fun getPendingCount(): Int
}
