package com.toquemedia.ekklesia.ui.screens.community.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.CommentType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.PostType
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.ui.composables.EmptyScreen
import com.toquemedia.ekklesia.ui.composables.ScreenAppLoading
import com.toquemedia.ekklesia.ui.screens.community.feed.story.FeedStories
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    community: CommunityWithMembers,
    loadingPosts: Boolean = false,
    user: UserType? = null,
    posts: List<PostType> = emptyList(),
    likedPosts: List<String> = emptyList(),
    onNavigateToComments: (String) -> Unit = {},
    onLikePost: (PostType) -> Unit = {},
    onRemoveLikePost: (PostType) -> Unit = {},
    onAddStory: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val loading = remember(loadingPosts) { loadingPosts }

        println("loading: $loading")

        if (loading) {
            ScreenAppLoading(
                screenText = stringResource(R.string.loading_community)
            )
        }

        if (posts.isEmpty()) {
            EmptyScreen(
                icon = painterResource(R.drawable.comment),
                emptyText = stringResource(R.string.no_posts),
                emptyDescription = stringResource(R.string.post_description),
                modifier = Modifier
                    .fillMaxSize()
                )
            return
        }

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
                        user = user,
                        onAddStory = onAddStory
                    )
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
            }

            items(items = posts) {
                Box(
                    modifier = modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                ) {
                    FeedPost(
                        showLastComment = it.comments.isNotEmpty(),
                        showLikes = it.likes > 0,
                        liked = likedPosts.contains(it.verseId),
                        post = it,
                        onNavigateToComments = onNavigateToComments,
                        onLikePost = onLikePost,
                        onRemoveLikePost = onRemoveLikePost,
                    )
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedScreenPrev() {
    FeedScreen(
        community = CommunityMock.getAllCommunityWithMembers().first(),
    )
}