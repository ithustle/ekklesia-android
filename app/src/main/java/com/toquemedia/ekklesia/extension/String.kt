package com.toquemedia.ekklesia.extension

import androidx.compose.ui.graphics.Color

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

fun String.toColor(): Color {
    if (!this.startsWith("Color(") || !this.endsWith(")")) {
        return Color.Gray
    }

    return try {
        val content = this.substringAfter("Color(").substringBeforeLast(")")

        val parts = content.split(',').map { it.trim() }

        if (parts.size < 4) {
            return Color.Gray
        }

        val red = parts[0].toFloat()
        val green = parts[1].toFloat()
        val blue = parts[2].toFloat()
        val alpha = parts[3].toFloat()

        Color(red = red, green = green, blue = blue, alpha = alpha)

    } catch (e: NumberFormatException) {
        e.printStackTrace()
        Color.Red
    } catch (e: Exception) {
        e.printStackTrace()
        Color.Gray
    }
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