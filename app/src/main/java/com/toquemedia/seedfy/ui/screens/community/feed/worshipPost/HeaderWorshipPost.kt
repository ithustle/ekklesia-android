package com.toquemedia.seedfy.ui.screens.community.feed.worshipPost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.extension.adjustBrightness

@Composable
fun HeaderWorshipPost(
    modifier: Modifier = Modifier,
    worshipColor: Color,
    worshipTitle: String,
    bookName: String,
    chapter: Int,
    versicle: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(worshipColor.adjustBrightness(0.35f), worshipColor)
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = worshipTitle,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Text(
            text = "Devocional - $bookName ${chapter}:$versicle",
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Preview
@Composable
private fun HeaderWorshipPostPrev() {
    HeaderWorshipPost(
        worshipColor = Color.Red,
        worshipTitle = "O Bom Pastor",
        bookName = "João",
        chapter = 10,
        versicle = 3
    )
}