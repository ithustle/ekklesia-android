package com.toquemedia.ekklesia.model

import androidx.compose.runtime.Stable
import java.util.Date
import java.util.UUID

@Stable
data class CommentType(
    val user: UserType? = null,
    val comment: String = "",
    val likes: Long = 0,
    val createdAt: Date = Date(),
    val communityId: String = "",
    val id: String = UUID.randomUUID().toString()
)
