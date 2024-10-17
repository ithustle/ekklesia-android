package com.toquemedia.ekklesia.ui.screens.bible.verses

import androidx.lifecycle.ViewModel
import com.toquemedia.ekklesia.repository.VerseRepositoryImpl
import com.toquemedia.ekklesia.ui.screens.bible.states.VerseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class VerseViewModel @Inject constructor(
    private val verseRepository: VerseRepositoryImpl,
) : ViewModel() {

    private val _uiState: MutableStateFlow<VerseUiState> = MutableStateFlow(VerseUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun markVerse(id: String, verse: String) {
        verseRepository.markVerse(id, verse)
    }

    suspend fun unMarkVerse(id: String) {
        verseRepository.unMarkVerse(id)
    }

    suspend fun getVerse(id: String) {
        verseRepository.getMarkedVerses(id)?.let { verse ->
            _uiState.value = _uiState.value.copy(
                markedVerse = verse
            )
        }
    }
}