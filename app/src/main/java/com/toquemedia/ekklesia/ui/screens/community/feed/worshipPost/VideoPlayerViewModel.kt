package com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.toquemedia.ekklesia.model.EkklesiaPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val player: EkklesiaPlayer
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideoPlayerUiState())
    val uiState = _uiState.asStateFlow<VideoPlayerUiState>()

    init {
        println("VIDEO PLAYER")
        _uiState.value.player = player
    }

    fun prepareVideo(video: Uri) {
        _uiState.value.player?.prepareVideo(video)
    }

    fun playVideo() {
        _uiState.value.player?.playVideo()
    }

    fun pauseVideo() {
        _uiState.value.player?.pauseVideo()
    }

    fun releasePlayer() {
        _uiState.value.apply {
           player?.releasePlayer()
        }
    }
}