package com.toquemedia.ekklesia.extension

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