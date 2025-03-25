package com.toquemedia.ekklesia.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.core.graphics.scale
import androidx.core.net.toUri
import java.io.ByteArrayOutputStream
import java.util.Locale
import kotlin.math.sqrt

fun String.splitTextByLineWidth(maxCharsPerLine: Int): List<String> {
    val words = this.split(" ")
    val result = mutableListOf<String>()
    var currentLine = ""

    words.forEach { word ->
        if (currentLine.length + word.length + 1 > maxCharsPerLine) {
            result.add(currentLine.trim())
            currentLine = ""
        }
        currentLine += "$word "
    }

    if (currentLine.isNotEmpty()) {
        result.add(currentLine.trim())
    }

    return result
}

fun String.uriToBase64(context: Context): String {
    return try {
        val inputStream = context.contentResolver.openInputStream(this.toUri())
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
        bitmapToBase64WithSizeLimit(bitmap)
    } catch (e: Exception) {
        e.printStackTrace()
        throw e
    }
}

fun String.base64ToBitmap(): Bitmap {
    val decodedBytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

fun String.getInitials(): String {
    val ignoredWords = listOf("da", "de", "do", "das", "dos", "e", "a", "o")
    val words = this.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }
    return words
        .filter { it.lowercase() !in ignoredWords }
        .mapNotNull { it.firstOrNull()?.uppercase() }
        .joinToString("")
}

private fun bitmapToBase64WithSizeLimit(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    var quality = 100
    val maxSizeKB = 500
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

    while (outputStream.size() > maxSizeKB * 1024 && quality > 10) {
        outputStream.reset()
        quality -= 10
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    }

    if (outputStream.size() > maxSizeKB * 1024) {
        val scaleFactor = sqrt((maxSizeKB * 1024.0) / outputStream.size())
        val newWidth = (bitmap.width * scaleFactor).toInt()
        val newHeight = (bitmap.height * scaleFactor).toInt()

        val resizedBitmap = bitmap.scale(newWidth, newHeight)

        outputStream.reset()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    }

    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}