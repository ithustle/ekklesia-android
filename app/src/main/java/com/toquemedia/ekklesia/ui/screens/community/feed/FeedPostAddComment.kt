package com.toquemedia.ekklesia.ui.screens.community.feed

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.ui.screens.community.chat.ChatInputMessage
import com.toquemedia.ekklesia.utils.mocks.PostsMock

@Composable
fun FeedPostAddComment(
    modifier: Modifier = Modifier,
    state: FeedCommunityUiState,
    onSendComment: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            ChatInputMessage(
                value = state.textComment,
                inputFor = ChatInputMessage.COMMENT,
                onChangeValue = state.onChangeTextComment,
                onSendComment = onSendComment
            )
        },
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = modifier
                .padding(it)

        ) {
            state.selectedPost?.let { post ->
                item {
                    FeedPost(
                        post = post,
                        showLikes = post.likes > 0
                    )

                    Spacer(Modifier.height(20.dp))
                }

                items(items = post.comments) {
                    FeedPostComment(commentary = it)
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedPostAddCommentPrev() {
    FeedPostAddComment(
        state = FeedCommunityUiState(selectedPost = PostsMock.getPosts().first())
    )
}