package com.toquemedia.ekklesia.model.interfaces

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ekklesiaVerses")

abstract class EkklesiaDataStore(context: Context) {
    protected val dataStore: DataStore<Preferences> = context.dataStore

    protected suspend fun savePreference(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }
    protected suspend fun getPreference(key: String): String? {
        val preferences = dataStore.data.first()
        return preferences[stringPreferencesKey(key)]
    }
    protected fun getPreferences(): Flow<Preferences> {
        return dataStore.data
    }
    protected suspend fun clearPreference(key: String) {
        dataStore.edit { preference ->
            preference.remove(stringPreferencesKey(key))
        }
    }
}