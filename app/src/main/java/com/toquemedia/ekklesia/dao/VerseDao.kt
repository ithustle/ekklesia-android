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

    override suspend fun savePreference(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preference ->
            preference[key] = value
        }
    }

    override suspend fun getPreference(key: Preferences.Key<String>): String? {
        val preferences = dataStore.data.first()
        return preferences[key]
    }

    override suspend fun getPreferences(): Flow<Preferences> {
        val preferences = dataStore.data
        return preferences
    }

    override suspend fun clearPreference(key: Preferences.Key<String>) {
        dataStore.edit { preference ->
            preference.remove(key)
        }
    }

    suspend fun markVerse(bookAndCap: String, verse: String) {
        this.savePreference(stringPreferencesKey(bookAndCap), verse)
    }

    suspend fun unMarkVerse(bookAndCap: String) {
        this.clearPreference(stringPreferencesKey(bookAndCap))
    }

    suspend fun getVerseMarked(bookAndCap: String): String? {
        return this.getPreference(stringPreferencesKey(bookAndCap))
    }

    suspend fun getVerseMarked() {
        val preferences = this.getPreferences()
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