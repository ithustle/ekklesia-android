package com.toquemedia.ekklesia.ui.screens.bible.chapter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.composables.Chapter
import com.toquemedia.ekklesia.ui.screens.bible.states.TestamentUiState

@Composable
fun ChapterScreen(
    modifier: Modifier = Modifier,
    states: TestamentUiState,
    onNavigateToVerses: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {

        Text(
            "Capítulos",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(48.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(states.chapters) { index ->
                Chapter(
                    chapterNumber = index + 1,
                    onNavigateToVerses = onNavigateToVerses
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChapterScreenPrev() {
    ChapterScreen(states = TestamentUiState())
}