package com.toquemedia.ekklesia.model.interfaces

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ekklesiaVerses")

abstract class EkklesiaDataStore(context: Context) {
    protected val dataStore: DataStore<Preferences> = context.dataStore

    abstract suspend fun savePreference(key: Preferences.Key<String>, value: String)
    abstract suspend fun getPreference(key: Preferences.Key<String>): String?
    abstract suspend fun getPreferences(): Flow<Preferences>
    abstract suspend fun clearPreference(key: Preferences.Key<String>)

}