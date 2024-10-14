package com.toquemedia.ekklesia.repository

import android.content.Context
import com.toquemedia.ekklesia.dao.BibleDao
import com.toquemedia.ekklesia.model.BibleType
import com.toquemedia.ekklesia.model.BookType

object BibleRepository {

    fun loadBible(context: Context): List<BibleType> {
        return BibleDao.shared.loadFileBible(context)
    }

    fun getBooks(context: Context): List<BookType> {
        val bible = this.loadBible(context)
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