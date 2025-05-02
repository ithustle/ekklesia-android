package com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost

import android.net.Uri
import com.toquemedia.ekklesia.model.EkklesiaPlayer

data class VideoPlayerUiState(
    val videoUri: Uri? = null,
    var player: EkklesiaPlayer? = null,
    var duration: Long = 0L,
    var currentTime: Long = 0L,
    var buffering: Boolean = false,
)
