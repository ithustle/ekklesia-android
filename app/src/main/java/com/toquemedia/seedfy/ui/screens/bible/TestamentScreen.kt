package com.toquemedia.seedfy.ui.screens.bible

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.BookType
import com.toquemedia.seedfy.ui.screens.bible.book.BookListScreen
import com.toquemedia.seedfy.ui.theme.PrincipalColor
import com.toquemedia.seedfy.utils.mocks.BookMock

@Composable
fun TestamentScreen(
    modifier: Modifier = Modifier,
    books: List<BookType>,
    onNavigateToChapter: (String) -> Unit = {}
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabTitles = listOf(stringResource(R.string.old_testament), stringResource(R.string.new_testament))

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = modifier
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
                books = books.slice(0..38),
                onNavigateToBook = {
                    onNavigateToChapter(it.bookName)
                }
            )
            1 -> BookListScreen(
                modifier = modifier.padding(horizontal = 16.dp),
                books = books.slice(39..65),
                onNavigateToBook = {
                    onNavigateToChapter(it.bookName)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TestamentScreenPrev() {
    TestamentScreen(
        books = BookMock.getAll()
    )
}