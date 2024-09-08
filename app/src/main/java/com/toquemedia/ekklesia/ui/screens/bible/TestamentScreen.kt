package com.toquemedia.ekklesia.ui.screens.bible

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.ui.screens.bible.composables.Book

@Composable
fun TestamentScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(all = 16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier
                .fillMaxSize()
        ) {
            items(24) {
                Book()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TestamentScreenPrev() {
    TestamentScreen()
}