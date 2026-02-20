package com.toquemedia.ekklesia.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyReading(
    val day: Int,
    val theme: String,
    val verses: List<VerseType>,
    val isCompleted: Boolean = false
)