package com.toquemedia.ekklesia.services

import com.toquemedia.ekklesia.dao.FirebaseFirestoreDao
import com.toquemedia.ekklesia.model.CommunityEntity

class CommunityService : FirebaseFirestoreDao() {

    private val collection: String = "communities"

    suspend fun createCommunity(community: CommunityEntity) {
        save(this.collection, community.id, community)
    }

    suspend fun getAll(): List<CommunityEntity> {
        return getAll<CommunityEntity>(collection)
    }

    suspend fun removeCommunity(id: String) {
        remove(collection, id)
    }
}