package com.toquemedia.ekklesia.dao

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.toquemedia.ekklesia.model.interfaces.EkklesiaDataStore
import javax.inject.Inject

open class AppCacheDao @Inject constructor(context: Context) : EkklesiaDataStore(context, "ekklesia_cache") {
    suspend fun<T> saveCache(
        key: String,
        value: T
    ) {
        val value = Gson().toJson(value)
        super.savePreference(key, value)
    }

    suspend fun<T> getCache(key: String, typeToken: TypeToken<T>): T? {
        val value =  super.getPreference(key)
        val data = Gson().fromJson(value, typeToken)
        return data
    }
}