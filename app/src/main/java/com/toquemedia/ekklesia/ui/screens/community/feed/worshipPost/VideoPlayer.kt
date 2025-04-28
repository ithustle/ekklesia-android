package com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.extension.timeAgo
import com.toquemedia.ekklesia.model.PostType
import com.toquemedia.ekklesia.ui.screens.community.feed.PostOwner
import com.toquemedia.ekklesia.utils.mocks.PostsMock

@Composable
fun VideoPlayer(
    post: PostType
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PostOwner(
            modifier = Modifier,
            user = post.user!!,
            timeAgo = post.createdAt.timeAgo(),
            postType = "${post.worship?.bookName} ${post.worship?.verse}:${post.worship?.versicle}"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VideoPlayerPrev() {
    VideoPlayer(
        post = PostsMock.getPosts().first()
    )
}