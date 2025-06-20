package com.toquemedia.seedfy.model.interfaces

import androidx.datastore.preferences.core.Preferences
import com.toquemedia.seedfy.model.StoryType
import com.toquemedia.seedfy.model.VerseResponse
import com.toquemedia.seedfy.services.StatsVerseOfDay
import kotlinx.coroutines.flow.Flow

interface VerseRepository {
    suspend fun markVerse(bookName: String, chapter: Int, versicle: Int, verse: String)
    suspend fun unMarkVerse(bookName: String, chapter: Int, versicle: Int)
    suspend fun getMarkedVerse(bookName: String, chapter: Int, versicle: Int): String?
    suspend fun getMarkedVerse(): Flow<Preferences>
    suspend fun getVerseOfDay(): VerseResponse?
    suspend fun handleLikeVerseOfDay(isForLike: Boolean = true): StatsVerseOfDay
    suspend fun shareVerseOfDay(): StatsVerseOfDay
    suspend fun addStoryToCommunity(story: StoryType)
    suspend fun getStories(communityId: String): List<StoryType>
}