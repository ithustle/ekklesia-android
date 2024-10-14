package com.toquemedia.ekklesia.ui.screens.bible

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.repository.BibleRepository
import com.toquemedia.ekklesia.ui.screens.bible.states.TestamentUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestamentViewModel(
    private val repository: BibleRepository = BibleRepository,
    private val context: Context,
): ViewModel() {

    private val _uiState = MutableStateFlow(TestamentUiState())
    val uiState: StateFlow<TestamentUiState> = _uiState

    init {
        loadBible()
        getBooks()
    }

    fun getChaptersOfTheBook(bookName: String) {
        val numberOfChapters = _uiState.value.books.find { it.bookName == bookName }?.numberOfChapters
        _uiState.value = _uiState.value.copy(
            chapters = numberOfChapters ?: 0
        )
    }

    fun getVersesOfTheChapter(bookName: String) {
        val book = _uiState.value.books.find { it.bookName == bookName }
        _uiState.value = _uiState.value.copy(
            book = book
        )
    }

    private fun getBooks() {
        viewModelScope.launch {
            val books = repository.getBooks(context)
            _uiState.value = _uiState.value.copy(
                books = books
            )
        }
    }

    private fun loadBible() {
        viewModelScope.launch {
            val bible = repository.loadBible(context)
            _uiState.value = _uiState.value.copy(
                bible = bible
            )
        }
    }
}