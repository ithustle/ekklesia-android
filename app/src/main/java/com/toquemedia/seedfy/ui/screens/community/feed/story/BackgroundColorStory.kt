package com.toquemedia.seedfy.ui.screens.community.feed.story

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val verseBackgroundColors = listOf(
    Pair(Color(0xFFE6F0FA), Color.Black),
    Pair(Color(0xFFF5E8C7), Color.Black),
    Pair(Color(0xFFE8D7F1), Color.Black),
    Pair(Color(0xFFD8E2DC), Color.Black),
    Pair(Color(0xFFF4C7C3), Color.Black),
    Pair(Color(0xFFECECEC), Color.Black),
    Pair(Color(0xFF4A6FA5), Color.White),
    Pair(Color(0xFFD4A5D5), Color.Black),
    Pair(Color(0xFFFDF6E3), Color.Black),
    Pair(Color(0xFFB2D8D8), Color.Black)
)

@Composable
fun BackgroundColorStory(
    modifier: Modifier = Modifier,
    onSelectedBgColor: (Pair<Color, Color>) -> Unit = { _ -> }
) {

    var selectedBgColor by remember { mutableStateOf<Pair<Color, Color>>(verseBackgroundColors.first()) }

    Row(
        modifier = modifier
            .horizontalScroll(state = rememberScrollState())
    ) {
        List(verseBackgroundColors.size) {
            Spacer(Modifier.width(16.dp))
            Box(
                Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(color = verseBackgroundColors[it].first)
                    .border(
                        width = 2.dp,
                        color = if (selectedBgColor.first == verseBackgroundColors[it].first) Color.Black else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable {
                        selectedBgColor = verseBackgroundColors[it]
                        onSelectedBgColor(verseBackgroundColors[it])
                    },
                contentAlignment = Alignment.Center
            ) {
                if (selectedBgColor.first == verseBackgroundColors[it].first) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BackgroundColorStoryPrev() {
    BackgroundColorStory()
}