package com.toquemedia.ekklesia.ui.screens.bible.notes

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import com.toquemedia.ekklesia.ui.theme.backgroundLightColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalNoteScreen(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismissRequest: (SheetState) -> Unit = {},
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

    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = backgroundLightColor,
        onDismissRequest = {
            onDismissRequest(sheetState)
        }
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