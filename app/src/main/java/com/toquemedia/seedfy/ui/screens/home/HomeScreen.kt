package com.toquemedia.seedfy.ui.screens.home

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.CommunityWithMembers
import com.toquemedia.seedfy.model.VerseType
import com.toquemedia.seedfy.services.StatsVerseOfDay
import com.toquemedia.seedfy.ui.composables.LoadingItem
import com.toquemedia.seedfy.ui.composables.SectionTitle
import com.toquemedia.seedfy.utils.mocks.CommunityMock

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

    var joiningCommunityId by remember { mutableStateOf("") }

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

        item { Spacer(Modifier.height(20.dp)) }

        item {
            MyCommunitiesSection(
                context = context,
                communitiesUserIn = communitiesUserIn,
                loadingCommunitiesUserIn = loadingCommunitiesUserIn,
                onNavigateToCommunity = onNavigateToCommunity
            )
        }

        item { Spacer(Modifier.height(20.dp)) }

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
                    joiningToCommunity = if (joiningCommunityId == community.community.id) joiningToCommunity else false,
                    onJoinToCommunity = {
                        joiningCommunityId = it
                        onJoinToCommunity(it)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
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