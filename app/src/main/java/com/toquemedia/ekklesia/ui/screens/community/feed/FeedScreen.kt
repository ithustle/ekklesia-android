package com.toquemedia.ekklesia.ui.screens.community.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.extension.toCommunity
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.ui.screens.community.feed.story.FeedStories
import com.toquemedia.ekklesia.utils.mocks.CommunityMock
import com.toquemedia.ekklesia.utils.mocks.PostsMock

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    community: CommunityType,
    members: List<CommunityMemberType> = emptyList(),
    user: UserType? = null,
    state: FeedCommunityUiState,
) {
    LazyColumn {
        item {
            Box(
                modifier = modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
            ) {
                FeedStories(
                    modifier = Modifier
                        .padding(16.dp),
                    user = user
                )
            }
        }

        item {
            Spacer(Modifier.height(8.dp))
        }

        items(items = state.posts) {
            Box(
                modifier = modifier
                    .background(color = Color.White)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                FeedPost(
                    showComments = it.comments.isNotEmpty(),
                    showLikes = it.likes.isNotEmpty(),
                    post = it
                )
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedScreenPrev() {
    FeedScreen(
        community = CommunityMock.getAll().first().toCommunity(LocalContext.current),
        state = FeedCommunityUiState(posts = PostsMock.getPosts()),
    )
}