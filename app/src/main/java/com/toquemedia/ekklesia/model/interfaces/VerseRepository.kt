package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.repository.VerseResponse
import com.toquemedia.ekklesia.services.StatsVerseOfDay

interface VerseRepository {
    suspend fun markVerse(bookName: String, chapter: Int, versicle: Int, verse: String)
    suspend fun unMarkVerse(bookName: String, chapter: Int, versicle: Int)
    suspend fun getMarkedVerse(bookName: String, chapter: Int, versicle: Int): String?
    suspend fun getMarkedVerse()
    suspend fun getVerseOfDay(): VerseResponse?
    suspend fun handleLikeVerseOfDay(isForLike: Boolean = true): StatsVerseOfDay
    suspend fun shareVerseOfDay(): StatsVerseOfDay
}