package com.toquemedia.seedfy.model.interfaces

import com.toquemedia.seedfy.model.StoryType
import com.toquemedia.seedfy.model.VerseResponse
import com.toquemedia.seedfy.services.StatsVerseOfDay

interface VerseRepository {
    suspend fun markVerse(bookName: String, chapter: Int, versicle: Int, verse: String)
    suspend fun unMarkVerse(bookName: String, chapter: Int, versicle: Int)
    suspend fun getMarkedVerse(bookName: String, chapter: Int, versicle: Int): String?
    suspend fun getMarkedVerse()
    suspend fun getVerseOfDay(): VerseResponse?
    suspend fun handleLikeVerseOfDay(isForLike: Boolean = true): StatsVerseOfDay
    suspend fun shareVerseOfDay(): StatsVerseOfDay
    suspend fun addStoryToCommunity(story: StoryType)
    suspend fun getStories(communityId: String): List<StoryType>
}