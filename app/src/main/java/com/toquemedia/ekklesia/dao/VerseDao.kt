package com.toquemedia.ekklesia.dao

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.asLiveData
import com.toquemedia.ekklesia.model.EkklesiaDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf


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

    suspend fun getVerseMarked(): SnapshotStateList<String> {
        val preferences = this.getPreferences()
        val verses = mutableStateListOf<String>()
        preferences.collect { preference ->
            for (key in preference.asMap().keys) {
                val value = preference[key].toString()
                Log.i("DADAOS", value.toString())
                verses.add(value)
            }

        }
        Log.i("DADOS", verses.toString())
        return verses
    }
}