package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.dao.WorshipDao
import com.toquemedia.ekklesia.model.WorshipEntity
import com.toquemedia.ekklesia.model.interfaces.WorshipRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorshipRepositoryImpl @Inject constructor(
    private val dao: WorshipDao
) : WorshipRepository {

    override suspend fun saveWorship(worship: WorshipEntity) = dao.insert(worship)
    override suspend fun updateWorship(worship: WorshipEntity) = dao.update(worship)
    override suspend fun deleteWorship(id: String) = dao.deleteWorship(id)
    override fun getAllWorships(): Flow<List<WorshipEntity>> = dao.getAll()

    override suspend fun shareToCommunity(worship: WorshipEntity) {
        TODO("Not yet implemented")
    }
}