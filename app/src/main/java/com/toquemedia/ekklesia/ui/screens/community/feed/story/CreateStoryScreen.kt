package com.toquemedia.ekklesia.ui.screens.community.feed.story

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
fun CreateStoryScreen(

) {
    val index = 0
    val (bgColor, textColor) = verseBackgroundColors[index]

    var selectedBgColor by remember { mutableStateOf<Pair<Color, Color>>(Pair(bgColor, textColor)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = selectedBgColor.first)
            .padding(vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.weight(1f))

        Text(
            text = "Olá",
            color = selectedBgColor.second,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier
                .horizontalScroll(state = rememberScrollState())
        ) {
            List(verseBackgroundColors.size) {
                Spacer(Modifier.width(12.dp))
                Box(
                    Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(color = verseBackgroundColors[it].first)
                        .border(width = 2.dp, color = if (selectedBgColor.first == verseBackgroundColors[it].first) Color.Black else Color.Transparent, shape = CircleShape)
                        .clickable {
                            selectedBgColor = verseBackgroundColors[it]
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedBgColor.first == verseBackgroundColors[it].first) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateStoryScreenPrev() {
    CreateStoryScreen()
}