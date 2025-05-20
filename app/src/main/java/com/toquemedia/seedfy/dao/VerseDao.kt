package com.toquemedia.seedfy.dao

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import com.toquemedia.seedfy.model.interfaces.EkklesiaDataStore
import kotlinx.coroutines.flow.Flow
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

    fun getVerseMarked(): Flow<Preferences> {
        val preferences = super.getPreferences()
        return preferences
    }
}