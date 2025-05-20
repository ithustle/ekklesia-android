package com.toquemedia.seedfy.repository

import androidx.datastore.preferences.core.Preferences
import com.toquemedia.seedfy.dao.VerseDao
import com.toquemedia.seedfy.extension.toPortuguese
import com.toquemedia.seedfy.model.StoryType
import com.toquemedia.seedfy.model.VerseResponse
import com.toquemedia.seedfy.model.interfaces.VerseRepository
import com.toquemedia.seedfy.services.OurmannaService
import com.toquemedia.seedfy.services.StatsVerseOfDay
import com.toquemedia.seedfy.services.StoryService
import com.toquemedia.seedfy.services.UserService
import com.toquemedia.seedfy.services.VerseOfDayService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class VerseRepositoryImpl @Inject constructor(
    private val verse: VerseDao,
    private val ourmannaService: OurmannaService,
    private val userService: UserService,
    private val verseService: VerseOfDayService,
    private val storyService: StoryService
): VerseRepository {

    val markedVerses: MutableStateFlow<List<String>> = verse.versesMarked

    override suspend fun markVerse(bookName: String, chapter: Int, versicle: Int, verse: String) {
        val verseId = this.getId(bookName, chapter, versicle)
        this.verse.markVerse(verseId, verse)
    }

    override suspend fun unMarkVerse(bookName: String, chapter: Int, versicle: Int) {
        val verseId = this.getId(bookName, chapter, versicle)
        this.verse.unMarkVerse(verseId)
    }

    override suspend fun getMarkedVerse(bookName: String, chapter: Int, versicle: Int): String? {
        val verseId = this.getId(bookName, chapter, versicle)
        return this.verse.getVerseMarked(verseId)
    }

    override suspend fun getMarkedVerse(): Flow<Preferences> {
        return this.verse.getVerseMarked()
    }

    override suspend fun getVerseOfDay(): VerseResponse? {
        try {
            val regex = Regex("""([\w\s]+)\s(\d+):(\d+)""")
            val response = ourmannaService.getVerseOfDay()

            val matchResult = regex.find(response.verse.details.reference)

            return matchResult?.let {
                val (book, cap, verse) = it.destructured
                val verseOfDay = Triple(book.toPortuguese(), cap.toInt(), verse.toInt())
                val stats = verseService.getVerseOfDay()
                VerseResponse(verseOfDay, stats)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        } catch (e: HttpException) {
            e.printStackTrace()
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun handleLikeVerseOfDay(isForLike: Boolean): StatsVerseOfDay {
        verseService.saveLikedVerseOfDay(isForLike)
        return verseService.getVerseOfDay()
    }

    override suspend fun shareVerseOfDay(): StatsVerseOfDay {
        verseService.saveSharedVerseOfDay()
        return verseService.getVerseOfDay()
    }

    override suspend fun addStoryToCommunity(story: StoryType) {
        storyService.addStory(story)
    }

    override suspend fun getStories(communityId: String): List<StoryType> {
        return storyService.getStories(communityId)
    }

    internal fun getId(bookName: String, chapter: Int, versicle: Int): String {
        val user = userService.getCurrentUser()
        return "${bookName}_${chapter}_${versicle}_${user?.id}"
    }
}