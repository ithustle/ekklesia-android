package com.toquemedia.seedfy.extension

fun Int.formatTime(): String {
    val minutes = this / 60
    val seconds = this % 60
    return String.format("%02d:%02d", minutes, seconds)
}