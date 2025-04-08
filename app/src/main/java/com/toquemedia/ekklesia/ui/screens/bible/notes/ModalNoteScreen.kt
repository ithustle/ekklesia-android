package com.toquemedia.ekklesia.ui.screens.bible.notes

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import com.toquemedia.ekklesia.ui.composables.EkklesiaModalSheet

@Composable
fun ModalNoteScreen(
    sheetState: ModalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
    //onDismissRequest: (SheetState) -> Unit = {},
    bookName: String,
    chapter: String,
    versicle: Int,
    verse: String,
    entryNote: String,
    onEntryNoteChange: (String) -> Unit = {},
    onSaveNote: () -> Unit = {},
    onSaveAndShareNote: () -> Unit = {},
    savingNote: Boolean
) {
    EkklesiaModalSheet(
        sheetState = sheetState,
        sheetContent = {},
    ) {
        NoteScreen(
            entryNote = entryNote,
            bookName = bookName,
            chapter = chapter,
            savingNote = savingNote,
            versicle = versicle,
            verse = verse,
            onEntryNoteChange = onEntryNoteChange,
            onSaveNote = onSaveNote,
            onSaveAndShareNote = onSaveAndShareNote
        )
    }
}