package com.toquemedia.ekklesia.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.toquemedia.ekklesia.model.CommunityType
import kotlinx.coroutines.flow.Flow

@Dao
interface CommunityDao {
    @Insert
    suspend fun insert(community: CommunityType)
    @Query("SELECT * FROM communities")
    fun getAll(): Flow<List<CommunityType>>
    @Query("DELETE FROM communities WHERE id=:id")
    fun deleteCommunity(id: String)
}
