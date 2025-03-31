package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.dao.LikeDao
import com.toquemedia.ekklesia.model.CommentType
import com.toquemedia.ekklesia.model.PostType
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.model.interfaces.PostRepository
import com.toquemedia.ekklesia.services.PostService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
    private val dao: LikeDao
) : PostRepository {

    override suspend fun addPost(post: PostType) = postService.addPost(post)
    override suspend fun removePost(postId: String) = postService.removePost(postId)
    override suspend fun getPosts(): List<PostType> = postService.getAllPosts()

    override suspend fun addComment(
        postId: String,
        comment: CommentType
    ) {
        postService.addComment(postId, comment)
    }

    override suspend fun getComments(postId: String, limit: Long): MutableList<CommentType> {
        return postService.getComments(postId, limit)
    }

    override suspend fun getLikesOnPost(postId: String): List<UserType> {
        return postService.getLikesOnComment(postId)
    }

    override suspend fun getUserLikesOnPost(): Flow<List<String>> {
        return dao.getLikes()
    }

    override suspend fun addLikeOnPost(
        postId: String,
        user: UserType
    ) {
        try {
            dao.saveLikeRegister(postId)
            postService.addLikeOnPost(postId, user)
        } catch (e: Exception) {
            e.printStackTrace()
            dao.removeLikeRegister(postId)
            throw e
        }
    }

    override suspend fun removeLikeOnPost(postId: String, userId: String) {
        try {
            dao.removeLikeRegister(postId)
            postService.removeLikeOnPost(postId, userId)
        } catch (e: Exception) {
            e.printStackTrace()
            dao.saveLikeRegister(postId)
        }
    }
}