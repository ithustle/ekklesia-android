package com.toquemedia.ekklesia.model.interfaces

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

abstract class EkklesiaDataStore(context: Context, storeName: String) {

    protected val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile(storeName)
    }

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