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

    @Query("UPDATE location_queue SET synced = 1 WHERE id = :id")
    suspend fun markSynced(id: Int)

    @Query("DELETE FROM location_queue WHERE synced = 1")
    suspend fun deleteSynced()
}
