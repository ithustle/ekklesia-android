package com.toquemedia.ekklesia.model.interfaces

interface VerseRepository {
    suspend fun markVerse(bookName: String, chapter: Int, versicle: Int, verse: String)
    suspend fun unMarkVerse(bookName: String, chapter: Int, versicle: Int)
    suspend fun getMarkedVerse(bookName: String, chapter: Int, versicle: Int): String?
    suspend fun getMarkedVerse()
    suspend fun getVerseOfDay()
}