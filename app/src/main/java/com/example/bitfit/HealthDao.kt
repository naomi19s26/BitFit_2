package com.example.bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDao {
    @Query("SELECT * FROM health_table")
    fun getAll(): Flow<List<HealthEntity>>

    @Insert
    fun insert(HealthEntity: HealthEntity)

    @Insert
    fun insertAll(articles: List<HealthEntity>)

    @Query("DELETE FROM health_table")
    fun deleteAll()
}
