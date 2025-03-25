package com.toquemedia.ekklesia.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.toquemedia.ekklesia.model.CommunityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommunityDao {
    @Insert
    suspend fun insert(community: CommunityEntity)
    @Query("SELECT * FROM communities")
    fun getAll(): Flow<List<CommunityEntity>>
    @Query("DELETE FROM communities WHERE id=:id")
    fun deleteCommunity(id: String)
}
