package com.toquemedia.ekklesia.dao

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.toquemedia.ekklesia.model.interfaces.EkklesiaDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first


class VerseDao(context: Context) : EkklesiaDataStore(context) {

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