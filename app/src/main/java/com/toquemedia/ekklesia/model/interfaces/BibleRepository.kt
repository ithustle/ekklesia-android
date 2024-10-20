package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.BibleType
import com.toquemedia.ekklesia.model.BookType

interface BibleRepository {
    fun loadBible(): List<BibleType>
    fun getBooks(): List<BookType>
}