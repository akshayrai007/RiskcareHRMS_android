package com.krishihr.app.data.local

import androidx.room.*

// ── Room Entity — offline queue ────────────────────────────────────────────────
@Entity(tableName = "location_queue")
data class LocationRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val lat: Double,
    val lng: Double,
    val accuracy: Float,
    val timestamp: Long = System.currentTimeMillis(),
    val isOd: Boolean = false,
    val synced: Boolean = false,
    val retryCount: Int = 0
)

// ── DAO ────────────────────────────────────────────────────────────────────────
@Dao
interface LocationQueueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: LocationRecord): Long

    /** Get all unsynced records, oldest first — for batch upload */
    @Query("SELECT * FROM location_queue WHERE synced = 0 ORDER BY timestamp ASC LIMIT 50")
    suspend fun getPendingRecords(): List<LocationRecord>

    /** Mark as synced after successful upload */
    @Query("UPDATE location_queue SET synced = 1 WHERE id IN (:ids)")
    suspend fun markSynced(ids: List<Long>)

    /** Increment retry count for failed records */
    @Query("UPDATE location_queue SET retryCount = retryCount + 1 WHERE id IN (:ids)")
    suspend fun incrementRetry(ids: List<Long>)

    /** Delete records that have been synced or exceeded max retries — keep DB clean */
    @Query("DELETE FROM location_queue WHERE synced = 1 OR retryCount >= 5")
    suspend fun cleanup()

    @Query("SELECT COUNT(*) FROM location_queue WHERE synced = 0")
    suspend fun getPendingCount(): Int

    /** Get the most recently inserted record — used for deduplication */
    @Query("SELECT * FROM location_queue ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastRecord(): LocationRecord?
}

// ── Database ───────────────────────────────────────────────────────────────────
@Database(entities = [LocationRecord::class], version = 2, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationQueueDao(): LocationQueueDao

    companion object {
        @Volatile private var INSTANCE: LocationDatabase? = null

        fun getInstance(context: android.content.Context): LocationDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    LocationDatabase::class.java,
                    "location_queue.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
    }
}