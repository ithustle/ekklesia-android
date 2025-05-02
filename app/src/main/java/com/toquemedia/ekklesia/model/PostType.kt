package com.toquemedia.ekklesia.model

import androidx.compose.runtime.Stable
import com.toquemedia.ekklesia.utils.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Stable
@Serializable
data class PostType(
    val note: NoteEntity? = null,
    val worship: WorshipEntity? = null,
    val verse: String? = null,
    val user: UserType? = null,
    val verseId: String = "",
    @Serializable(with = DateSerializer::class)
    val createdAt: Date = Date(),
    val comments: List<CommentType> = emptyList(),
    val likes: Long = 0,
    val firstUsersLiked: List<UserType> = emptyList(),
    val communityId: List<String> = emptyList()
)