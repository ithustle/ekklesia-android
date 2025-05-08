package com.toquemedia.seedfy.model

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@UnstableApi
class EkklesiaPlayer @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context)
        .build()

    fun getPlayer(): ExoPlayer = exoPlayer

    fun prepareVideo(videoUri: Uri) {
        val mediaItem = MediaItem.fromUri(videoUri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    fun playVideo() = exoPlayer.play()

    fun pauseVideo() = exoPlayer.pause()

    fun seekTo(position: Long) = exoPlayer.seekTo(position)

    fun releasePlayer() {
        exoPlayer.stop()
        exoPlayer.release()
    }
}
