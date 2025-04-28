package com.toquemedia.ekklesia.model

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EkklesiaPlayer @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    fun getPlayer(): Player = exoPlayer

    fun prepareVideo(videoUri: Uri) {
        val mediaItem = MediaItem.fromUri(videoUri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

    fun playVideo() {
        exoPlayer.play()
    }

    fun pauseVideo() {
        exoPlayer.pause()
    }

    fun releasePlayer() {
        exoPlayer.stop()
        exoPlayer.release()
    }
}