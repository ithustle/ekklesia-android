package com.toquemedia.ekklesia.services

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.toquemedia.ekklesia.model.CommentType
import com.toquemedia.ekklesia.model.PostType
import com.toquemedia.ekklesia.model.UserType
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostService @Inject constructor(
    private val db: FirebaseFirestore
) {
    private val collection = "posts"
    private val subCollectionComments = "comments"
    private val subCollectionLikes = "likes"

    suspend fun addPost(post: PostType) {
        db.collection(collection).document(post.verseId).set(post).await()
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

    suspend fun addComment(postId: String, comment: CommentType) {
        db.collection(collection)
            .document(postId)
            .collection(subCollectionComments)
            .document(comment.id)
            .set(comment)
            .await()
    }

    suspend fun getComments(postId: String, limit: Long): MutableList<CommentType> {
        val snapshot = db.collection(collection).document(postId)
            .collection(subCollectionComments)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(limit)
            .get()
            .await()

        return snapshot.toObjects(CommentType::class.java)
    }

    suspend fun addLikeOnPost(postId: String, user: UserType) {
        try {
            val ref = db.collection(collection).document(postId)
            val likeRef = ref.collection(subCollectionLikes).document(user.id)

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

    suspend fun removeLikeOnPost(postId: String, userId: String) {

        try {
            val ref = db.collection(collection).document(postId)
            val likeRef = ref.collection(subCollectionLikes).document(userId)

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