package com.toquemedia.seedfy.services

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.toquemedia.seedfy.model.CommentType
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.model.UserType
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostService @Inject constructor(
    private val db: FirebaseFirestore
) {
    private val collection = "posts"
    private val subCollectionComments = "comments"
    private val subCollectionLikes = "likes"

    suspend fun addPost(post: PostType, isUpdate: Boolean = false) {
        if (isUpdate) {
            db.collection(collection).document(post.verseId).set(post, SetOptions.merge()).await()
        } else {
            db.collection(collection).document(post.verseId).set(post).await()
        }
    }

    suspend fun removePost(postId: String) {
        db.collection(collection).document(postId).delete().await()
    }

    suspend fun getAllPosts(communityId: String): List<PostType> {
        val snapshot = db.collection(collection)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .whereArrayContains("communityId", communityId)
            .get()
            .await()

        return snapshot.toObjects(PostType::class.java)
    }

    suspend fun addComment(postId: String, communityId: String, comment: CommentType) {
        db.collection(collection)
            .document(postId)
            .collection(subCollectionComments)
            .document("${comment.id}_$communityId")
            .set(comment)
            .await()
    }

    suspend fun getComments(postId: String, communityId: String, limit: Long): MutableList<CommentType> {
        val snapshot = db.collection(collection).document(postId)
            .collection(subCollectionComments)
            .whereEqualTo("communityId", communityId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(limit)
            .get()
            .await()

        return snapshot.toObjects(CommentType::class.java)
    }

    suspend fun addLikeOnPost(postId: String, communityId: String, user: UserType) {
        try {
            val ref = db.collection(collection).document(postId)
            val likeRef = ref.collection(subCollectionLikes).document("${postId}_$communityId")

            db.runTransaction { transaction ->
                transaction.update(ref, "likes", FieldValue.increment(1))
                transaction.set(likeRef, user)
            }.await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    suspend fun getLikesOnComment(postId: String): List<UserType> {
        val snapshot = db.collection(collection).document(postId)
            .collection(subCollectionLikes)
            .limit(4)
            .get()
            .await()

        return snapshot.toObjects(UserType::class.java)
    }

    suspend fun removeLikeOnPost(postId: String, communityId: String) {

        try {
            val ref = db.collection(collection).document(postId)
            val likeRef = ref.collection(subCollectionLikes).document("${postId}_$communityId")

            db.runTransaction { transaction ->
                transaction.update(ref, "likes", FieldValue.increment(-1))
                transaction.delete(likeRef)
            }.await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}