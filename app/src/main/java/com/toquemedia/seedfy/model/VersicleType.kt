package com.toquemedia.seedfy.model

import kotlinx.serialization.Serializable

@Serializable
data class VerseType(
    val bookName: String,
    val chapter: Int,
    val versicle: Int,
    val text: String
)
