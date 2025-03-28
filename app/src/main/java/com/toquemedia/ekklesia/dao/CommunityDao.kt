package com.toquemedia.ekklesia.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.toquemedia.ekklesia.model.CommunityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommunityDao {
    @Insert
    suspend fun insert(community: CommunityEntity)
    @Update
    suspend fun update(community: CommunityEntity)
    @Query("UPDATE communities SET members = members + 1 WHERE id = :id")
    suspend fun updateMembers(id: String)
    @Query("SELECT * FROM communities")
    fun getAll(): Flow<List<CommunityEntity>>
    @Query("DELETE FROM communities WHERE id=:id")
    fun deleteCommunity(id: String)
}
