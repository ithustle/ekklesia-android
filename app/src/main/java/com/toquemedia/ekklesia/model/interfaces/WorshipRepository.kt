package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.WorshipEntity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface WorshipRepository {
    suspend fun saveWorship(worship: WorshipEntity)
    suspend fun updateWorship(worship: WorshipEntity)
    suspend fun deleteWorship(id: String)
    suspend fun shareToCommunity(communityId: String, worship: WorshipEntity): Deferred<Unit>
    fun getAllWorships(): Flow<List<WorshipEntity>>
}