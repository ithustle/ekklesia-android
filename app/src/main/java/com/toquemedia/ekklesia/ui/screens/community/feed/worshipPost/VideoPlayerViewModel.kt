package com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost

import android.net.Uri
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import com.toquemedia.ekklesia.model.EkklesiaPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
@OptIn(UnstableApi::class)
class VideoPlayerViewModel @OptIn(UnstableApi::class)
@Inject constructor(
    private val player: EkklesiaPlayer
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideoPlayerUiState())
    val uiState = _uiState.asStateFlow<VideoPlayerUiState>()

    init {
        println("PLAYER")
        _uiState.value.player = player


        player.getPlayer().addListener(object : Player.Listener {
            override fun onVideoSizeChanged(videoSize: VideoSize) {
                println("PLAYER Video size changed: ${videoSize.width}x${videoSize.height}")
            }
        })


        player.getPlayer().addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when(playbackState) {
                    Player.STATE_BUFFERING -> _uiState.update { it.copy(buffering = true) }
                    Player.STATE_READY -> _uiState.update { it.copy(buffering = false) }
                    Player.STATE_ENDED -> {}
                    Player.STATE_IDLE -> {}
                }
            }
        })
    }

    fun prepareVideo(video: Uri) = player.prepareVideo(video)

    fun playVideo() = player.playVideo()

    fun pauseVideo() = player.pauseVideo()

    fun handleReplay(position: Long) {
        player.seekTo(position = player.getPlayer().currentPosition + position)
    }

    fun releasePlayer() {
        _uiState.value.apply {
           player?.releasePlayer()
        }
    }
}