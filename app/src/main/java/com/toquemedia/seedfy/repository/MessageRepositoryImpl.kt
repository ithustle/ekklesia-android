package com.toquemedia.seedfy.repository

import com.toquemedia.seedfy.dao.MessageDao
import com.toquemedia.seedfy.model.MessageEntity
import com.toquemedia.seedfy.model.MessageStatus
import com.toquemedia.seedfy.model.interfaces.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val dao: MessageDao,
) : MessageRepository {
    override suspend fun sendMessage(message: MessageEntity) {
        dao.save(message)
    }

    override suspend fun getMessages(communityId: String): Flow<List<MessageEntity>> {
        return dao.getAll(communityId)
    }

    override suspend fun updateMessageStatus(
        status: MessageStatus,
        messageId: String
    ) {
        TODO("Not yet implemented")
    }
}