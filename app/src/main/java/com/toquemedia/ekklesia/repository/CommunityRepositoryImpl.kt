package com.toquemedia.ekklesia.repository

import android.net.Uri
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.model.interfaces.CommunityRepository
import com.toquemedia.ekklesia.services.CommunityService
import com.toquemedia.ekklesia.services.StorageService
import com.toquemedia.ekklesia.services.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val service : CommunityService,
    private val storage: StorageService,
    private val auth: UserService
) : CommunityRepository {

    override suspend fun createCommunity(name: String, description: String, image: Uri?, user: UserType?): CommunityWithMembers? {
        return withContext(Dispatchers.IO) {
            user?.email?.let {
                val communityId = UUID.randomUUID().toString()
                val imageUrl = async {
                    storage.uploadImage(communityId, image)
                }.await()

                val community = CommunityType(
                    id = communityId,
                    communityName = name.trim(),
                    communityDescription = description,
                    communityImage = imageUrl.toString(),
                    email = it
                )

                val member = CommunityMemberType(
                    id = user.id,
                    user = user,
                    isAdmin = true
                )

                try {
                    val communityWithMembers = CommunityWithMembers(
                        community = community,
                        allMembers = listOf(member)
                    )

                    async {
                        service.createCommunity(communityWithMembers)
                    }.await()

                    async {
                        service.addMember(communityId = community.id, member = member)
                    }.await()

                    communityWithMembers
                } catch (firestoreException: Exception) {
                    throw firestoreException
                    null
                }
            }
        }
    }

    override suspend fun addMember(
        communityId: String,
        member: CommunityMemberType
    ) {
        withContext(Dispatchers.IO) {
            service.addMember(communityId, member)
            auth.saveCommunityIn(communityId)
        }
    }

    override suspend fun getAll(): Flow<List<CommunityType>> {
        return service.getCommunitiesOnFeed()
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
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun getCommunitiesUserIn(): List<CommunityWithMembers> {
        return service.getCommunitiesUserIn()
    }
}