package com.toquemedia.seedfy.ui.screens.bible.verses

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val density       = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val wrapWidthDp   = (screenWidthPx * 0.75f).dp
    val textMeasureWidthDp = wrapWidthDp - (8.dp * 2)
    val isMarked = markedVerse.contains(verse)

    var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    Text(
        text = verse,
        fontSize = 16.sp,
        modifier = modifier
            .alpha(0f)
            .width(textMeasureWidthDp),
        onTextLayout = { layoutResult = it },
        softWrap = true,
        maxLines = Int.MAX_VALUE,
        overflow = TextOverflow.Clip
    )

    layoutResult?.let { result ->
        val lines = (0 until result.lineCount).map { i ->
            verse.substring(result.getLineStart(i), result.getLineEnd(i))
        }

        Column(modifier = modifier) {
            lines.forEachIndexed { idx, lineText ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = lineText,
                            fontSize = 16.sp,
                            color = if (isMarked) Color.White else PrincipalColor,
                            textDecoration = if (selectedVerse == verse) TextDecoration.Underline else null,
                            fontWeight = if (selectedVerse == verse) FontWeight.SemiBold else FontWeight.Normal,
                            modifier = Modifier
                                .background(color = if (isMarked) PrincipalColor else Color.Transparent)
                                .padding(1.dp)
                        )
                        if (idx == lines.lastIndex) {
                            Spacer(Modifier.width(4.dp))
                            if (hasNote) Icon(
                                Icons.Default.EditNote,
                                null,
                                Modifier.size(20.dp),
                                tint = PrincipalColor
                            )
                            if (hasWorship) {
                                Spacer(Modifier.width(4.dp))
                                Icon(
                                    Icons.Default.Diversity3,
                                    null,
                                    Modifier.size(20.dp),
                                    tint = PrincipalColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerseTextPrev() {
    Column {
        Row {
            Text("1")
            Box(
                modifier = Modifier.border(width = 1.dp, color = Color.Black)
            ) {
                VerseText(
                    verse = "Observem o mês de abibe e celebrem a Páscoa do Senhor, do seu Deus, pois no mês de abibe, de noite, ele os tirou do Egito.",
                    selectedVerse = "",
                    markedVerse = listOf("Observem o mês de abibe e celebrem a Páscoa do Senhor, do seu Deus, pois no mês de abibe, de noite, ele os tirou do Egito."),
                    hasNote = true,
                    hasWorship = true,
                )
            }
        }
    }
}