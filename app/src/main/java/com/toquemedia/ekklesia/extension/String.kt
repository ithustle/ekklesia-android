package com.toquemedia.ekklesia.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import java.io.ByteArrayOutputStream
import android.util.Base64
import androidx.core.graphics.scale
import java.util.Locale

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

fun String.textSlug(): String {
    return this
        .lowercase(Locale.ROOT)
        .replace(" ", "-")
        .replace("[áàãâä]".toRegex(), "a")
        .replace("[éèêë]".toRegex(), "e")
        .replace("[íìîï]".toRegex(), "i")
        .replace("[óòõôö]".toRegex(), "o")
        .replace("[úùûü]".toRegex(), "u")
        .replace("ç", "c")
        .replace("[^a-z0-9-]".toRegex(), "")
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
        val scaleFactor = Math.sqrt((maxSizeKB * 1024.0) / outputStream.size())
        val newWidth = (bitmap.width * scaleFactor).toInt()
        val newHeight = (bitmap.height * scaleFactor).toInt()

        val resizedBitmap = bitmap.scale(newWidth, newHeight)

        outputStream.reset()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    }

    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}