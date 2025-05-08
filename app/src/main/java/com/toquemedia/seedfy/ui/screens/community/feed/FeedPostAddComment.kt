package com.toquemedia.seedfy.ui.screens.community.feed

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
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.ui.screens.community.chat.ChatInputMessage
import com.toquemedia.seedfy.utils.mocks.PostsMock

@Composable
fun FeedPostAddComment(
    modifier: Modifier = Modifier,
    onSendComment: () -> Unit = {},
    onChangeTextComment: (String) -> Unit = {},
    onLikePost: (PostType) -> Unit = {},
    selectedPost: PostType? = null,
    textComment: String
) {

    Scaffold(
        bottomBar = {
            ChatInputMessage(
                value = textComment,
                inputFor = ChatInputMessage.COMMENT,
                onChangeValue = onChangeTextComment,
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
            selectedPost?.let { post ->
                item {
                    FeedPost(
                        post = post,
                        comments = post.comments,
                        showLikes = post.likes > 0,
                        onLikePost = onLikePost
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
        selectedPost = PostsMock.getPosts().first(),
        textComment = "",
    )
}