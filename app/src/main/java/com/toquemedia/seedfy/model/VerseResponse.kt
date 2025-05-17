package com.toquemedia.seedfy.model

import com.toquemedia.seedfy.services.StatsVerseOfDay

data class VerseResponse(
    val verseOfDay: Triple<String, Int, Int>,
    val stats: StatsVerseOfDay
)