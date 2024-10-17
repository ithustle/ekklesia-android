package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.dao.BibleDao
import com.toquemedia.ekklesia.model.BibleRepository
import com.toquemedia.ekklesia.model.BibleType
import com.toquemedia.ekklesia.model.BookType
import javax.inject.Inject

class BibleRepositoryImpl @Inject constructor(
    private val dao: BibleDao,
) : BibleRepository {

    override fun loadBible(): List<BibleType> {
        return dao.loadFileBible()
    }

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