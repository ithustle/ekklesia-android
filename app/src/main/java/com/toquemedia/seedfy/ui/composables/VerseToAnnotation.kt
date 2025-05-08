package com.toquemedia.seedfy.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.toquemedia.seedfy.extension.splitTextByLineWidth
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun VerseToAnnotation(
    modifier: Modifier = Modifier,
    bookName: String,
    chapter: String,
    versicle: Int,
    verse: String,
    bookNameAsTitle: Boolean = true,
    color: Color = PrincipalColor
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val paragraphs = verse.splitTextByLineWidth(screenWidth = screenWidthPx.toInt(), percentOfScreen = 0.9f)

    Column(
        modifier = modifier
    ) {
        if (bookNameAsTitle) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "$bookName $chapter:$versicle",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = color
                )
            }
        } else {
            Text(
                "$bookName $chapter:$versicle",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = color,
            )
        }

        Spacer(modifier = Modifier.size(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .border(width = 3.dp, color = color, shape = RectangleShape)
                    .width(3.dp)
                    .height(paragraphs.size * 50.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = verse,
                color = color,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerseToAnnotationPrev() {
    VerseToAnnotation(
        bookName = "Provérbios",
        chapter = "13",
        versicle = 1,
        color = PrincipalColor,
        verse = "Provérbios de Salomão: O filho sábio da alegria ao pai; o filho tolo dá tristeza à mãe."
    )
}