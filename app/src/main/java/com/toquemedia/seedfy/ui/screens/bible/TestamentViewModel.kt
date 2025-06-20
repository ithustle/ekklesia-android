package com.toquemedia.seedfy.ui.screens.bible

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.seedfy.repository.BibleRepositoryImpl
import com.toquemedia.seedfy.ui.screens.bible.states.TestamentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestamentViewModel @Inject constructor(
    private val bibleRepository: BibleRepositoryImpl
): ViewModel() {

    private val _uiState = MutableStateFlow(TestamentUiState())
    val uiState: StateFlow<TestamentUiState> = _uiState.asStateFlow()

    init {
        println("Testamento")
    }

    fun generateResponse(userPrompt: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(searchResponse = null) }
            val response = bibleRepository.talkToSeedfyBible(userPrompt)
            _uiState.update { it.copy(searchResponse = response) }
        }
    }
}