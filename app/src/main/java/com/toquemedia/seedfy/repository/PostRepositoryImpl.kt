package com.toquemedia.seedfy.repository

import com.toquemedia.seedfy.dao.LikeDao
import com.toquemedia.seedfy.model.CommentType
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.model.interfaces.PostRepository
import com.toquemedia.seedfy.services.CommunityService
import com.toquemedia.seedfy.services.PostService
import com.toquemedia.seedfy.services.UserService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
    private val auth: UserService,
    private val dao: LikeDao,
    private val communityService: CommunityService
) : PostRepository {

    override suspend fun addPost(post: PostType) {
        val communitiesActiveIds = communityService.getCommunitiesActiveIds(post.communityId)
        post.copy(communityId = communitiesActiveIds)
        postService.addPost(post)
    }
    override suspend fun removePost(postId: String) = postService.removePost(postId)
    override suspend fun getPosts(communityId: String): List<PostType> = postService.getAllPosts(communityId)

    override suspend fun addComment(
        postId: String,
        comment: CommentType,
        communityId: String
    ) {
        postService.addComment(postId, communityId, comment)
    }

    override suspend fun getComments(postId: String,  communityId: String, limit: Long): MutableList<CommentType> {
        return postService.getComments(postId, communityId, limit)
    }

    override suspend fun getLikesOnPost(postId: String): List<UserType> {
        return postService.getLikesOnComment(postId)
    }

    override suspend fun getUserLikesOnPost(): Flow<List<String>> {
        return dao.getLikes()
    }

    override suspend fun addLikeOnPost(
        postId: String,
        communityId: String,
        user: UserType
    ) {
        try {
            dao.saveLikeRegister(postId, communityId)
            auth.saveLikes("${postId}_$communityId")
            postService.addLikeOnPost(postId, communityId, user)
        } catch (e: Exception) {
            e.printStackTrace()
            dao.removeLikeRegister(postId, communityId)
            throw e
        }
    }

    override suspend fun removeLikeOnPost(postId: String, communityId: String) {
        try {
            dao.removeLikeRegister(postId, communityId)
            postService.removeLikeOnPost(postId, communityId)
        } catch (e: Exception) {
            e.printStackTrace()
            dao.saveLikeRegister(postId, communityId)
        }
    }
}