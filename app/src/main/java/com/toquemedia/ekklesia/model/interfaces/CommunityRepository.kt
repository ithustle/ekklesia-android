package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.CommunityEntity
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun createCommunity(name: String, description: String, image: String)
    suspend fun getAll(): Flow<List<CommunityEntity>>
    suspend fun deleteCommunity(id: String)
}