package com.toquemedia.ekklesia.ui.screens.community.feed.story

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.ProgressIndicatorDefaults.drawStopIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StoriesProgressIndicator(
    modifier: Modifier = Modifier,
    storiesCount: Int,
    currentStoryIndex: Int,
    currentProgress: Float,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val availableWidth = LocalConfiguration.current.screenWidthDp.dp * 0.9f
        val indicatorWidth = (availableWidth - (4.dp * (storiesCount - 1))) / storiesCount

        for (i in 0 until storiesCount) {
            LinearProgressIndicator(
                progress = {
                    when {
                        i < currentStoryIndex -> 1f
                        i > currentStoryIndex -> 0f
                        else -> currentProgress
                    }
                },
                modifier = Modifier.width(indicatorWidth),
                color = Color.White,
                trackColor = Color.Gray,
                gapSize = 10.dp,
                drawStopIndicator = {
                    drawStopIndicator(
                        drawScope = this,
                        stopSize = 0.dp,
                        color = Color.White,
                        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun StoriesProgressIndicatorPrev() {
    StoriesProgressIndicator(
        storiesCount = 15,
        currentStoryIndex = 2,
        currentProgress = 0.5f
    )
}