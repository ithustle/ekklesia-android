package com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Scripture(
    modifier: Modifier = Modifier,
    verse: String,
    bookName: String,
    chapter: Int,
    versicle: Int,
    backgroundColor: Color
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(148.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "\"$verse\"",
                fontSize = 18.sp,
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic
            )

            Spacer(Modifier.weight(1f))

            Row {
                Spacer(Modifier.weight(1f))
                Text(
                    text = "$bookName $chapter:$versicle",
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScripturePrev() {
    Scripture(
        verse = "And God said, Let there be light: and there was light.",
        bookName = "Genesis",
        chapter = 1,
        versicle = 3,
        backgroundColor = Color.Red
    )
}