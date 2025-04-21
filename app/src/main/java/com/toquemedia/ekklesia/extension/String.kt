package com.toquemedia.ekklesia.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.core.graphics.scale
import androidx.core.net.toUri
import java.io.ByteArrayOutputStream
import kotlin.math.sqrt

fun String.splitTextByLineWidth(screenWidth: Int, percentOfScreen: Float): List<String> {
    val maxCharsPerLine = (screenWidth * percentOfScreen / 10).toInt()

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

fun String.toPortuguese(): String {
    val books = mapOf(
        "Genesis" to "Gênesis",
        "Exodus" to "Êxodo",
        "Leviticus" to "Levítico",
        "Numbers" to "Números",
        "Deuteronomy" to "Deuteronômio",
        "Joshua" to "Josué",
        "Judges" to "Juízes",
        "Ruth" to "Rute",
        "1 Samuel" to "1 Samuel",
        "2 Samuel" to "2 Samuel",
        "1 Kings" to "1 Reis",
        "2 Kings" to "2 Reis",
        "1 Chronicles" to "1 Crônicas",
        "2 Chronicles" to "2 Crônicas",
        "Ezra" to "Esdras",
        "Nehemiah" to "Neemias",
        "Esther" to "Ester",
        "Job" to "Jó",
        "Psalm" to "Salmos",
        "Proverbs" to "Provérbios",
        "Ecclesiastes" to "Eclesiastes",
        "Song of Solomon" to "Cânticos dos Cânticos",
        "Isaiah" to "Isaías",
        "Jeremiah" to "Jeremias",
        "Lamentations" to "Lamentações",
        "Ezekiel" to "Ezequiel",
        "Daniel" to "Daniel",
        "Hosea" to "Oseias",
        "Joel" to "Joel",
        "Amos" to "Amós",
        "Obadiah" to "Obadias",
        "Jonah" to "Jonas",
        "Micah" to "Miqueias",
        "Nahum" to "Naum",
        "Habakkuk" to "Habacuque",
        "Zephaniah" to "Sofonias",
        "Haggai" to "Ageu",
        "Zechariah" to "Zacarias",
        "Malachi" to "Malaquias",
        "Matthew" to "Mateus",
        "Mark" to "Marcos",
        "Luke" to "Lucas",
        "John" to "João",
        "Acts" to "Atos",
        "Romans" to "Romanos",
        "1 Corinthians" to "1 Coríntios",
        "2 Corinthians" to "2 Coríntios",
        "Galatians" to "Gálatas",
        "Ephesians" to "Efésios",
        "Philippians" to "Filipenses",
        "Colossians" to "Colossenses",
        "1 Thessalonians" to "1 Tessalonicenses",
        "2 Thessalonians" to "2 Tessalonicenses",
        "1 Timothy" to "1 Timóteo",
        "2 Timothy" to "2 Timóteo",
        "Titus" to "Tito",
        "Philemon" to "Filemom",
        "Hebrews" to "Hebreus",
        "James" to "Tiago",
        "1 Peter" to "1 Pedro",
        "2 Peter" to "2 Pedro",
        "1 John" to "1 João",
        "2 John" to "2 João",
        "3 John" to "3 João",
        "Jude" to "Judas",
        "Revelation" to "Apocalipse"
    )
    return books[this] ?: this
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