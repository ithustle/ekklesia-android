package com.toquemedia.ekklesia.ui.screens.bible

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.screens.bible.book.BookListScreen
import com.toquemedia.ekklesia.ui.screens.bible.states.TestamentUiState
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestamentScreen(
    modifier: Modifier = Modifier,
    states: TestamentUiState,
    onNavigateToChapter: (bookName: String) -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Antigo Testamento", "Novo Testamento")

    Column {
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = modifier
                .padding(top = 8.dp)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color(PrincipalColor.value) else Color.Black,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                )
            }
        }

        when(selectedTabIndex) {
            0 -> BookListScreen(
                modifier = modifier.padding(horizontal = 16.dp),
                books = states.books.slice(0..38),
                onNavigateToBook = {
                    onNavigateToChapter(it.bookName)
                }
            )
            1 -> BookListScreen(
                modifier = modifier.padding(horizontal = 16.dp),
                books = states.books.slice(39..65),
                onNavigateToBook = {
                    onNavigateToChapter(it.bookName)
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TestamentScreenPrev() {
    TestamentScreen(states = TestamentUiState())
}