package com.toquemedia.seedfy.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.toquemedia.seedfy.model.MessageStatus
import com.toquemedia.seedfy.model.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert
    suspend fun save(vararg message: MessageEntity)

    @Query("SELECT * FROM messages WHERE communityId = :communityId")
    fun getAll(communityId: String): Flow<List<MessageEntity>>

    @Query("UPDATE messages SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: String, status: MessageStatus)
}