package com.toquemedia.ekklesia.ui.screens.bible.states

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class VerseUiState(
    val markedVerse: String = "",
    val selectedVerse: String = "",
    val versicle: Int = -1,
    val markedVerses: StateFlow<List<String>> = MutableStateFlow(emptyList()),
    var onSelectVerse: (String, Int) -> Unit = { _, _ -> },
    var onMarkVerse: (String) -> Unit = {},
    var onShowVerseAction: (Boolean) -> Unit = {},
    val showVerseActionOption: Boolean = false
)