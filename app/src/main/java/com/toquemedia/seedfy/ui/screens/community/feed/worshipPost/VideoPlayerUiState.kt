package com.toquemedia.seedfy.ui.screens.community.feed.worshipPost

import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import com.toquemedia.seedfy.model.EkklesiaPlayer

@OptIn(UnstableApi::class)
data class VideoPlayerUiState(
    val videoUri: Uri? = null,
    var player: EkklesiaPlayer? = null,
    var isPlaying: Boolean = false,
    var duration: Long = 0L,
    var currentTime: Long = 0L,
    var buffering: Boolean = false,
)
