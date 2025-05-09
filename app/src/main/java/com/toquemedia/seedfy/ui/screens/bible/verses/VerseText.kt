package com.toquemedia.seedfy.ui.screens.bible.verses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.extension.splitTextByLineWidth
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun VerseText(
    modifier: Modifier = Modifier,
    verse: String,
    selectedVerse: String,
    markedVerse: List<String>,
    hasNote: Boolean,
    hasWorship: Boolean
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }

    val paragraphs = verse.splitTextByLineWidth(screenWidth = screenWidthPx.toInt(), percentOfScreen = 0.60f)

    Column {
        paragraphs.forEachIndexed { index, paragraph ->

            if (index == paragraphs.size - 1 && (hasNote || hasWorship)) {
                Row {
                    Text(
                        text = paragraph,
                        fontSize = 17.sp,
                        color = if (markedVerse.contains(verse)) Color.White else PrincipalColor,
                        textDecoration = if (selectedVerse == verse) TextDecoration.Underline else TextDecoration.None,
                        fontWeight = if (selectedVerse == verse) FontWeight.SemiBold else FontWeight.Normal,
                        modifier = modifier
                    )

                    if (hasNote) {
                        Spacer(modifier = Modifier.padding(start = 4.dp))
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = null,
                            tint = PrincipalColor,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }

                    if (hasWorship) {
                        Spacer(modifier = Modifier.padding(start = 4.dp))
                        Icon(
                            imageVector = Icons.Default.Diversity3,
                            contentDescription = null,
                            tint = PrincipalColor,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }
            } else {
                Text(
                    text = paragraph,
                    fontSize = 17.sp,
                    color = if (markedVerse.contains(verse)) Color.White else PrincipalColor,
                    textDecoration = if (selectedVerse == verse) TextDecoration.Underline else TextDecoration.None,
                    fontWeight = if (selectedVerse == verse) FontWeight.SemiBold else FontWeight.Normal,
                    modifier = modifier
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerseTextPrev() {
    VerseText(
        verse = "Observem o mês de abibe e celebrem a Páscoa do Senhor, do seu Deus, pois no mês de abibe, de noite, ele os tirou do Egito.",
        selectedVerse = "",
        markedVerse = listOf("Observem o mês de abibe e celebrem a Páscoa do Senhor, do seu Deus, pois no mês de abibe, de noite, ele os tirou do Egito"),
        hasNote = true,
        hasWorship = true,
    )
}