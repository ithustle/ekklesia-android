package com.toquemedia.ekklesia.ui.screens.bible.verses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun VersesNavigation(
    modifier: Modifier = Modifier,
    currentVerse: Int = 0,
    maxVerses: Int = 0,
    bookName: String,
    onNextVerse: (Int) -> Unit = {},
    onPreviousVerse: (Int) -> Unit = {},
) {

    println("MAX: $maxVerses")
    println("Verse: $currentVerse")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {

        if (currentVerse == 1) {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(100.dp))
                    .width(48.dp)
                    .height(48.dp)
            )
        } else {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(color = PrincipalColor)
                    .width(48.dp)
                    .height(48.dp)
                    .clickable {
                        onPreviousVerse(currentVerse)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        Text(
            text = "$bookName $currentVerse",
            fontSize = 20.sp,
            color = PrincipalColor
        )

        if (currentVerse >= maxVerses) {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(100.dp))
                    .width(48.dp)
                    .height(48.dp),
            )
        } else {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(color = PrincipalColor)
                    .width(48.dp)
                    .height(48.dp)
                    .clickable {
                        onNextVerse(currentVerse)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun VersesNavigationPreview() {
    VersesNavigation(
        currentVerse = 1,
        bookName = "Gênesis",
        maxVerses = 1
    )
}