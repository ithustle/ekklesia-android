package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.dao.DevocionalDao
import com.toquemedia.ekklesia.model.DevocionalType
import com.toquemedia.ekklesia.model.interfaces.DevocionalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DevocionalRepositoryImpl @Inject constructor(
    private val dao: DevocionalDao
) : DevocionalRepository {

    override suspend fun saveDevocional(devocional: DevocionalType) = dao.insert(devocional)
    override fun getAllDevocional(): Flow<List<DevocionalType>> = dao.getAll()
}