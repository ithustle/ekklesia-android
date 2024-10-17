package com.toquemedia.ekklesia.model

interface VerseRepository {
    suspend fun markVerse(verseId: String, verse: String)
    suspend fun unMarkVerse(verseId: String)
    suspend fun getMarkedVerses(verseId: String): String?
}