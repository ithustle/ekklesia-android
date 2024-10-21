package com.toquemedia.ekklesia.ui.screens.bible.devocional

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.model.DevocionalType
import com.toquemedia.ekklesia.repository.DevocionalRepositoryImpl
import com.toquemedia.ekklesia.ui.screens.bible.states.DevocionalUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevocionalViewModel @Inject constructor(
    private val repository: DevocionalRepositoryImpl
) : ViewModel() {

    private val _uiState: MutableStateFlow<DevocionalUiState> = MutableStateFlow(DevocionalUiState())
    val uiState: StateFlow<DevocionalUiState> = _uiState.asStateFlow()

    init {

        _uiState.update { currentState ->
            currentState.copy(
                onDevocionalTitleChange = {
                    _uiState.value = _uiState.value.copy(devocionalTitle = it)
                },
                onDevocionalContentChange = {
                    _uiState.value = _uiState.value.copy(devocionalContent = it)
                }
            )
        }

        viewModelScope.launch {
            repository.getAllDevocional().collect {
                _uiState.value = _uiState.value.copy(
                    allDevocional = it
                )
            }
        }
    }

    fun saveDevocional(bookName: String, chapter: Int?, versicle: Int, verse: String) {
        viewModelScope.launch {
            val devocional = DevocionalType(
                id = "${bookName}_${chapter}_$versicle",
                bookName = bookName,
                versicle = versicle,
                chapter = chapter ?: 0,
                verse = verse,
                devocional = _uiState.value.devocionalContent
            )
            repository.saveDevocional(devocional)
        }
    }
}