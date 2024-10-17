package com.toquemedia.ekklesia.ui.screens.bible.verses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.repository.VerseRepositoryImpl
import com.toquemedia.ekklesia.ui.screens.bible.states.VerseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerseViewModel @Inject constructor(
    private val verseRepository: VerseRepositoryImpl,
) : ViewModel() {

    private val _uiState: MutableStateFlow<VerseUiState> = MutableStateFlow(VerseUiState())
    val uiState: StateFlow<VerseUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onSelectVerse = {
                    _uiState.value = _uiState.value.copy(selectedVerse = it)
                },
                onMarkVerse = {
                    _uiState.value = _uiState.value.copy(
                        markedVerse = it,
                    )
                },
                onShowVerseAction = {
                    _uiState.value = _uiState.value.copy(showVerseActionOption = it)
                }
            )
        }

        viewModelScope.launch {
            val markedVerses = verseRepository.getMarkedVerse()
            Log.i("markedVerses", markedVerses.toString())
            _uiState.value = _uiState.value.copy(
                markedVerses = markedVerses
            )
        }
    }

    suspend fun markVerse(bookName: String, chapter: String, verse: String) {
        verseRepository.markVerse(bookName, chapter.toInt(), verse)
    }

    suspend fun unMarkVerse(bookName: String, chapter: String) {
        verseRepository.unMarkVerse(bookName, chapter.toInt())
    }
}