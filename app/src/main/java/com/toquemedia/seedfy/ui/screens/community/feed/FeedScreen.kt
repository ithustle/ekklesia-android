package com.toquemedia.seedfy.ui.screens.community.feed

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
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.CommunityWithMembers
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.model.StoryType
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.ui.composables.EmptyScreen
import com.toquemedia.seedfy.ui.composables.ScreenAppLoading
import com.toquemedia.seedfy.ui.screens.community.feed.story.FeedStories
import com.toquemedia.seedfy.utils.mocks.CommunityMock
import com.toquemedia.seedfy.utils.mocks.PostsMock

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    community: CommunityWithMembers,
    user: UserType? = null,
    loadingPosts: Boolean = false,
    posts: List<PostType> = emptyList(),
    stories: List<StoryType> = emptyList(),
    likedPosts: List<String> = emptyList(),
    onUserLikes: (String, Long) -> Unit = {_, _ ->},
    onNavigateToComments: (String) -> Unit = {},
    onLikePost: (PostType) -> Unit = {},
    onRemoveLikePost: (PostType) -> Unit = {},
    onShowStory: (UserType) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val loading = remember(loadingPosts) { loadingPosts }

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
                        hasStory = stories.any { it.user?.email == user?.email },
                        stories = stories.filter { it.user?.email != user?.email },
                        onShowStory = onShowStory
                    )
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
            }

            items(items = posts) { post ->

                val postId = "${post.verseId}_${community.community.id}"
                val isLiked = likedPosts.contains(postId)
                val alreadyCounted = post.firstUsersLiked.any { it.email == user?.email }

                val likesCount = maxOf(0, post.likes + if (isLiked && !alreadyCounted) 1 else 0)

                Box(
                    modifier = modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                ) {
                    post.worship?.let {
                        FeedPostWorship(
                            post = post,
                            comments = post.comments,
                            onNavigateToWorship = onNavigateToComments,
                            onNavigateToComments = onNavigateToComments,
                            onLikePost = onLikePost,
                            onRemoveLikePost = onRemoveLikePost,
                            liked = likedPosts.contains(post.verseId),
                        )
                    } ?: run {
                        FeedPost(
                            showLastComment = post.comments.isNotEmpty(),
                            liked = isLiked, //it.likes > 0,
                            post = post,
                            comments = post.comments,
                            hasStory = stories.any { it.user?.email == user?.email },
                            onNavigateToComments = onNavigateToComments,
                            onLikePost = {
                                onUserLikes(postId, 1)
                                onLikePost(it)
                            },
                            onRemoveLikePost = {
                                onUserLikes(postId, 0)
                                onRemoveLikePost(it)
                            },
                            onNavigateToStory = onShowStory,
                            commentsCount = post.comments.size.toLong(),
                            likesCount = likesCount
                        )
                    }
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
        posts = PostsMock.getPosts()
    )
}