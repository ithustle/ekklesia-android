package com.toquemedia.ekklesia.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.toquemedia.ekklesia.extension.splitTextByLineWidth
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun VerseToAnnotation(
    modifier: Modifier = Modifier,
    bookName: String,
    chapter: String,
    versicle: Int,
    verse: String,
    bookNameAsTitle: Boolean = true
) {

    val paragraphs = verse.splitTextByLineWidth(maxCharsPerLine = 2)

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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrincipalColor,
                )
            }
        } else {
            Text(
                "$bookName $chapter:$versicle",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrincipalColor,
            )
        }

        Spacer(modifier = modifier.size(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = modifier
                    .border(width = 3.dp, color = PrincipalColor, shape = RectangleShape)
                    .width(3.dp)
                    .height(paragraphs.size * 3.dp)
            )
            Spacer(modifier = modifier.size(10.dp))

            Text(
                text = verse,
                color = PrincipalColor,
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
        verse = "Provérbios de Salomão: O filho sábio da alegria ao pai; o filho tolo dá tristeza à mãe."
    )
}