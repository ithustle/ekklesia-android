package com.toquemedia.ekklesia.dao

import android.content.Context
import com.toquemedia.ekklesia.model.interfaces.EkklesiaDataStore
import javax.inject.Inject

class AppCacheDao @Inject constructor(context: Context) : EkklesiaDataStore(context) {
    suspend fun saveCache(
        key: String,
        value: String
    ) {
        super.savePreference(key, value)
    }

    suspend fun getCache(key: String): String? {
        return super.getPreference(key)
    }
}