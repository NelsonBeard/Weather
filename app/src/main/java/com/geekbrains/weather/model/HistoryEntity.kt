package com.geekbrains.weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val city: String,
    val temperature: Int,
    val condition: String,
    val timestamp: Long
)