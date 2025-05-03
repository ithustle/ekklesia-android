package com.toquemedia.ekklesia.extension

import java.text.SimpleDateFormat
import java.util.Calendar
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

fun Date.convertToString(): String {
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(this)
}

fun Date.getGreeting(): String {
    val calendar = Calendar.getInstance().apply {
        time = this@getGreeting
    }

    val hour = calendar.get(Calendar.HOUR_OF_DAY)

    return when {
        hour < 12 -> "Bom dia"
        hour < 18 -> "Boa tarde"
        else -> "Boa noite"
    }
}


fun Date.addDateForTomorrow(): Date {
    return Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, 1)
    }.time
}