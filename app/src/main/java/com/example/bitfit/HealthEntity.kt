package com.example.bitfit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_table")
data class HealthEntity(

    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "calories") val calories: String?,
    @ColumnInfo(name = "sleep") val sleep: String?,
    @ColumnInfo(name = "no_calories") val noCalories: String?,
    @ColumnInfo(name = "no_sleep") val noSleep: String?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)