package com.toquemedia.ekklesia.ui.screens.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.ui.composables.EkklesiaButton
import com.toquemedia.ekklesia.ui.screens.notes.composables.VerseToAnnotation
import com.toquemedia.ekklesia.ui.theme.TextPlaceholderColor
import com.toquemedia.ekklesia.ui.theme.backgroundLightColor

@Composable
fun NoteScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundLightColor)
            .padding(horizontal = 16.dp, vertical = 30.dp)
    ) {
        VerseToAnnotation(
            bookName = "Provérbios",
            chapter = "1",
            versicle = "13",
            verse = "Provérbios de Salomão: O filho sábio dá alegria ao pai; o filho tolo dá tristeza à mãe."
        )
        Spacer(modifier = Modifier.size(30.dp))

        Text(
            "Adicione a tua anotação",
            fontSize = 15.sp,
            color = TextPlaceholderColor
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            EkklesiaButton(
                label = "Partilhar",
                filled = false,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onClick = { /*TODO*/ },
            )
            EkklesiaButton(
                label = "Salvar",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                onClick = { /*TODO*/ }

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteScreenPrev() {
    NoteScreen()
}