package com.toquemedia.ekklesia.repository

import android.content.Context
import com.toquemedia.ekklesia.dao.CommunityDao
import com.toquemedia.ekklesia.extension.uriToBase64
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.model.interfaces.CommunityRepository
import com.toquemedia.ekklesia.services.CommunityService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service : CommunityService,
    private val dao : CommunityDao
) : CommunityRepository {

    override suspend fun createCommunity(name: String, description: String, image: String) {
        withContext(Dispatchers.IO) {
            val community = CommunityType(
                id = UUID.randomUUID().toString(),
                communityName = name.trim(),
                communityDescription = description,
                communityImage = image.uriToBase64(context)
            )

            try {
                dao.insert(community)

                try {
                    service.createCommunity(community)
                } catch (firestoreException: Exception) {
                    dao.deleteCommunity(community.id)
                    throw firestoreException
                }

            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    override suspend fun getAll(): Flow<List<CommunityType>> {
        return dao.getAll()
    }

    override suspend fun deleteCommunity(id: String) {
        withContext(Dispatchers.IO) {
            try {
                service.removeCommunity(id)
                dao.deleteCommunity(id)
            } catch (e: Exception) {
                throw e
            }
        }
    }
}