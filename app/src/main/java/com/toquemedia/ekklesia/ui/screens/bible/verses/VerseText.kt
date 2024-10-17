package com.toquemedia.ekklesia.ui.screens.bible.verses

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.extension.splitTextByLineWidth
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun VerseText(
    modifier: Modifier = Modifier,
    verse: String,
    selectedVerse: String,
    markedVerse: String
) {
    val paragraphs = verse.splitTextByLineWidth(maxCharsPerLine = 40)

    Column {
        paragraphs.forEach {
            Text(
                text = it,
                fontSize = 17.sp,
                color = if (markedVerse == verse) Color.White else PrincipalColor,
                textDecoration = if (selectedVerse == verse) TextDecoration.Underline else TextDecoration.None,
                fontWeight = if (selectedVerse == verse) FontWeight.SemiBold else FontWeight.Normal,
                modifier = modifier
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun VerseTextPrev() {
    VerseText(
        verse = "Observem o mês de abibe e celebrem a Páscoa do Senhor, do seu Deus, pois no mês de abibe, de noite, ele os tirou do Egito.",
        selectedVerse = "",
        markedVerse = ""
    )
}