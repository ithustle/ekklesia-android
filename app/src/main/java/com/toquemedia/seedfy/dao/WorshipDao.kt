package com.toquemedia.seedfy.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.toquemedia.seedfy.model.WorshipEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorshipDao {
    @Insert
    suspend fun insert(worship: WorshipEntity)

    @Update
    suspend fun update(worship: WorshipEntity)

    @Query("UPDATE worships SET post_id = :postId, community_id = :communityId WHERE id = :id")
    suspend fun associatePostToWorship(postId: String, communityId: String, id: String)

    @Query("DELETE FROM worships WHERE id = :id")
    suspend fun deleteWorship(id: String)

    @Query("SELECT * FROM worships")
    fun getAll(): Flow<List<WorshipEntity>>
}