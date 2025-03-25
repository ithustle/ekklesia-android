package com.toquemedia.ekklesia.ui.screens.bible.devocional

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.model.DevocionalEntity
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

    fun saveDevocional(bookName: String, chapter: Int?, versicle: Int, verse: String, isDraft: Boolean = false) {
        viewModelScope.launch {
            val devocional = DevocionalEntity(
                id = "${bookName}_${chapter}_$versicle",
                bookName = bookName,
                versicle = versicle,
                chapter = chapter ?: 0,
                verse = verse,
                title = _uiState.value.devocionalTitle.text.trimEnd(),
                devocional = _uiState.value.devocionalContent.text,
                draft = isDraft
            )
            repository.saveDevocional(devocional)
        }
    }

    fun addVerseToDevocional(verse: String) {
        _uiState.update { state ->
            val updatedText = state.devocionalContent.text + verse
            val newTextFieldValue = TextFieldValue(
                text = updatedText,
                selection = TextRange(updatedText.length)
            )
            state.copy(devocionalContent = newTextFieldValue)
        }
    }
}