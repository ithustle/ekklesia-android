package com.toquemedia.seedfy.model.interfaces

import com.toquemedia.seedfy.model.MessageEntity
import com.toquemedia.seedfy.model.MessageStatus
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun sendMessage(message: MessageEntity)
    suspend fun getMessages(communityId: String): Flow<List<MessageEntity>>
    suspend fun updateMessageStatus(status: MessageStatus, messageId: String)
}