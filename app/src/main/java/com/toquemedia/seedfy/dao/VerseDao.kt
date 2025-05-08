package com.toquemedia.seedfy.dao

import android.content.Context
import com.toquemedia.seedfy.model.interfaces.EkklesiaDataStore
import kotlinx.coroutines.flow.MutableStateFlow


class VerseDao(context: Context) : EkklesiaDataStore(context, "ekklesia_verses") {

    private val _versesFlow = MutableStateFlow<List<String>>(emptyList())
    var versesMarked: MutableStateFlow<List<String>> = _versesFlow

    suspend fun markVerse(bookAndCap: String, verse: String) {
        super.savePreference(bookAndCap, verse)
    }

    suspend fun unMarkVerse(bookAndCap: String) {
        super.clearPreference(bookAndCap)
    }

    suspend fun getVerseMarked(bookAndCap: String): String? {
        return super.getPreference(bookAndCap)
    }

    suspend fun getVerseMarked() {
        val preferences = super.getPreferences()
        val verses = mutableListOf<String>()
        preferences.collect { preference ->
            for (key in preference.asMap().keys) {
                val value = preference[key].toString()
                verses.add(value)
            }
            _versesFlow.value = verses
        }
    }
}