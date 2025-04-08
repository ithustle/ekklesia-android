package com.toquemedia.ekklesia.services

import com.google.firebase.firestore.FirebaseFirestore
import com.toquemedia.ekklesia.model.CommunityEntity
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommunityService @Inject constructor(private val db: FirebaseFirestore) {

    private val collection: String = "communities"
    private val subCollection: String = "members"

    suspend fun createCommunity(data: CommunityWithMembers) {
        val community = data.community!!
        val member = data.allMembers!!.first()

        db.collection(collection).document(community.id).set(community).await()
        this.addMember(communityId = community.id, member = member)
    }

    suspend fun addMember(communityId: String, member: CommunityMemberType) {
        db.collection(collection)
            .document(communityId)
            .collection(subCollection)
            .document(member.id)
            .set(member)
            .await()
    }

    suspend fun getAllMembers(communityId: String): List<CommunityMemberType> {
        return db.collection(collection)
            .document(communityId)
            .collection(subCollection)
            .get()
            .await()
            .toObjects(CommunityMemberType::class.java)
    }

    suspend fun getAllWithQuery(email: String): List<CommunityEntity> {
        return db.collection(collection)
            .whereNotEqualTo("email", email)
            .get()
            .await()
            .toObjects(CommunityEntity::class.java)
    }

    suspend fun removeMember(communityId: String, memberId: String) {
        db.collection(collection)
            .document(communityId)
            .collection(subCollection)
            .document(memberId)
            .delete()
            .await()
    }

    suspend fun removeCommunity(id: String) {
        db.collection(collection).document(id).delete().await()
    }

    suspend fun getCommunitiesUserIn(ids: List<String>): List<CommunityWithMembers> {
        if (ids.isEmpty()) return emptyList()

        val communityDocs = withContext(Dispatchers.IO) {
            val docRefs = ids.map { db.collection(collection).document(it) }

            db.runTransaction { transaction ->
                docRefs.map { docRef -> transaction.get(docRef) }
            }.await()
        }

        val validCommunities = communityDocs
            .filter { it.exists() }
            .mapNotNull { doc ->
                doc.toObject(CommunityEntity::class.java)?.let { it to doc.reference }
            }

        return coroutineScope {
            validCommunities.map { (community, reference) ->
                async {
                    val members = reference.collection(subCollection).get().await()
                        .toObjects(CommunityMemberType::class.java)
                    CommunityWithMembers(community, members)
                }
            }.awaitAll()
        }
    }
}