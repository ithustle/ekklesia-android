package com.toquemedia.ekklesia.ui.screens.biblePlan

import com.toquemedia.ekklesia.model.StudyPlan

data class BiblePlanUiState(
    val biblePlan: StudyPlan? = null,
    val biblePlans: List<StudyPlan> = emptyList(),
    val loadingPlans: Boolean = true
)
