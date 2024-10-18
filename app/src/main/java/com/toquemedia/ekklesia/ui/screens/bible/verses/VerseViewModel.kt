package com.toquemedia.ekklesia.ui.screens.bible.verses

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
                onSelectVerse = { verse, versicle ->
                    _uiState.value = _uiState.value.copy(
                        selectedVerse = verse,
                        versicle = versicle
                    )
                },
                onMarkVerse = {
                    _uiState.value = _uiState.value.copy(
                        markedVerse = it,
                    )
                },
                onUnMarkVerse = { verse ->
                    val updatedVerses = _uiState.value.markedVerses.value.filter { it != verse }
                    _uiState.value = _uiState.value.copy(
                        markedVerses = MutableStateFlow(updatedVerses)
                    )
                },
                onShowVerseAction = {
                    _uiState.value = _uiState.value.copy(showVerseActionOption = it)
                },
                markedVerses = verseRepository.markedVerses
            )
        }

        viewModelScope.launch {
            verseRepository.getMarkedVerse()
        }
    }

    suspend fun markVerse(bookName: String?, chapter: String?, versicle: String, verse: String) {
        if (bookName == null || chapter == null) return
        verseRepository.markVerse(bookName, chapter.toInt(), versicle.toInt(), verse)
    }

    suspend fun unMarkVerse(bookName: String?, chapter: String?, versicle: String) {
        if (bookName == null || chapter == null) return
        verseRepository.unMarkVerse(bookName, chapter.toInt(), versicle.toInt())
    }
}