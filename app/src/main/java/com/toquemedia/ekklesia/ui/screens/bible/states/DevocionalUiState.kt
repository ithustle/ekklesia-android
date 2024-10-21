package com.toquemedia.ekklesia.ui.screens.bible.states

import com.toquemedia.ekklesia.model.DevocionalType

data class DevocionalUiState(
    val devocionalTitle: String = "",
    val devocionalContent: String = "",
    val allDevocional: List<DevocionalType> = emptyList(),
    val onDevocionalTitleChange: (String) -> Unit = {},
    val onDevocionalContentChange: (String) -> Unit = {}
)
