package com.toquemedia.ekklesia.ui.screens.community.feed

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.ui.screens.community.chat.ChatInputMessage
import com.toquemedia.ekklesia.utils.mocks.PostsMock

@Composable
fun FeedPostAddComment(
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            ChatInputMessage(
                value = "",
                onChangeValue = {},
            )
        },
        modifier = modifier
            .border(width = 1.dp, color = Color.Red)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = modifier
                .padding(it.calculateTopPadding())
                .padding(16.dp)
        ) {
            item {
                FeedPost(
                    post = PostsMock.getPosts().first(),
                    showLikes = true,
                    showLastComment = false
                )

                Spacer(Modifier.height(20.dp))
            }

            items(4) {
                FeedPostComment()
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedPostAddCommentPrev() {
    FeedPostAddComment()
}