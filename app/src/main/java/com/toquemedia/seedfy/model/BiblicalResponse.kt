package com.toquemedia.seedfy.model

import kotlinx.serialization.Serializable

@Serializable
data class BiblicalResponse(
    val query: String,
    val verses: List<VerseType>,
    val biblicalTeaching: String,
    val studyPlan: StudyPlan,
    val meditationQuestion: String
)

