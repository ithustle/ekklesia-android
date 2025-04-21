package com.toquemedia.ekklesia.ui.screens.home

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.VerseType
import com.toquemedia.ekklesia.services.StatsVerseOfDay
import com.toquemedia.ekklesia.ui.composables.LoadingItem
import com.toquemedia.ekklesia.ui.composables.SectionTitle
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    context: Context,
    verseOfDay: VerseType? = null,
    verseOfDayStats: StatsVerseOfDay = StatsVerseOfDay(),
    likedVerseOfDay: Boolean = false,
    joiningToCommunity: Boolean = false,
    loadCommunities: Boolean = true,
    communitiesUserIn: List<CommunityWithMembers> = emptyList(),
    communities: List<CommunityWithMembers> = emptyList(),
    loadingCommunitiesUserIn: Boolean = true,
    onJoinToCommunity: (String) -> Unit = {},
    onNavigateToCommunity: (CommunityWithMembers) -> Unit = {},
    onShareVerseOfDay: () -> Unit = {},
    onLikeVerseOfDay: (Boolean) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {

        item {
            VerseSection(
                verse = verseOfDay,
                stats = verseOfDayStats,
                isUserLiked = likedVerseOfDay,
                onLike = onLikeVerseOfDay,
                onShare = onShareVerseOfDay
            )
        }

        item { Spacer(Modifier.height(38.dp)) }

        item {
            MyCommunitiesSection(
                context = context,
                communitiesUserIn = communitiesUserIn,
                loadingCommunitiesUserIn = loadingCommunitiesUserIn,
                onNavigateToCommunity = onNavigateToCommunity
            )
        }

        if (loadCommunities) {
            item {
                LoadingItem()
            }
        } else if (communities.isNotEmpty()) {
            item {
                SectionTitle(stringResource(R.string.other_communities))
            }

            items(items = communities, key = { it.community.id }) { community ->
                HomeCommunity(
                    community = community.community,
                    members = community.allMembers,
                    onJoinToCommunity = onJoinToCommunity,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePrev() {
    HomeScreen(
        communities = CommunityMock.getAllCommunityWithMembers(),
        communitiesUserIn = CommunityMock.getAllCommunityWithMembers(),
        verseOfDay = VerseType(
            bookName = "Jó",
            chapter = 42,
            versicle = 2,
            text = "Bem sei que tudo podes, e que nada te impede de ser feito."
        ),
        verseOfDayStats = StatsVerseOfDay(shares = 10, likes = 20),
        context = LocalContext.current,
    )
}