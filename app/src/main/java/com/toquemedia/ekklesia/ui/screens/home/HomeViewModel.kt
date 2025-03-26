package com.toquemedia.ekklesia.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.repository.VerseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val verseRepository: VerseRepositoryImpl,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getVerseOfDay()
    }

    private fun getVerseOfDay() {
        viewModelScope.launch {
            verseRepository.getVerseOfDay()
            _uiState.value = _uiState.value.copy(verseOfDay = verseRepository.verseOfDay.value)
        }
    }
}