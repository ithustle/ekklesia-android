package com.toquemedia.ekklesia.repository

import android.app.Application
import com.toquemedia.ekklesia.dao.VerseDao
import com.toquemedia.ekklesia.model.VerseRepository

class VerseRepositoryImpl(context: Application): VerseRepository {

    private val verse = VerseDao(context)

    override suspend fun markVerse(verseId: String, verse: String) {
        this.verse.markVerse(verseId, verse)
    }

    override suspend fun unMarkVerse(verseId: String) {
        this.verse.unMarkVerse(verseId)
    }

    override suspend fun getMarkedVerses(verseId: String): String? {
        return this.verse.getVerseMarked(verseId)
    }
}