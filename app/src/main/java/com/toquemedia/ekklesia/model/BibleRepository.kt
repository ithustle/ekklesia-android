package com.toquemedia.ekklesia.model

import android.content.Context

interface BibleRepository {
    fun loadBible(): List<BibleType>
    fun getBooks(): List<BookType>
}