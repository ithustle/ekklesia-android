package com.toquemedia.seedfy.ui.screens.bible.worship

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.seedfy.extension.toColor
import com.toquemedia.seedfy.model.UploadStatus
import com.toquemedia.seedfy.model.WorshipEntity
import com.toquemedia.seedfy.repository.WorshipRepositoryImpl
import com.toquemedia.seedfy.ui.screens.bible.states.WorshipUiState
import com.toquemedia.seedfy.ui.theme.PrincipalColor
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class WorshipViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val repository: WorshipRepositoryImpl,
) : ViewModel() {

    private val _uiState: MutableStateFlow<WorshipUiState> =
        MutableStateFlow(WorshipUiState())
    val uiState: StateFlow<WorshipUiState> = _uiState.asStateFlow()

    init {
        println("RECRIAAAAAA")
        _uiState.update { currentState ->
            currentState.copy(
                onWorshipTitleChange = {
                    _uiState.value = _uiState.value.copy(worshipTitle = it)
                },
                onWorshipContentChange = {
                    _uiState.value = _uiState.value.copy(worshipContent = it)
                },
                onWorshipBackgroundChange = {
                    _uiState.value = _uiState.value.copy(worshipBackgroundColor = it)
                },
                onWorshipBackgroundStringChange = {
                    _uiState.value = _uiState.value.copy(worshipBackgroundColorString = it)
                }
            )
        }

        viewModelScope.launch {
            repository.getAllWorships().collect {
                _uiState.value = _uiState.value.copy(
                    worships = it
                )
            }
        }
    }

    fun saveWorship(
        bookName: String,
        chapter: Int?,
        versicle: Int,
        verse: String,
        video: Uri?
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(savingWorship = true) }

            _uiState.value.apply {
                try {
                    val worship = WorshipEntity(
                        id = "${bookName}_${chapter}_$versicle",
                        bookName = bookName,
                        versicle = versicle,
                        chapter = chapter ?: 0,
                        verse = verse,
                        backgroundColor = worshipBackgroundColorString,
                        title = worshipTitle.text.trimEnd(),
                        worship = worshipContent.text,
                        video = video?.toString(),
                        durationVideo = video?.let { (getDurationVideo(it) / 1000).toInt() }
                    )
                    repository.saveWorship(worship)
                    clearState()
                } catch (e: Exception) {
                    e.printStackTrace()
                    _uiState.update { it.copy(savingWorship = false) }
                }
            }
        }
    }

    fun addVerseToWorship(verse: String) {
        _uiState.update { state ->
            val updatedText = state.worshipContent.text + verse
            val newTextFieldValue = TextFieldValue(
                text = updatedText,
                selection = TextRange(updatedText.length)
            )
            state.copy(worshipContent = newTextFieldValue)
        }
    }

    fun prepareWorshipForEdit(worship: WorshipEntity) {
        worship.apply {
            _uiState.update { state ->
                state.copy(
                    selectedWorship = this,
                    worshipTitle = TextFieldValue(title),
                    worshipContent = TextFieldValue(this.worship),
                    worshipBackgroundColor = backgroundColor.toColor(),
                    worshipBackgroundColorString = backgroundColor,
                    videoUri = video?.toUri(),
                )
            }
        }
    }

    fun updateWorship(worship: WorshipEntity) {
        viewModelScope.launch {
            _uiState.value.apply {
                val worship = WorshipEntity(
                    id = worship.id,
                    bookName = worship.bookName,
                    versicle = worship.versicle,
                    chapter = worship.chapter,
                    verse = worship.verse,
                    backgroundColor = worshipBackgroundColorString,
                    title = worshipTitle.text.trimEnd(),
                    worship = worshipContent.text,
                    postId = worship.postId,
                    communityId = worship.communityId,
                    video = videoUri?.toString(),
                    durationVideo = videoUri?.let { (getDurationVideo(it) / 1000).toInt() }
                )
                repository.updateWorship(worship)
                clearState()
            }
        }
    }

    fun deleteWorship(worshipId: String) {
        viewModelScope.launch {
            repository.deleteWorship(worshipId)
        }
    }

    fun uploadRecordedVideo(videoPath: String) {
        _uiState.update { it.copy(uploadStatus = UploadStatus.INIT) }
        val file = File(videoPath)

        viewModelScope.launch {
            repository.uploadWorshipVideo(file = file).collect { progress ->
                _uiState.update {
                    it.copy(
                        progressUploadVideo = progress.first,
                        videoUri = progress.second,
                        uploadStatus = if (progress.first == 1f) UploadStatus.FINISHED else UploadStatus.DOWNLOADING
                    )
                }
            }
        }
    }

    fun deleteRecordedVideo(videoPath: String? = null) {

        videoPath?.let {
            val file = File(videoPath)
            if (file.exists()) {
                _uiState.update { it.copy(progressUploadVideo = 0f, uploadStatus = UploadStatus.IDLE) }
                file.delete()
            }
        } ?: run {
            _uiState.update {
                it.copy(videoUri = null)
            }
        }
    }

    suspend fun shareWorshipToCommunity(communityId: String, worship: WorshipEntity) {
        _uiState.update { it.copy(sharingWorship = true) }
        repository.shareToCommunity(communityId, worship).await()
        _uiState.update { it.copy(sharingWorship = false) }
    }

    private fun getDurationVideo(uri: Uri): Long {
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(context, uri)
            val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            time?.toLongOrNull() ?: 0L
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        } finally {
            retriever.release()
        }
    }

    private fun clearState() {
        _uiState.update {
            it.copy(
                savingWorship = false,
                videoUri = null,
                progressUploadVideo = 0f,
                worshipBackgroundColorString = "",
                worshipBackgroundColor = PrincipalColor,
                uploadStatus = UploadStatus.IDLE,
                worshipTitle = TextFieldValue(""),
                worshipContent = TextFieldValue("")
            )
        }
    }
}