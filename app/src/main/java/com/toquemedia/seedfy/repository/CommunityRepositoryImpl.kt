package com.toquemedia.seedfy.repository

import android.net.Uri
import com.toquemedia.seedfy.model.CommunityMemberType
import com.toquemedia.seedfy.model.CommunityType
import com.toquemedia.seedfy.model.CommunityWithMembers
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.model.interfaces.CommunityRepository
import com.toquemedia.seedfy.services.CommunityService
import com.toquemedia.seedfy.services.NotificationService
import com.toquemedia.seedfy.services.StorageService
import com.toquemedia.seedfy.services.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val service: CommunityService,
    private val storage: StorageService,
    private val auth: UserService,
    private val notification: NotificationService
) : CommunityRepository {

    override suspend fun createCommunity(
        name: String,
        description: String,
        image: Uri?,
        user: UserType?
    ): CommunityWithMembers? {
        return withContext(Dispatchers.IO) {
            val email = user?.email ?: return@withContext null

            val communityId = UUID.randomUUID().toString()
            val imageUrl = storage.uploadImage(communityId, image)

            val community = CommunityType(
                id = communityId,
                communityName = name.trim(),
                communityDescription = description,
                communityImage = imageUrl.toString(),
                email = email
            )

            val member = CommunityMemberType(
                id = user.id,
                user = user,
                admin = true
            )

            try {
                val communityWithMembers = CommunityWithMembers(
                    community = community,
                    allMembers = listOf(member)
                )

                val createCommunityJob =  async { service.createCommunity(communityWithMembers) }
                val notifyJob = async { notification.subscribeToTopicForNotification(communityId) }

                createCommunityJob.await()
                notifyJob.await()

                communityWithMembers
            } catch (firestoreException: Exception) {
                throw firestoreException
            }
        }
    }

    override suspend fun addMember(
        communityId: String,
        member: CommunityMemberType
    ) {
        withContext(Dispatchers.IO) {
            val addMemberJob = async { service.addMember(communityId, member) }
            val saveInCommunityJob = async { auth.saveCommunityIn(communityId) }
            val notifyJob = async { notification.subscribeToTopicForNotification(communityId) }

            addMemberJob.await()
            saveInCommunityJob.await()
            notifyJob.await()
        }
    }

    override suspend fun getAll(): Flow<List<CommunityType>> {
        return service.getCommunitiesOnFeed()
    }

    override suspend fun getAllMembers(communityId: String): List<CommunityMemberType> {
        return service.getAllMembers(communityId)
    }

    override suspend fun removeMember(communityId: String, memberId: String) {
        withContext(Dispatchers.IO) {
            val removedJob = async { service.removeMember(communityId, memberId) }
            val notificationJob = async { notification.unsubscribeToTopicForNotification(communityId) }

            removedJob.await()
            notificationJob.await()
        }
    }

    override suspend fun deleteCommunity(id: String) {
        withContext(Dispatchers.IO) {
            try {
                val removeCommunityJob = async { service.removeCommunity(id) }
                val removeNotificationJob = async { notification.unsubscribeToTopicForNotification(id) }

                removeCommunityJob.await()
                removeNotificationJob.await()
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun getCommunitiesUserIn(): List<CommunityWithMembers> {
        return service.getCommunitiesUserIn()
    }
}