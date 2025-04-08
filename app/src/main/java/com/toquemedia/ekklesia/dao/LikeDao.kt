package com.toquemedia.ekklesia.dao

import android.content.Context
import com.toquemedia.ekklesia.model.interfaces.EkklesiaDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class LikeDao @Inject constructor(
    @ApplicationContext context: Context
) : EkklesiaDataStore(context, "ekklesia_likes") {

    suspend fun saveLikeRegister(postId: String) {
        super.savePreference(key = postId, value = postId)
    }

    fun getLikes(): Flow<List<String>> {
        return super.getPreferences().mapNotNull { preference ->
            preference.asMap()
                .values
                .filterIsInstance<String>()
                .toList()
        }
    }

    suspend fun removeLikeRegister(postId: String) {
        super.clearPreference(key = postId)
    }
}