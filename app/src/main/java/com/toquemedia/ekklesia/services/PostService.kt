package com.toquemedia.ekklesia.services

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.toquemedia.ekklesia.model.PostType
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostService @Inject constructor(
    private val db: FirebaseFirestore
) {

    private val collection = "posts"

    suspend fun addPost(post: PostType) {
        db.collection(collection).document(post.verseId).set(post).await()
    }

    suspend fun removePost(verseId: String) {
        db.collection(collection).document(verseId).delete().await()
    }

    suspend fun getAllPosts(): List<PostType> {
        val snapshot = db.collection(collection)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .await()

        return snapshot.toObjects(PostType::class.java)
    }
}