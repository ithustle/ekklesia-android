package com.toquemedia.ekklesia.ui.screens.bible.devocional

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.composables.EkklesiaButton
import com.toquemedia.ekklesia.ui.composables.EkklesiaTextField
import com.toquemedia.ekklesia.ui.composables.VerseToAnnotation
import com.toquemedia.ekklesia.ui.screens.bible.states.DevocionalUiState
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun CreateDevocionalScreen(
    bookName: String,
    versicle: Int,
    chapter: String,
    verse: String,
    state: DevocionalUiState,
    onSaveDevocional: () -> Unit = {},
    onSaveAsDraftDevocional: () -> Unit = {},
    onAddVerseToDevocional: (String) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 30.dp)
            .fillMaxSize()
    ) {
        VerseToAnnotation(
            bookName = bookName,
            versicle = versicle,
            chapter = chapter,
            verse = verse,
            bookNameAsTitle = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        EkklesiaTextField(
            value = state.devocionalTitle.text,
            placeholder = stringResource(R.string.devocional_title),
            height = 41.dp,
            singleLine = true,
            onChangeValue = state.onDevocionalTitleChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        EkklesiaTextField(
            value = state.devocionalContent.text,
            placeholder = stringResource(R.string.let_holy_spirit_come),
            height = 250.dp,
            onChangeValue = {
                state.onDevocionalContentChange(it)
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .clickable {
                    onAddVerseToDevocional(verse)
                }
        ) {
            Icon(
                imageVector = Icons.Rounded.AddBox,
                contentDescription = null,
                tint = PrincipalColor
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = stringResource(R.string.tap_to_add_verse),
                color = PrincipalColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        EkklesiaButton(
            label = stringResource(R.string.done),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            onSaveDevocional()
        }

        EkklesiaButton(
            label = stringResource(R.string.salvar_as_draft),
            filled = false,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            onSaveAsDraftDevocional()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateDevocionalScreenPrev() {
    CreateDevocionalScreen(
        bookName = "Provérbios",
        versicle = 1,
        chapter = "12",
        verse = "Os tesouros de origem desonesta não servem para nada, mas a retidão livra da morte.",
        state = DevocionalUiState()
    )
}