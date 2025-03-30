package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.PostType

interface PostRepository {
    suspend fun addPost(post: PostType)
    suspend fun removePost(verseId: String)
    suspend fun getPosts(): List<PostType>
}