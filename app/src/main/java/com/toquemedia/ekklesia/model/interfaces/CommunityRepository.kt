package com.toquemedia.ekklesia.model.interfaces

import android.content.Context
import com.toquemedia.ekklesia.model.CommunityType
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun createCommunity(name: String, description: String, image: String)
    suspend fun getAll(): Flow<List<CommunityType>>
    suspend fun deleteCommunity(id: String)
}