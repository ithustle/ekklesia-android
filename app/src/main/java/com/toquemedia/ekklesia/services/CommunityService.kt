package com.toquemedia.ekklesia.services

import com.toquemedia.ekklesia.dao.FirebaseFirestoreDao
import com.toquemedia.ekklesia.model.CommunityEntity
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityWithMembers

class CommunityService : FirebaseFirestoreDao() {

    private val collection: String = "communities"
    private val subCollection: String = "members"

    suspend fun createCommunity(data: CommunityWithMembers) {
        val community = data.community!!
        val member = data.allMembers!!.first()

        save(
            collection = collection,
            id = community.id,
            data = community
        )

        this.addMember(
            communityId = community.id,
            member = member
        )
    }

    suspend fun addMember(communityId: String, member: CommunityMemberType) {
        saveSubCollection(
            collection = this.collection,
            id = communityId,
            subCollection = subCollection,
            subId = member.id,
            data = member
        )
    }

    suspend fun getAllMembers(communityId: String): List<CommunityMemberType> {
        return getOnSubCollection(this.collection, communityId, this.subCollection)
    }

    suspend fun getAllWithQuery(email: String): List<CommunityEntity> {
        return getAllWithQuery<CommunityEntity>(collection, email)
    }

    suspend fun removeMember(communityId: String, memberId: String) {
        removeSubCollection(
            collection = this.collection,
            id = communityId,
            subCollection = subCollection,
            subId = memberId
        )
    }

    suspend fun removeCommunity(id: String) {
        remove(collection, id)
    }
}