package com.toquemedia.seedfy.dao

import android.content.Context
import com.toquemedia.seedfy.model.interfaces.EkklesiaDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class LikeDao @Inject constructor(
    @ApplicationContext context: Context
) : EkklesiaDataStore(context, "ekklesia_likes") {

    suspend fun saveLikeRegister(postId: String, communityId: String) {
        super.savePreference(key = "${postId}_$communityId", value = "${postId}_$communityId")
    }

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

    suspend fun removeLikeRegister(postId: String, communityId: String) {
        super.clearPreference(key = "${postId}_$communityId")
    }

    suspend fun removeLikeRegister(postId: String) {
        super.clearPreference(key = postId)
    }
}