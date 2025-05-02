package com.toquemedia.ekklesia.repository

import android.net.Uri
import androidx.core.net.toUri
import com.toquemedia.ekklesia.dao.WorshipDao
import com.toquemedia.ekklesia.model.PostType
import com.toquemedia.ekklesia.model.WorshipEntity
import com.toquemedia.ekklesia.model.interfaces.WorshipRepository
import com.toquemedia.ekklesia.services.BunnyService
import com.toquemedia.ekklesia.services.PostService
import com.toquemedia.ekklesia.services.ProgressRequestBody
import com.toquemedia.ekklesia.services.UserService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.MediaType.Companion.toMediaType
import java.io.File
import java.util.UUID
import javax.inject.Inject

class WorshipRepositoryImpl @Inject constructor(
    private val dao: WorshipDao,
    private val postService: PostService,
    private val user: UserService,
    private val bunnyService: BunnyService
) : WorshipRepository {

    override suspend fun saveWorship(worship: WorshipEntity) = dao.insert(worship)
    override suspend fun updateWorship(worship: WorshipEntity) {
        dao.update(worship)

        if (worship.postId != null && worship.communityId != null) {
            val post = PostType(
                worship = worship,
                communityId = listOf(worship.communityId!!),
                user = user.getCurrentUser(),
                verseId = worship.postId!!
            )
            postService.addPost(post, isUpdate = true)
        }
    }
    override suspend fun deleteWorship(id: String) = dao.deleteWorship(id)
    override fun getAllWorships(): Flow<List<WorshipEntity>> = dao.getAll()

    override suspend fun shareToCommunity(communityId: String, worship: WorshipEntity) =
        coroutineScope {
            async {
                val currentUser = user.getCurrentUser()
                val postId = UUID.randomUUID().toString()

                worship.communityId = communityId
                worship.postId = postId

                println(worship)

                val post = PostType(
                    worship = worship,
                    communityId = listOf(communityId),
                    user = currentUser,
                    verseId = postId
                )
                dao.associatePostToWorship(postId, communityId, worship.id)
                postService.addPost(post)
            }
        }


    override fun uploadWorshipVideo(file: File): Flow<Pair<Float, Uri?>> = callbackFlow {
        try {

            val progressRequestBody = ProgressRequestBody(
                file = file,
                contentType = "application/octet-stream".toMediaType(),
            ) { progress ->
                val pr: Float = progress / 100f
                trySend(Pair(pr, null))
            }

            val filename = file.name

            println(filename)
            println(progressRequestBody)

            val response = bunnyService.uploadVideo(filename = filename, file = progressRequestBody)

            println(response)

            if (response.HttpCode == "201") {
                val uri = "https://ekklesia.b-cdn.net/$filename".toUri()
                trySend(Pair(1f, uri))
                close()
            } else {
                trySend(Pair(0f, null))
                close(IllegalStateException("Erro na resposta: ${response.Message}"))
            }

        } catch(e: Exception) {
            trySend(Pair(0f, null))
            close(e)
        }
    }
}