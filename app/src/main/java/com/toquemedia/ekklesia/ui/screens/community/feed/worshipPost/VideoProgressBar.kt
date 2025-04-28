package com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    onProgressChange: (Float) -> Unit = {},
    bufferedProgress: Float = 0f,
) {
    Box(modifier = modifier.height(24.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .align(Alignment.Center)
                .background(Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(1.dp))
        )

        if (bufferedProgress > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(bufferedProgress)
                    .height(2.dp)
                    .align(Alignment.CenterStart)
                    .background(Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(1.dp))
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(3.dp)
                .align(Alignment.CenterStart)
                .background(PrincipalColor, RoundedCornerShape(1.dp))
        )

        Slider(
            value = progress,
            onValueChange = onProgressChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            colors = SliderDefaults.colors(
                thumbColor = PrincipalColor,
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent
            ),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(PrincipalColor, CircleShape)
                )
            }
        )
    }
}

@Preview
@Composable
private fun VideoProgressBarPrev() {
    VideoProgressBar(
        progress = 0.4f,

    )
}