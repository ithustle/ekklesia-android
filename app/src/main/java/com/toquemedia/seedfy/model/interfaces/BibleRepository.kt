package com.toquemedia.seedfy.model.interfaces

import com.toquemedia.seedfy.model.BibleType
import com.toquemedia.seedfy.model.BiblicalResponse
import com.toquemedia.seedfy.model.BookType

interface BibleRepository {
    fun loadBible(): List<BibleType>
    fun getBooks(): List<BookType>
    suspend fun talkToSeedfyBible(userPrompt: String): BiblicalResponse
}