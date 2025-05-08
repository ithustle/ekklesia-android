package com.toquemedia.seedfy.repository

import com.toquemedia.seedfy.dao.BibleDao
import com.toquemedia.seedfy.model.interfaces.BibleRepository
import com.toquemedia.seedfy.model.BibleType
import com.toquemedia.seedfy.model.BookType
import javax.inject.Inject

class BibleRepositoryImpl @Inject constructor(
    private val dao: BibleDao,
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
}