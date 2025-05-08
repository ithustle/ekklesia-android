package com.toquemedia.seedfy.extension

import com.toquemedia.seedfy.model.PostType

fun PostType.getBookWithChapterAndVersicle(): Triple<String, String, Int> {
    val data = this.verseId.split("_")
    return Triple(data[0], data[1], data[2].toInt())
}