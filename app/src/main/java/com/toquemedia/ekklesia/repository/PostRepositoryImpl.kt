package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.model.PostType
import com.toquemedia.ekklesia.model.interfaces.PostRepository
import com.toquemedia.ekklesia.services.PostService
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService
) : PostRepository {
    override suspend fun addPost(post: PostType) = postService.addPost(post)

    override suspend fun removePost(verseId: String) = postService.removePost(verseId)

    override suspend fun getPosts(): List<PostType> = postService.getAllPosts()
}