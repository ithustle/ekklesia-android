package com.toquemedia.seedfy.ui.screens.home

import com.toquemedia.seedfy.model.VerseType
import com.toquemedia.seedfy.services.StatsVerseOfDay

data class HomeUiState(
    val verseOfDay: VerseType? = null,
    val verseOfDayStats: StatsVerseOfDay = StatsVerseOfDay(),
    val likedVerseOfDay: Boolean = false,
    val errorConnection: String? = null
)