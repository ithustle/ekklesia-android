package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.CommunityEntity
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun createCommunity(name: String, description: String, image: String, email: String?)
    suspend fun getAllLocal(): Flow<List<CommunityEntity>>
    suspend fun getAll(): List<CommunityEntity>
    suspend fun deleteCommunity(id: String)
}