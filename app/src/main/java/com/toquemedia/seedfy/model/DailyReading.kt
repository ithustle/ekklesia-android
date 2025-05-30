package com.toquemedia.seedfy.model

data class DailyReading(
    val day: Int,
    val theme: String,
    val verses: List<VerseType>
)