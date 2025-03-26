package com.toquemedia.ekklesia.ui.screens.community.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.outlined.SyncAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.ui.composables.EkklesiaTopBar
import com.toquemedia.ekklesia.ui.screens.community.feed.story.FeedStories
import com.toquemedia.ekklesia.ui.theme.PrincipalColor
import com.toquemedia.ekklesia.ui.theme.backgroundLightColor
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    community: CommunityType,
    state: FeedCommunityUiState,
    onNavigateToChat: () -> Unit = {},
    onSelectCommunity: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            EkklesiaTopBar(
                title = community.communityName,
                isBackgroundTransparent = true,
                showTitleAvatar = true,
                actions = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Chat,
                        contentDescription = stringResource(R.string.change_community_description),
                        tint = PrincipalColor,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .size(20.dp)
                            .clickable {
                                onNavigateToChat()
                            }
                    )
                    Icon(
                        imageVector = Icons.Outlined.SyncAlt,
                        contentDescription = stringResource(R.string.change_community_description),
                        tint = PrincipalColor,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .size(20.dp)
                            .clickable {
                                onSelectCommunity()
                            }
                    )
                }
            )
        },
        containerColor = backgroundLightColor
    ) { innerPadding ->
        Column {
            Box(
                modifier = modifier
                    .background(color = Color.White)
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {
                FeedStories(
                    modifier = Modifier
                        .padding(16.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = modifier
                    .background(color = Color.White)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                FeedPost(
                    hasNote = true,
                    showComments = true,
                    showLikes = true
                )
            }

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = modifier
                    .background(color = Color.White)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                FeedPost()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedScreenPrev() {
    FeedScreen(
        community = CommunityMock.getAll().first(),
        state = FeedCommunityUiState(),
    )
}