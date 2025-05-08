package com.toquemedia.seedfy.services

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction.DESCENDING
import com.toquemedia.seedfy.model.StoryType
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class StoryService @Inject constructor(
    private val db: FirebaseFirestore
) {

    private val collection = "stories"

    suspend fun addStory(story: StoryType) {
        db.collection(collection).add(story).await()
    }

    suspend fun getStories(communityId: String): List<StoryType> {
        val snapshot = db.collection(collection)
            .whereArrayContains("communityId", communityId)
            .whereGreaterThanOrEqualTo("expirationDate", Date())
            .orderBy("createdAt", DESCENDING)
            .get().await()
        return snapshot.toObjects(StoryType::class.java)
    }
}