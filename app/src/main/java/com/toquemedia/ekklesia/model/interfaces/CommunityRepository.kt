package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.CommunityEntity
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.UserType
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun createCommunity(name: String, description: String, image: String, user: UserType?)
    suspend fun addMember(communityId: String, member: CommunityMemberType)
    suspend fun getAll(): Flow<List<CommunityEntity>>
    suspend fun getAll(email: String): List<CommunityEntity>
    suspend fun getAllMembers(communityId: String): List<CommunityMemberType>
    suspend fun removeMember(communityId: String, memberId: String)
    suspend fun deleteCommunity(id: String)
    suspend fun getCommunitiesUserIn(ids: List<String>): List<CommunityWithMembers>
    suspend fun getCommunitiesUserInIds(): Flow<List<String>>
}