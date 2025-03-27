package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.CommunityEntity
import com.toquemedia.ekklesia.model.CommunityType
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun createCommunity(name: String, description: String, image: String, email: String?)
    suspend fun getAllLocal(): Flow<List<CommunityEntity>>
    suspend fun getAll(): List<CommunityType>
    suspend fun deleteCommunity(id: String)
}