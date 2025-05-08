package com.toquemedia.seedfy.ui.screens.community.feed.worshipPost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Forward10
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Replay10
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.ui.composables.EkklesiaProgress

@Composable
fun VideoPlayerControl(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    buffering: Boolean = false,
    togglePlayPauseButton: () -> Unit = {},
    toggleSeekBack: () -> Unit = {},
    toggleSeekForward: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        IconButton(onClick = toggleSeekBack) {
            Icon(
                imageVector = Icons.Rounded.Replay10,
                contentDescription = stringResource(R.string.play_description_icon),
                tint = Color.White,
                modifier = Modifier
                    .size(100.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        if (buffering) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 4.dp)
            ) {
                EkklesiaProgress(
                    color = Color.White,
                )
            }
        } else {
            IconButton(onClick = togglePlayPauseButton) {
                Icon(
                    imageVector = if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                    contentDescription = stringResource(R.string.play_description_icon),
                    tint = Color.White,
                    modifier = Modifier
                        .size(100.dp)
                )
            }
        }

        Spacer(Modifier.weight(1f))
        IconButton(onClick = toggleSeekForward) {
            Icon(
                imageVector = Icons.Rounded.Forward10,
                contentDescription = stringResource(R.string.play_description_icon),
                tint = Color.White,
                modifier = Modifier
                    .size(100.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun VideoPlayerControlPrev() {
    VideoPlayerControl()
}