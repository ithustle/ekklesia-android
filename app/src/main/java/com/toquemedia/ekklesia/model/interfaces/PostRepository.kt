package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.CommentType
import com.toquemedia.ekklesia.model.PostType
import com.toquemedia.ekklesia.model.UserType
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun addPost(post: PostType)
    suspend fun removePost(postId: String)
    suspend fun getPosts(communityId: String): List<PostType>
    suspend fun addComment(postId: String, comment: CommentType, communityId: String)
    suspend fun getComments(postId: String, communityId: String, limit: Long = Long.MAX_VALUE): MutableList<CommentType>
    suspend fun getLikesOnPost(postId: String): List<UserType>
    suspend fun getUserLikesOnPost(): Flow<List<String>>
    suspend fun addLikeOnPost(postId: String, communityId: String, user: UserType)
    suspend fun removeLikeOnPost(postId: String, communityId: String)
}