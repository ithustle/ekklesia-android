package com.toquemedia.ekklesia.ui.screens.bible.states

import com.toquemedia.ekklesia.model.NoteEntity
import kotlinx.coroutines.flow.MutableStateFlow

data class VerseUiState(
    val markedVerse: String = "",
    val selectedVerse: String = "",
    val notes: List<NoteEntity> = emptyList(),
    val versicle: Int = -1,
    val chapter: Int = 1,
    val markedVerses: MutableStateFlow<List<String>> = MutableStateFlow(emptyList()),
    var onSelectVerse: (String, Int) -> Unit = { _, _ -> },
    var onMarkVerse: (String) -> Unit = {},
    var onUnMarkVerse: (String) -> Unit = {},
    var onChangeChapter: (Int) -> Unit = {},
    var onShowVerseAction: (Boolean) -> Unit = {},
    var onShowAddNote: (Boolean) -> Unit = {},
    val showVerseActionOption: Boolean = false,
    val showAddNote: Boolean = false,
    val entryNote: String = "",
    val onEntryNoteChange: (String) -> Unit = {},
    val savingNote: Boolean = false,
    val onSavingNote: (Boolean) -> Unit = {}
)