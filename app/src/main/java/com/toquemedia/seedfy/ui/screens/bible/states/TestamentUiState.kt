package com.toquemedia.seedfy.ui.screens.bible.states

import com.toquemedia.seedfy.model.BibleType
import com.toquemedia.seedfy.model.BiblicalResponse
import com.toquemedia.seedfy.model.BookType

data class TestamentUiState(
    var bible: List<BibleType> = emptyList(),
    var books: List<BookType> = emptyList(),
    var book: BookType? = null,
    var chapters: Int? = null,
    var searchResponse: BiblicalResponse? = null
)