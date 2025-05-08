package com.toquemedia.seedfy.model.interfaces

import android.net.Uri
import com.toquemedia.seedfy.model.WorshipEntity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import java.io.File

interface WorshipRepository {
    suspend fun saveWorship(worship: WorshipEntity)
    suspend fun updateWorship(worship: WorshipEntity)
    suspend fun deleteWorship(id: String)
    suspend fun shareToCommunity(communityId: String, worship: WorshipEntity): Deferred<Unit>
    fun getAllWorships(): Flow<List<WorshipEntity>>
    fun uploadWorshipVideo(file: File): Flow<Pair<Float, Uri?>>
}