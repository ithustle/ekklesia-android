package com.toquemedia.ekklesia.ui.screens.home

import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.VerseType

data class HomeUiState(
    val verseOfDay: VerseType? = null,
    val communities: List<CommunityWithMembers> = emptyList(),
    val loadCommunities: Boolean = true,
    val joiningToCommunity: Boolean = false
)
