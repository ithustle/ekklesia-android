package com.toquemedia.ekklesia.ui.screens.home

import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.VerseType
import com.toquemedia.ekklesia.services.StatsVerseOfDay

data class HomeUiState(
    val communities: List<CommunityWithMembers> = emptyList(),
    val loadCommunities: Boolean = true,
    val joiningToCommunity: Boolean = false,
    val communitiesUserIn: List<CommunityWithMembers> = emptyList(),
    val loadingCommunitiesUserIn: Boolean = true,
    val verseOfDay: VerseType? = null,
    val verseOfDayStats: StatsVerseOfDay = StatsVerseOfDay(),
    val likedVerseOfDay: Boolean = false
)