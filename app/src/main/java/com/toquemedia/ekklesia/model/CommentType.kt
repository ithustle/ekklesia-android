package com.toquemedia.ekklesia.model

import java.util.Date

data class CommentType(
    val user: UserType,
    val comment: String,
    val createdAt: Date = Date(),
    val id: String
)
