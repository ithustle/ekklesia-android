package com.toquemedia.ekklesia.model

interface VerseRepository {
    suspend fun markVerse(bookName: String, chapter: Int, verse: String)
    suspend fun unMarkVerse(bookName: String, chapter: Int)
    suspend fun getMarkedVerse(bookName: String, chapter: Int): String?
    suspend fun getMarkedVerse(): MutableList<String>
}