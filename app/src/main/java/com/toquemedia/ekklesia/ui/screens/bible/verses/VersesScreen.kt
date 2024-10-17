package com.toquemedia.ekklesia.ui.screens.bible.verses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.screens.bible.states.TestamentUiState
import com.toquemedia.ekklesia.ui.theme.PrincipalColor
import com.toquemedia.ekklesia.utils.mocks.BookMock
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VersesScreen(
    modifier: Modifier = Modifier,
    states: TestamentUiState,
    chapterNumber: String?
) {

    val book = states.book
    var chapter by remember { mutableIntStateOf(chapterNumber?.toInt() ?: 0) }
    var selectedVerse by remember { mutableStateOf("") }
    var showVerseActionOption by remember { mutableStateOf(false) }
    var markedVerse by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    Column(
        modifier = modifier
            .fillMaxSize()
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
                text = chapter.toString(),
                fontSize = 40.sp,
                color = PrincipalColor
            )

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            book?.verses?.get((chapter.minus(1)))?.forEachIndexed { line, verse ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = (line + 1).toString(),
                        fontSize = 14.sp,
                        color = PrincipalColor,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.padding(horizontal = 4.dp))
                    VerseText(
                        verse = verse,
                        selectedVerse = selectedVerse,
                        markedVerse = markedVerse,
                        modifier = Modifier
                            .padding(bottom = 1.dp)
                            .background(color = if (markedVerse == verse) PrincipalColor else Color.Transparent)
                            .clickable {
                                selectedVerse = verse
                                showVerseActionOption = true
                            }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(weight = 1f))

        VersesNavigation(
            modifier = Modifier
                .padding(top = 20.dp),
            currentVerse = chapter,
            bookName = book?.bookName.toString(),
            onNextVerse = {
                if (chapter < (chapterNumber?.toInt() ?: 0)) {
                    chapter += 1
                    scope.launch {
                        scrollState.scrollTo(0)
                    }
                }
            },
            onPreviousVerse = {
                if (chapter > 1) {
                    chapter -= 1
                    scope.launch {
                        scrollState.scrollTo(0)
                    }
                }
            }
        )
        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        if (showVerseActionOption) {
            VerseActionOption(
                sheetState = sheetState,
                onDismissRequest = {
                    selectedVerse = ""
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showVerseActionOption = false
                        }
                    }
                },
                onFavoriteVerse = {
                    markedVerse = selectedVerse
                    selectedVerse = ""
                    showVerseActionOption = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VersesScreenPrev() {
    VersesScreen(
        states = TestamentUiState(
            book = BookMock.get()
        ),
        chapterNumber = "1"
    )
}