package com.toquemedia.ekklesia.ui.screens.bible.book

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.model.BookType
import com.toquemedia.ekklesia.ui.composables.Book
import com.toquemedia.ekklesia.utils.mocks.BookMock

@Composable
fun BookListScreen(
    modifier: Modifier = Modifier,
    books: List<BookType> = emptyList(),
    onNavigateToBook: (BookType) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        items(books.size) { index ->
            Book(
                book = books[index],
                onNavigateToBook = onNavigateToBook
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookListScreenPrev() {
    BookListScreen(books = BookMock.getAll())
}