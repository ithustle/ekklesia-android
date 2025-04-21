package com.toquemedia.ekklesia.ui.screens.home

import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.VerseType
import com.toquemedia.ekklesia.services.StatsVerseOfDay

data class HomeUiState(
    val verseOfDay: VerseType? = null,
    val verseOfDayStats: StatsVerseOfDay = StatsVerseOfDay(),
    val likedVerseOfDay: Boolean = false
)