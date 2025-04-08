package com.toquemedia.ekklesia.model

import kotlinx.serialization.Serializable

@Serializable
data class BookType(
    val bookName: String,
    val numberOfChapters: Int,
    val verses: List<List<String>> = emptyList()
)