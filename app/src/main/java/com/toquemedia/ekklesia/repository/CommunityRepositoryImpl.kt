package com.toquemedia.ekklesia.repository

import android.content.Context
import com.toquemedia.ekklesia.dao.CommunityDao
import com.toquemedia.ekklesia.dao.CommunityInsiderDao
import com.toquemedia.ekklesia.extension.uriToBase64
import com.toquemedia.ekklesia.model.CommunityEntity
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.UserType
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
    private val dao : CommunityDao,
    private val communityInsiderDao: CommunityInsiderDao
) : CommunityRepository {

    override suspend fun createCommunity(name: String, description: String, image: String, user: UserType?) {
        withContext(Dispatchers.IO) {
            user?.email?.let {
                val community = CommunityEntity(
                    id = UUID.randomUUID().toString(),
                    communityName = name.trim(),
                    communityDescription = description,
                    communityImage = image.uriToBase64(context),
                    members = 1,
                    email = it
                )

                try {
                    dao.insert(community)
                    try {
                        val member = CommunityMemberType(
                            id = user.id,
                            user = user,
                            isAdmin = true
                        )
                        val communityWithMembers = CommunityWithMembers(
                            community = community,
                            allMembers = listOf(member)
                        )

                        service.createCommunity(communityWithMembers)
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
    }

    override suspend fun addMember(
        communityId: String,
        member: CommunityMemberType
    ) {
        withContext(Dispatchers.IO) {
            dao.updateMembers(communityId)
            service.addMember(communityId, member)
            communityInsiderDao.saveCommunity(communityId)
        }
    }

    override suspend fun getAll(): Flow<List<CommunityEntity>> {
        return dao.getAll()
    }

    override suspend fun getAll(email: String): List<CommunityEntity> {
        return service.getAllWithQuery(email)
    }

    override suspend fun getAllMembers(communityId: String): List<CommunityMemberType> {
        return service.getAllMembers(communityId)
    }

    override suspend fun removeMember(communityId: String, memberId: String) {
        service.removeMember(communityId, memberId)
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

    override suspend fun getCommunitiesUserIn(ids: List<String>): List<CommunityWithMembers> {
        return service.getCommunitiesUserIn(ids)
    }

    override suspend fun getCommunitiesUserInIds(): Flow<List<String>> {
        return communityInsiderDao.getAll()
    }
}