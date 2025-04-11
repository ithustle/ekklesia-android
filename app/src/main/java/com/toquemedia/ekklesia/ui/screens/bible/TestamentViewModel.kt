package com.toquemedia.ekklesia.ui.screens.bible

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.repository.BibleRepositoryImpl
import com.toquemedia.ekklesia.ui.screens.bible.states.TestamentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestamentViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(TestamentUiState())
    val uiState: StateFlow<TestamentUiState> = _uiState.asStateFlow()

    fun getChaptersOfTheBook(bookName: String) {
        val numberOfChapters = _uiState.value.books.find { it.bookName == bookName }?.numberOfChapters
        _uiState.value = _uiState.value.copy(
            chapters = numberOfChapters
        )
    }

    /*private fun getBooks() {
        viewModelScope.launch {
            val books = repository.getBooks()
            _uiState.value = _uiState.value.copy(
                books = books
            )
        }
    }

    private fun loadBible() {
        viewModelScope.launch {
            val bible = repository.loadBible()
            _uiState.value = _uiState.value.copy(
                bible = bible
            )
        }
    } */
}