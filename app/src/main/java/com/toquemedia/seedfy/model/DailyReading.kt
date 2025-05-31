package com.toquemedia.seedfy.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyReading(
    val day: Int,
    val theme: String,
    val verses: List<VerseType>
)