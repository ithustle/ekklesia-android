package com.toquemedia.ekklesia.ui.screens.bible.states

import androidx.compose.ui.text.input.TextFieldValue
import com.toquemedia.ekklesia.model.DevocionalEntity

data class DevocionalUiState(
    val devocionalTitle: TextFieldValue = TextFieldValue(),
    var devocionalContent: TextFieldValue = TextFieldValue(),
    val allDevocional: List<DevocionalEntity> = emptyList(),
    val onDevocionalTitleChange: (TextFieldValue) -> Unit = {},
    val onDevocionalContentChange: (TextFieldValue) -> Unit = {}
)
