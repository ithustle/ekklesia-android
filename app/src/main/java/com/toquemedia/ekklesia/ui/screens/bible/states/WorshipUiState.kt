package com.toquemedia.ekklesia.ui.screens.bible.states

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.toquemedia.ekklesia.model.UploadStatus
import com.toquemedia.ekklesia.model.WorshipEntity
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

data class WorshipUiState(
    val worshipTitle: TextFieldValue = TextFieldValue(),
    var worshipContent: TextFieldValue = TextFieldValue(),
    var worshipBackgroundColorString: String = "",
    var progressUploadVideo: Float = 0f,
    var uploadStatus: UploadStatus = UploadStatus.IDLE,
    var savingWorship: Boolean = false,
    var sharingWorship: Boolean = false,
    var worshipBackgroundColor: Color = PrincipalColor,
    var videoUri: Uri? = null,
    val worships: List<WorshipEntity> = emptyList(),
    val selectedWorship: WorshipEntity? = null,
    val onWorshipTitleChange: (TextFieldValue) -> Unit = {},
    val onWorshipContentChange: (TextFieldValue) -> Unit = {},
    val onWorshipBackgroundStringChange: (String) -> Unit = {},
    val onWorshipBackgroundChange: (Color) -> Unit = {}

)
