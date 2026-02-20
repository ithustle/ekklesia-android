package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.dao.BibleDao
import com.toquemedia.ekklesia.model.BibleType
import com.toquemedia.ekklesia.model.BiblicalResponse
import com.toquemedia.ekklesia.model.BookType
import com.toquemedia.ekklesia.model.interfaces.BibleRepository
import com.toquemedia.ekklesia.services.FirebaseAiService
import javax.inject.Inject

class BibleRepositoryImpl @Inject constructor(
    private val dao: BibleDao,
    private val iaService: FirebaseAiService
) : BibleRepository {

    override fun loadBible(): List<BibleType> = dao.loadFileBible()

    override fun getBooks(): List<BookType> {
        val bible = this.loadBible()
        val books = mutableListOf<BookType>()
        for (b in bible) {
            val book = BookType(
                bookName = b.name,
                numberOfChapters = b.chapters.size,
                verses = b.chapters
            )
            books.add(book)
        }
        return books
    }

    override suspend fun talkToSeedfyBible(userPrompt: String): BiblicalResponse {
        //val bible = this.loadBible()
        return iaService.generateText(userPrompt)
    }
}