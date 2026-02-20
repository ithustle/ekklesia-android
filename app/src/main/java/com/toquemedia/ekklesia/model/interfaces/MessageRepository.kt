package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.MessageEntity
import com.toquemedia.ekklesia.model.MessageStatus
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun sendMessage(message: MessageEntity)
    suspend fun getMessages(communityId: String): Flow<List<MessageEntity>>
    suspend fun updateMessageStatus(status: MessageStatus, messageId: String)
}