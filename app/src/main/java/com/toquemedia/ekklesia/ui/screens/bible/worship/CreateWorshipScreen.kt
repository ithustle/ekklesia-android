package com.toquemedia.ekklesia.ui.screens.bible.worship

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.WorshipEntity
import com.toquemedia.ekklesia.ui.composables.EkklesiaButton
import com.toquemedia.ekklesia.ui.composables.EkklesiaProgress
import com.toquemedia.ekklesia.ui.composables.EkklesiaTextField
import com.toquemedia.ekklesia.ui.composables.VerseToAnnotation
import com.toquemedia.ekklesia.ui.screens.bible.states.WorshipUiState

@Composable
fun CreateWorshipScreen(
    context: Context,
    bookName: String? = null,
    versicle: Int? = null,
    chapter: String? = null,
    isUpdating: Boolean = false,
    videoPathResult: String? = null,
    verse: String? = null,
    state: WorshipUiState,
    onSaveWorship: () -> Unit = {},
    onUpdateWorship: (WorshipEntity) -> Unit = {},
    onDeleteWorshipVideo: () -> Unit = {},
    onCreateVideoForWorship: () -> Unit = {},
    onAddVerseToWorship: (String) -> Unit = {}
) {

    if (!isUpdating) {
        BackHandler(enabled = state.worshipTitle.text.isNotEmpty() || state.progressUploadVideo > 0f) {
            Toast.makeText(
                context, "Você precisa terminar a edição do devocional ou apagar tudo antes de sair",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 30.dp)
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerseToAnnotation(
            bookName = state.selectedWorship?.bookName ?: bookName ?: "",
            versicle = state.selectedWorship?.versicle ?: versicle ?: 0,
            chapter = state.selectedWorship?.chapter?.toString() ?: chapter ?: "",
            verse = state.selectedWorship?.verse ?: verse ?: "",
            bookNameAsTitle = true,
            color = state.worshipBackgroundColor
        )

        Spacer(modifier = Modifier.height(32.dp))

        EkklesiaTextField(
            value = state.worshipTitle.text,
            placeholder = stringResource(R.string.devocional_title),
            height = 41.dp,
            singleLine = true,
            onChangeValue = state.onWorshipTitleChange,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(20.dp))

        EkklesiaTextField(
            value = state.worshipContent.text,
            placeholder = stringResource(R.string.let_holy_spirit_come),
            height = 250.dp,
            onChangeValue = state.onWorshipContentChange,
            imeAction = ImeAction.Done
        )

        Spacer(Modifier.height(10.dp))

        ColorPicker(
            initialColor = state.worshipBackgroundColor,
            onColorChanged = {
                if (it != Color(0xFFFFFFFF)) {
                    state.onWorshipBackgroundChange(it)
                }
                state.onWorshipBackgroundStringChange(it.toString())
            }
        )

        Spacer(Modifier.height(10.dp))

        AddResourceForWorship(
            text = stringResource(R.string.tap_to_add_verse),
            color = state.worshipBackgroundColor,
            onAddVerseToWorship = {
                if (verse != null) {
                    onAddVerseToWorship(verse)
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        EkklesiaButton(
            label = stringResource(R.string.done),
            loading = state.savingWorship,
            color = state.worshipBackgroundColor,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isUpdating) {
                state.selectedWorship?.let {
                    onUpdateWorship(it)
                }
            } else {
                onSaveWorship()
            }
        }

        if (videoPathResult != null) {
            EkklesiaProgress(color = state.worshipBackgroundColor)
        } else if (state.videoUri != null) {
            VideoUploadedSuccessful(
                text = stringResource(R.string.video_upload_message),
                color = state.worshipBackgroundColor,
                onDeleteVideo = onDeleteWorshipVideo
            )
        } else if (state.progressUploadVideo > 0f && state.progressUploadVideo < 1f) {
            LinearProgressIndicator(
                progress = { state.progressUploadVideo },
                trackColor = state.worshipBackgroundColor.copy(alpha = 0.5f),
                color = state.worshipBackgroundColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
            )
        } else {
            EkklesiaButton(
                label = stringResource(R.string.add_video),
                filled = false,
                color = state.worshipBackgroundColor,
                enabled = !state.savingWorship,
                modifier = Modifier.fillMaxWidth()
            ) {
                onCreateVideoForWorship()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateWorshipScreenPrev() {
    CreateWorshipScreen(
        bookName = "Provérbios",
        versicle = 1,
        chapter = "12",
        verse = "Os tesouros de origem desonesta não servem para nada, mas a retidão livra da morte.",
        state = WorshipUiState(),
        context = LocalContext.current
    )
}