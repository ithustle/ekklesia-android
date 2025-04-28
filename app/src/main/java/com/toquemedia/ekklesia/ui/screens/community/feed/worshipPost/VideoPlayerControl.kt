package com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun VideoPlayerControl(
    modifier: Modifier = Modifier,
    sliderPosition: Float = 0f,
    onSliderPosition: (Float) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = stringResource(R.string.play_description_icon),
                modifier = Modifier
                    .size(100.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        VideoProgressBar(
            progress = sliderPosition,
            onProgressChange = onSliderPosition,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8f)
        )

        Spacer(Modifier.weight(1f))
        Text(
            text = "1:20",
            color = PrincipalColor
        )
        Spacer(Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun VideoPlayerControlPrev() {
    VideoPlayerControl()
}