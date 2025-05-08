package com.toquemedia.seedfy.ui.screens.community.feed.worshipPost

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.PlayerSurface
import androidx.media3.ui.compose.SURFACE_TYPE_TEXTURE_VIEW
import androidx.media3.ui.compose.modifiers.resizeWithContentScale
import androidx.media3.ui.compose.state.rememberPresentationState
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.extension.timeAgo
import com.toquemedia.seedfy.model.EkklesiaPlayer
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.ui.screens.community.feed.PostOwner
import com.toquemedia.seedfy.utils.mocks.PostsMock
import kotlinx.coroutines.delay

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayer(
    post: PostType,
    player: EkklesiaPlayer,
    buffering: Boolean = false,
    isPlaying: Boolean = false,
    onPlay: () -> Unit = {},
    onPause: () -> Unit = {},
    onSeekBack: (Long) -> Unit = {},
    onSeekForward: (Long) -> Unit = {},
    onReleasePlayer: () -> Unit = {},
) {

    val presentationState = rememberPresentationState(player.getPlayer())
    var showControls by remember { mutableStateOf(true) }
    var iteration by remember { mutableIntStateOf(0) }

    LaunchedEffect(showControls) {
        if (showControls) {
            while (true) {
                if (iteration == 5) {
                    showControls = false
                    iteration = 0
                } else {
                    iteration += 1
                }

                delay(1000)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            println("VideoPlayer released")
            onReleasePlayer()
        }
    }

    println("VideoPlayer")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .zIndex(1f)
        ) {
            IconButton(onClick = onReleasePlayer) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back_description),
                    tint = Color.White
                )
            }

            PostOwner(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                user = post.user!!,
                timeAgo = post.createdAt.timeAgo(),
                color = Color.White,
                postType = "${post.worship?.bookName} ${post.worship?.chapter}:${post.worship?.versicle}"
            )
        }

        if (LocalInspectionMode.current) {
            Text(
                text = "Video Preview",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        } else  {
            PlayerSurface(
                player = player.getPlayer(),
                modifier = Modifier
                    .resizeWithContentScale(contentScale = ContentScale.FillHeight, sourceSizeDp = presentationState.videoSizeDp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                showControls = true
                            }
                        )
                    },
                surfaceType = SURFACE_TYPE_TEXTURE_VIEW,
            )
        }

        if (showControls) {
            VideoPlayerControl(
                isPlaying = isPlaying,
                buffering = buffering,
                togglePlayPauseButton = {
                    if (isPlaying) {
                        onPause()
                    } else {
                        onPlay()
                    }
                },
                toggleSeekBack = { onSeekBack(-10000) },
                toggleSeekForward = { onSeekForward(10000) },
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(horizontal = 8.dp)
            )
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Preview(showBackground = true)
@Composable
private fun VideoPlayerPrev() {
    VideoPlayer(
        post = PostsMock.getPosts()[1],
        player = EkklesiaPlayer(LocalContext.current)
    )
}