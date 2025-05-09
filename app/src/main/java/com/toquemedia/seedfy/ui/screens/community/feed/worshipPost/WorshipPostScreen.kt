package com.toquemedia.seedfy.ui.screens.community.feed.worshipPost

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.seedfy.extension.formatTime
import com.toquemedia.seedfy.extension.toColor
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.ui.composables.VideoPresence
import com.toquemedia.seedfy.utils.mocks.PostsMock

@Composable
fun WorshipPostScreen(
    post: PostType,
    onNavigateToVideo: (String) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeaderWorshipPost(
            worshipTitle = post.worship?.title.toString(),
            worshipColor = post.worship?.backgroundColor?.toColor() ?: Color.DarkGray,
            bookName = post.worship?.bookName.toString(),
            chapter = post.worship?.chapter ?: 0,
            versicle = post.worship?.versicle ?: 0
        )

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
        ) {
            Scripture(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                bookName = post.worship?.bookName.toString(),
                chapter = post.worship?.chapter ?: 0,
                versicle = post.worship?.versicle ?: 0,
                verse = post.worship?.verse.toString(),
                backgroundColor = post.worship?.backgroundColor?.toColor() ?: Color.DarkGray
            )

            post.worship?.video?.let {
                VideoPresence(
                    icon = Icons.Rounded.PlayCircle,
                    label = "Assista o video",
                    duration = post.worship.durationVideo?.formatTime() ?: "",
                    backgroundColor = post.worship.backgroundColor.toColor(),
                    onWatchVideo = {
                        onNavigateToVideo(it)
                    }
                )
            }

            WorshipReflection(
                modifier = Modifier
                    .padding(16.dp),
                reflection = post.worship?.worship.toString(),
                backgroundColor = post.worship?.backgroundColor?.toColor() ?: Color.DarkGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WorshipPostScreenPrev() {
    WorshipPostScreen(
        post = PostsMock.getPosts()[1]
    )
}