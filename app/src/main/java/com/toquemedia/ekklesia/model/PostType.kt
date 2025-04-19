package com.toquemedia.ekklesia.model

import androidx.compose.runtime.Stable
import com.toquemedia.ekklesia.utils.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Stable
@Serializable
data class PostType(
    val note: NoteEntity? = null,
    val verse: String = "",
    val user: UserType? = null,
    val verseId: String = "",
    @Serializable(with = DateSerializer::class)
    val createdAt: Date = Date(),
    val comments: List<CommentType> = emptyList(),
    val likes: Long = 0,
    val firstUsersLiked: List<UserType> = emptyList(),
    val communityId: List<String> = emptyList()
)