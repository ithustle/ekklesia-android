package com.toquemedia.ekklesia.ui.screens.bible.states

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.toquemedia.ekklesia.model.DevocionalEntity

data class DevocionalUiState(
    val devocionalTitle: TextFieldValue = TextFieldValue(),
    var devocionalContent: TextFieldValue = TextFieldValue(),
    var progressUploadVideo: Float = 0f,
    var savingDevocional: Boolean = false,
    var videoUri: Uri? = null,
    val allDevocional: List<DevocionalEntity> = emptyList(),
    val onDevocionalTitleChange: (TextFieldValue) -> Unit = {},
    val onDevocionalContentChange: (TextFieldValue) -> Unit = {}
)
