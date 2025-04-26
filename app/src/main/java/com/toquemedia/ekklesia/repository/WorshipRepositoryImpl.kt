package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.dao.WorshipDao
import com.toquemedia.ekklesia.model.PostType
import com.toquemedia.ekklesia.model.WorshipEntity
import com.toquemedia.ekklesia.model.interfaces.WorshipRepository
import com.toquemedia.ekklesia.services.PostService
import com.toquemedia.ekklesia.services.UserService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class WorshipRepositoryImpl @Inject constructor(
    private val dao: WorshipDao,
    private val postService: PostService,
    private val user: UserService
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
}