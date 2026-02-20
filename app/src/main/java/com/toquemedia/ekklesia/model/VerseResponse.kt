package com.toquemedia.ekklesia.model

import com.toquemedia.ekklesia.services.StatsVerseOfDay

data class VerseResponse(
    val verseOfDay: Triple<String, Int, Int>,
    val stats: StatsVerseOfDay
)