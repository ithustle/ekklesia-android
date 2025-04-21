package com.toquemedia.ekklesia.ui.screens.bible.verses

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.model.BookType
import com.toquemedia.ekklesia.ui.screens.bible.states.DevocionalUiState
import com.toquemedia.ekklesia.ui.screens.bible.states.VerseUiState
import com.toquemedia.ekklesia.ui.theme.PrincipalColor
import com.toquemedia.ekklesia.utils.mocks.BookMock

@Composable
fun VersesScreen(
    modifier: Modifier = Modifier,
    book: BookType?,
    versesStates: VerseUiState,
    devocionalState: DevocionalUiState,
    scrollState: ScrollState,
    chapterNumber: Int?,
    onSelectedVerse: (verse: String, versicle: Int) -> Unit = { _, _ -> },
    onNextVerse: (Int) -> Unit = {},
    onPreviousVerse: (Int) -> Unit = {},
    onUnMarkVerse: (String, Int) -> Unit = { _, _ -> },
) {

    val markedVerses by versesStates.markedVerses.collectAsState()

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.padding(vertical = 32.dp))
        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = book?.bookName.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrincipalColor
            )
            Text(
                text = chapterNumber.toString(),
                fontSize = 40.sp,
                color = PrincipalColor
            )

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            book?.verses?.get(((chapterNumber ?: 0).minus(1)))?.forEachIndexed { versicle, verse ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = (versicle + 1).toString(),
                        fontSize = 14.sp,
                        color = PrincipalColor,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.padding(horizontal = 4.dp))
                    VerseText(
                        verse = verse,
                        selectedVerse = versesStates.selectedVerse,
                        markedVerse = markedVerses,
                        hasNote = versesStates.notes.find { it.verse == verse } != null,
                        hasDevocional = devocionalState.allDevocional.find { it.verse == verse && !it.draft } != null,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .background(color = if (markedVerses.contains(verse)) PrincipalColor else Color.Transparent)
                            .clickable {
                                if (markedVerses.contains(verse)) {
                                    onUnMarkVerse(verse, versicle + 1)
                                } else {
                                    versesStates.onShowVerseAction(true)
                                    onSelectedVerse(verse, versicle + 1)
                                }
                            }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(weight = 1f))
        Spacer(modifier = Modifier.height(20.dp))

        VersesNavigation(
            modifier = Modifier,
            currentVerse = chapterNumber ?: 0,
            bookName = book?.bookName.toString(),
            onNextVerse = onNextVerse,
            onPreviousVerse = onPreviousVerse
        )
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun VersesScreenPrev() {
    VersesScreen(
        book = BookMock.get(),
        chapterNumber = 1,
        versesStates = VerseUiState(),
        scrollState = rememberScrollState(),
        devocionalState = DevocionalUiState()
    )
}