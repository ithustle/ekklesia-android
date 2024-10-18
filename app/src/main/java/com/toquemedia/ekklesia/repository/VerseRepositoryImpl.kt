package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.dao.VerseDao
import com.toquemedia.ekklesia.model.VerseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class VerseRepositoryImpl @Inject constructor (
    private val verse: VerseDao
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
}