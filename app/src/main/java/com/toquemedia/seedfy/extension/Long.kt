package com.toquemedia.seedfy.extension

import java.util.Locale

fun Long.formatTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    return String.format(locale = Locale.US, "%02d:%02d", minutes, seconds)
}