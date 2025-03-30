package com.toquemedia.ekklesia.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun Date.timeAgo(): String {
    val now = Date()
    val diff = now.time - this.time

    return when {
        diff < TimeUnit.MINUTES.toMillis(1) -> "${diff / 1000} seg"
        diff < TimeUnit.HOURS.toMillis(1) -> "${diff / TimeUnit.MINUTES.toMillis(1)} min"
        diff < TimeUnit.DAYS.toMillis(1) -> "${diff / TimeUnit.HOURS.toMillis(1)} h"
        diff < TimeUnit.DAYS.toMillis(2) -> "Ontem"
        else -> SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)
    }
}