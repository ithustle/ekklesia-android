package com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.ui.compose.PlayerSurface
import androidx.media3.ui.compose.SURFACE_TYPE_SURFACE_VIEW
import com.toquemedia.ekklesia.extension.timeAgo
import com.toquemedia.ekklesia.model.EkklesiaPlayer
import com.toquemedia.ekklesia.model.PostType
import com.toquemedia.ekklesia.ui.screens.community.feed.PostOwner
import com.toquemedia.ekklesia.utils.mocks.PostsMock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayer(
    post: PostType,
    player: EkklesiaPlayer? = null,
    onPlay: () -> Unit = {},
    onPause: () -> Unit = {},
) {

    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {

        if (LocalInspectionMode.current) {
            PostOwner(
                modifier = Modifier
                    .padding(16.dp),
                user = post.user!!,
                timeAgo = post.createdAt.timeAgo(),
                postType = "${post.worship?.bookName} ${post.worship?.chapter}:${post.worship?.versicle}"
            )

            Text(
                text = "Video Preview",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (player != null) {
            PlayerSurface(
                player = player.getPlayer(),
                modifier = Modifier.fillMaxSize()
            )
        }
        VideoPlayerControl(
            sliderPosition = sliderPosition,
            onSliderPosition = { sliderPosition },
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VideoPlayerPrev() {
    VideoPlayer(
        post = PostsMock.getPosts()[1],
    )
}