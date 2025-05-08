package com.toquemedia.seedfy.ui.screens.bible.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.ui.composables.EkklesiaButton
import com.toquemedia.seedfy.ui.composables.VerseToAnnotation
import com.toquemedia.seedfy.ui.theme.PrincipalColor
import com.toquemedia.seedfy.ui.theme.TextPlaceholderColor
import com.toquemedia.seedfy.ui.theme.BackgroundLightColor

@Composable
fun NoteScreen(
    entryNote: String,
    bookName: String,
    chapter: String,
    versicle: Int,
    verse: String,
    onEntryNoteChange: (String) -> Unit = { },
    onSaveNote: () -> Unit = {},
    onSaveAndShareNote: () -> Unit = {},
    savingNote: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundLightColor)
            .padding(horizontal = 16.dp, vertical = 30.dp)
    ) {
        VerseToAnnotation(
            bookName = bookName,
            chapter = chapter,
            versicle = versicle,
            verse = verse,
            color = PrincipalColor
        )
        Spacer(modifier = Modifier.size(30.dp))

        Box {
            BasicTextField(
                value = entryNote,
                onValueChange = onEntryNoteChange,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = Int.MAX_VALUE
            )

            if (entryNote.isEmpty()) {
                Text(
                    "Adicione a tua anotação",
                    fontSize = 15.sp,
                    color = TextPlaceholderColor,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if (savingNote) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(48.dp),
                    color = PrincipalColor,
                    backgroundColor = MaterialTheme.colorScheme.surface,
                )
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                EkklesiaButton(
                    label = "Salvar e Partilhar",
                    filled = false,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    onClick = onSaveAndShareNote,
                )
                EkklesiaButton(
                    label = "Salvar",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    onClick = onSaveNote

                )
            }
        }
    }
}

@Preview
@Composable
private fun NoteScreenPrev() {
    NoteScreen(
        entryNote = "",
        bookName = "Gênesis",
        chapter = "1",
        versicle = 1,
        verse = "Provérbios de Salomão: O filho sábio da alegria ao pai; o filho tolo dá tristeza à mãe.",
        savingNote = false
    )
}