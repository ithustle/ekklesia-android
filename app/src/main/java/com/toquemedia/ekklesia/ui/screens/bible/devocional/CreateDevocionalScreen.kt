package com.toquemedia.ekklesia.ui.screens.bible.devocional

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    onCreateVideoForDevocional: () -> Unit = {},
    onAddVerseToDevocional: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 30.dp)
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
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
            onChangeValue = state.onDevocionalTitleChange,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(20.dp))

        EkklesiaTextField(
            value = state.devocionalContent.text,
            placeholder = stringResource(R.string.let_holy_spirit_come),
            height = 250.dp,
            onChangeValue = {
                state.onDevocionalContentChange(it)
            },
            imeAction = ImeAction.Done
        )

        AddResourceForDevocional(
            text = stringResource(R.string.tap_to_add_verse),
            onAddVerseToDevocional = {
                onAddVerseToDevocional(verse)
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        EkklesiaButton(
            label = stringResource(R.string.done),
            loading = state.savingDevocional,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            onSaveDevocional()
        }

        if (state.progressUploadVideo > 0f && state.progressUploadVideo < 1f) {
            LinearProgressIndicator(
                progress = { state.progressUploadVideo },
                trackColor = PrincipalColor.copy(alpha = 0.5f),
                color = PrincipalColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
            )
        } else if (state.progressUploadVideo == 1f) {
            VideoUploadedSuccessful(
                text = stringResource(R.string.video_upload_message),
                onDeleteVideo = {}
            )
        } else{
            EkklesiaButton(
                label = stringResource(R.string.add_video),
                filled = false,
                enabled = !state.savingDevocional,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                onCreateVideoForDevocional()
            }
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