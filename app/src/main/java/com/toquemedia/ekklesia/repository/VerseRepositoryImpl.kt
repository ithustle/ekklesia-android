package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.dao.VerseDao
import com.toquemedia.ekklesia.extension.toPortuguese
import com.toquemedia.ekklesia.model.interfaces.VerseRepository
import com.toquemedia.ekklesia.services.OurmannaService
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class VerseRepositoryImpl @Inject constructor(
    private val verse: VerseDao,
    private val ourmannaService: OurmannaService
): VerseRepository {

    val markedVerses: MutableStateFlow<List<String>> = verse.versesMarked

    override suspend fun markVerse(bookName: String, chapter: Int, versicle: Int, verse: String) {
        val verseId = "${bookName}_${chapter}_$versicle"
        this.verse.markVerse(verseId, verse)
    }

    override suspend fun unMarkVerse(bookName: String, chapter: Int, versicle: Int) {
        val verseId = "${bookName}_${chapter}_$versicle"
        this.verse.unMarkVerse(verseId)
    }

    override suspend fun getMarkedVerse(bookName: String, chapter: Int, versicle: Int): String? {
        val verseId = "${bookName}_${chapter}_$versicle"
        return this.verse.getVerseMarked(verseId)
    }

    override suspend fun getMarkedVerse() {
        return this.verse.getVerseMarked()
    }

    override suspend fun getVerseOfDay(): Triple<String, Int, Int>? {
        val regex = Regex("""([\w\s]+)\s(\d+):(\d+)""")
        val response = ourmannaService.getVerseOfDay()

        val matchResult = regex.find(response.verse.details.reference)

        return matchResult?.let {
            val (book, cap, verse) = it.destructured
            Triple(book.toPortuguese(), cap.toInt(), verse.toInt())
        }
    }
}