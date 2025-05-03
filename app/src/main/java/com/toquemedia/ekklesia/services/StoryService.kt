package com.toquemedia.ekklesia.services

import com.google.firebase.firestore.FirebaseFirestore
import com.toquemedia.ekklesia.model.StoryType
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StoryService @Inject constructor(
    private val db: FirebaseFirestore
) {

    private val collection = "stories"

    suspend fun addStory(story: StoryType) {
        db.collection(collection).add(story).await()
    }
}