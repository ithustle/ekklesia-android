package com.toquemedia.seedfy.ui.screens.biblePlan

import com.toquemedia.seedfy.model.StudyPlan

data class BiblePlanUiState(
    val biblePlan: StudyPlan? = null,
    val biblePlans: List<StudyPlan> = emptyList(),
    val loadingPlans: Boolean = true
)
