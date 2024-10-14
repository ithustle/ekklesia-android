package com.toquemedia.ekklesia.model

data class BookType(
    val bookName: String,
    val numberOfChapters: Int,
    val verses: List<List<String>> = emptyList()
)