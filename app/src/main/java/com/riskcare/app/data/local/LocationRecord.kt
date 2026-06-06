package com.riskcare.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_queue")
data class LocationRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lat: Double,
    val lng: Double,
    val accuracy: Float,
    val timestamp: Long,
    val isOd: Boolean = false,
    val synced: Boolean = false
)
