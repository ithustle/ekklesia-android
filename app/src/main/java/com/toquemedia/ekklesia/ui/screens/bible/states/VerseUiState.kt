package com.toquemedia.ekklesia.ui.screens.bible.states

data class VerseUiState(
    val markedVerse: String = "",
    val selectedVerse: String = "",
    val markedVerses: MutableList<String> = mutableListOf(),
    var onSelectVerse: (String) -> Unit = {},
    var onMarkVerse: (String) -> Unit = {},
    var onShowVerseAction: (Boolean) -> Unit = {},
    val showVerseActionOption: Boolean = false
)