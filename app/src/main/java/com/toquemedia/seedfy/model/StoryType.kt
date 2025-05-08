package com.toquemedia.seedfy.model

import com.toquemedia.seedfy.extension.addDateForTomorrow
import java.util.Date

data class StoryType(
    val createdAt: Date = Date(),
    val expirationDate: Date = Date().addDateForTomorrow(),
    val verse: String = "",
    val user: UserType? = null,
    val bookNameWithVersicle: String = "",
    val backgroundColor: Int = 0,
    val verseColor: Int = 0,
    val communityId: List<String> = emptyList(),
)
