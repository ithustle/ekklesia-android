package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.dao.DevocionalDao
import com.toquemedia.ekklesia.model.DevocionalEntity
import com.toquemedia.ekklesia.model.interfaces.DevocionalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DevocionalRepositoryImpl @Inject constructor(
    private val dao: DevocionalDao
) : DevocionalRepository {

    override suspend fun saveDevocional(devocional: DevocionalEntity) = dao.insert(devocional)
    override fun getAllDevocional(): Flow<List<DevocionalEntity>> = dao.getAll()
}