package com.toquemedia.ekklesia.services

import com.google.firebase.firestore.FirebaseFirestore
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
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
        db.collection(collection).document(community.id).set(community).await()
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
        return try {
            withContext(Dispatchers.IO) {
                db.collection(collection)
                    .document(communityId)
                    .collection(subCollection)
                    .get()
                    .await()
                    .toObjects(CommunityMemberType::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getCommunitiesOnFeed(): Flow<List<CommunityType>> = callbackFlow {
        val communityQuery = db.collection(collection)
            .whereEqualTo("active", true)

        val listenerRegistration = communityQuery.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val communities = snapshot?.toObjects(CommunityType::class.java)
            if (communities != null) {
                trySend(communities)
            }
        }

        awaitClose {
            listenerRegistration.remove()
        }
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

    suspend fun getCommunitiesUserIn(): List<CommunityWithMembers> {

        val ids = auth.getCommunitiesIn()

        if (ids.isEmpty()) return emptyList()

        val communityDocs = withContext(Dispatchers.IO) {
            val docRefs = ids.map { db.collection(collection).document(it) }

            db.runTransaction { transaction ->
                docRefs.map { docRef ->
                    transaction.get(docRef)
                }
            }.await()
        }

        val validCommunities = communityDocs
            .filter { it.exists() }
            .mapNotNull { doc ->
                doc.toObject(CommunityType::class.java)?.let { community ->
                    if (community.active == true) {
                        community to doc.reference
                    } else null
                }
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