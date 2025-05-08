package com.toquemedia.seedfy.model

import androidx.compose.runtime.Stable
import com.toquemedia.seedfy.utils.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID

@Stable
@Serializable
data class CommentType(
    val user: UserType? = null,
    val comment: String = "",
    val likes: Long = 0,
    @Serializable(with = DateSerializer::class)
    val createdAt: Date = Date(),
    val communityId: String = "",
    val postId: String = "",
    val id: String = UUID.randomUUID().toString()
)
