package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.WorshipEntity
import kotlinx.coroutines.flow.Flow

interface WorshipRepository {
    suspend fun saveWorship(worship: WorshipEntity)
    suspend fun updateWorship(worship: WorshipEntity)
    suspend fun deleteWorship(id: String)
    suspend fun shareToCommunity(worship: WorshipEntity)
    fun getAllWorships(): Flow<List<WorshipEntity>>
}