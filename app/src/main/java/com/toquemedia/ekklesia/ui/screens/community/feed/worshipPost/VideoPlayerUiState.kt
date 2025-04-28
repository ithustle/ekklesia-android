package com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost

import android.net.Uri
import com.toquemedia.ekklesia.model.EkklesiaPlayer

data class VideoPlayerUiState(
    val videoUri: Uri? = null,
    val isPlaying: Boolean = false,
    var player: EkklesiaPlayer? = null,
)
