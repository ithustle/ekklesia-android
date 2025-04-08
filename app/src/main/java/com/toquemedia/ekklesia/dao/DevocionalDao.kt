package com.toquemedia.ekklesia.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.toquemedia.ekklesia.model.DevocionalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DevocionalDao {
    @Insert
    suspend fun insert(devocional: DevocionalEntity)
    @Query("SELECT * FROM devocional")
    fun getAll(): Flow<List<DevocionalEntity>>
}