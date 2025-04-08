package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.dao.MessageDao
import com.toquemedia.ekklesia.model.MessageEntity
import com.toquemedia.ekklesia.model.MessageStatus
import com.toquemedia.ekklesia.model.interfaces.MessageRepository
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