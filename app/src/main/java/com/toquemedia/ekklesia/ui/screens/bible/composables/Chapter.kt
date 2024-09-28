package com.toquemedia.ekklesia.ui.screens.bible.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Chapter(
    modifier: Modifier = Modifier,
    chapterNumber: Int
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(shape = RoundedCornerShape(2.dp))
            .background(color = Color.Red),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(
            text = chapterNumber.toString(),
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun ChapterPrev() {
    Chapter(chapterNumber = 2)
}