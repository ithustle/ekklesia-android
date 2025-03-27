package com.toquemedia.ekklesia.ui.screens.home

import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.model.VerseType

data class HomeUiState(
    val verseOfDay: VerseType? = null,
    val communities: List<CommunityType> = emptyList()
)
