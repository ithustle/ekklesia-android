package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.DevocionalEntity
import kotlinx.coroutines.flow.Flow

interface DevocionalRepository {
    suspend fun saveDevocional(devocional: DevocionalEntity)
    fun getAllDevocional(): Flow<List<DevocionalEntity>>
}