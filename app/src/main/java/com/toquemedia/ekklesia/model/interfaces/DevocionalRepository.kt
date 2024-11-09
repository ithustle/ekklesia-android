package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.DevocionalType
import kotlinx.coroutines.flow.Flow

interface DevocionalRepository {
    suspend fun saveDevocional(devocional: DevocionalType)
    fun getAllDevocional(): Flow<List<DevocionalType>>
}