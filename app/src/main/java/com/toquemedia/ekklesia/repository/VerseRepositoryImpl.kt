package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.dao.VerseDao
import com.toquemedia.ekklesia.model.VerseRepository
import javax.inject.Inject

class VerseRepositoryImpl @Inject constructor (
    private val verse: VerseDao
): VerseRepository {

    override suspend fun markVerse(bookName: String, chapter: Int, verse: String) {
        val verseId = "${bookName}_$chapter"
        this.verse.markVerse(verseId, verse)
    }

    override suspend fun unMarkVerse(bookName: String, chapter: Int) {
        val verseId = "${bookName}_$chapter"
        this.verse.unMarkVerse(verseId)
    }

    override suspend fun getMarkedVerse(bookName: String, chapter: Int): String? {
        val verseId = "${bookName}_$chapter"
        return this.verse.getVerseMarked(verseId)
    }

    override suspend fun getMarkedVerse(): MutableList<String> {
        return this.verse.getVerseMarked()
    }
}