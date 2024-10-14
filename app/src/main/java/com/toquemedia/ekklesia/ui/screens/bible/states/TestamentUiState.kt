package com.toquemedia.ekklesia.ui.screens.bible.states

import com.toquemedia.ekklesia.model.BibleType
import com.toquemedia.ekklesia.model.BookType

data class TestamentUiState(
    var bible: List<BibleType> = emptyList(),
    var books: List<BookType> = emptyList(),
    var book: BookType? = null,
    var chapters: Int = 0,
)