package com.toquemedia.ekklesia.dao

import android.content.Context
import com.toquemedia.ekklesia.model.interfaces.EkklesiaDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class CommunityInsiderDao @Inject constructor(
    @ApplicationContext context: Context
) : EkklesiaDataStore(context, "ekklesia_communities") {

    suspend fun saveCommunity(communityId: String) {
        super.savePreference(key = communityId, value = communityId)
    }

    fun getAll(): Flow<List<String>> {
        return super.getPreferences().mapNotNull {
            it.asMap()
                .values
                .filterIsInstance<String>()
                .toList()
        }
    }

    suspend fun remove(communityId: String) {
        super.clearPreference(key = communityId)
    }
}