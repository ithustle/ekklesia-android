package com.toquemedia.seedfy.model

data class StudyPlan(
    val title: String,
    val durationDays: Int,
    val dailyReadings: List<DailyReading>
)