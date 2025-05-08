package com.toquemedia.seedfy.model.interfaces

import android.net.Uri
import com.toquemedia.seedfy.model.CommunityMemberType
import com.toquemedia.seedfy.model.CommunityType
import com.toquemedia.seedfy.model.CommunityWithMembers
import com.toquemedia.seedfy.model.UserType
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