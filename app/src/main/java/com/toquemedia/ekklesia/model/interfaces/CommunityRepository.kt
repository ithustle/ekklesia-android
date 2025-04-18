package com.toquemedia.ekklesia.model.interfaces

import android.net.Uri
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.UserType
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun createCommunity(name: String, description: String, image: Uri?, user: UserType?): CommunityWithMembers?
    suspend fun addMember(communityId: String, member: CommunityMemberType)
    suspend fun getAll(): Flow<List<CommunityType>>
    suspend fun getAllMembers(communityId: String): List<CommunityMemberType>
    suspend fun removeMember(communityId: String, memberId: String)
    suspend fun deleteCommunity(id: String)
    suspend fun getCommunitiesUserIn(): List<CommunityWithMembers>
}