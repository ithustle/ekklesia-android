package com.toquemedia.ekklesia.ui.screens.notes.composables

import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
    versicle: String,
    verse: String
) {

    val paragraphs = verse.splitTextByLineWidth(maxCharsPerLine = 2)

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            "$bookName $chapter:$versicle",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = PrincipalColor,
        )
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
                text = verse
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
        versicle = "1",
        verse = "Provérbios de Salomão: O filho sábio da alegria ao pai; o filho tolo dá tristeza à mãe."
    )
}