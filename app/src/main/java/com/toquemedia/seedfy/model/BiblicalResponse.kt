package com.toquemedia.seedfy.model

data class BiblicalResponse(
    val query: String,
    val verses: List<VerseType>,
    val biblicalTeaching: String,
    val studyPlan: StudyPlan,
    val meditationQuestion: String
)

