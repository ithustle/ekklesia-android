package com.toquemedia.ekklesia.model

import androidx.compose.runtime.Stable
import java.util.Date

@Stable
data class PostType(
    val note: NoteEntity? = null,
    val verse: String = "",
    val user: UserType? = null,
    val verseId: String = "",
    val createdAt: Date = Date(),
    val comments: List<CommentType> = emptyList(),
    val likes: Long = 0,
    val firstUsersLiked: List<UserType> = emptyList()
)