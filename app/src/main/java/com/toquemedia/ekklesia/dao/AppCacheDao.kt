package com.toquemedia.ekklesia.dao

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.toquemedia.ekklesia.model.interfaces.EkklesiaDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AppCacheDao @Inject constructor(context: Context) : EkklesiaDataStore(context) {
    override suspend fun savePreference(
        key: Preferences.Key<String>,
        value: String
    ) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun getPreference(key: Preferences.Key<String>): String? {
        val preferences = dataStore.data.first()
        return preferences[key]
    }

    override suspend fun getPreferences(): Flow<Preferences> {
        TODO("Not yet implemented")
    }

    override suspend fun clearPreference(key: Preferences.Key<String>) {
        TODO("Not yet implemented")
    }
}