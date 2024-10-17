package com.toquemedia.ekklesia.dao

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.toquemedia.ekklesia.model.EkklesiaDataStore
import kotlinx.coroutines.flow.first


class VerseDao(context: Context) : EkklesiaDataStore(context) {

    override suspend fun savePreference(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preference ->
            preference[key] = value
        }
    }

    override suspend fun getPreference(key: Preferences.Key<String>): String? {
        val preferences = dataStore.data.first()
        return preferences[key]
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
}