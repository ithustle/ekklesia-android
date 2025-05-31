package com.toquemedia.seedfy.model

import kotlinx.serialization.Serializable

@Serializable
data class StudyPlan(
    val title: String,
    val durationDays: Int,
    val dailyReadings: List<DailyReading>,
    val progress: Int = 0
)