package com.toquemedia.ekklesia.services

import com.google.firebase.firestore.FirebaseFirestore
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommunityService @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: UserService
) {

    private val collection: String = "communities"
    private val subCollection: String = "members"

    suspend fun createCommunity(data: CommunityWithMembers) {
        val community = data.community
        val member = data.allMembers.first()

        db.collection(collection).document(community.id).set(community).await()
        this.addMember(communityId = community.id, member = member)
        auth.saveCommunityIn(community.id)
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

    fun getCommunitiesOnFeed(): Flow<List<CommunityType>> {
        val communities: MutableStateFlow<List<CommunityType>> = MutableStateFlow(emptyList())

        db.collection(collection)
            .whereEqualTo("active", true)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val c = snapshot?.toObjects(CommunityType::class.java)
                if (c != null) {
                    communities.value = c
                }
            }

        return communities
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
        db.collection(collection).document(id).update("active", false).await()
    }

    suspend fun getCommunitiesUserIn(email: String): List<CommunityWithMembers> {

        val ids = auth.getCommunitiesIn(email)

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
                doc.toObject(CommunityType::class.java)?.let { it to doc.reference }
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