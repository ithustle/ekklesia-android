package com.toquemedia.seedfy.ui.screens.biblePlan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.seedfy.dao.StudyPlanDao
import com.toquemedia.seedfy.model.StudyPlan
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BiblePlanViewModel @Inject constructor(
    private val plan: StudyPlanDao
): ViewModel() {
    private val _uiState = MutableStateFlow(BiblePlanUiState())
    val uiState = _uiState.asStateFlow()

    init {
        println("BiblePlanViewModel")
        this.getBiblePlans()
    }

    fun saveBiblePlan(biblePlan: StudyPlan) {
        viewModelScope.launch { plan.saveStudyPlan(biblePlan) }
    }

    fun updateBiblePlan(newStudyPlan: StudyPlan, checked: Boolean, day: Int) {
        viewModelScope.launch {
            try {

                val updatedDailyRead = newStudyPlan.dailyReadings.map { dailyRead ->
                    if (dailyRead.day == day) {
                        dailyRead.copy(isCompleted = checked)
                    } else {
                        dailyRead
                    }
                }

                val countingCompletedDailyRead = updatedDailyRead.count { it.isCompleted }

                val updatedPlan = newStudyPlan.copy(
                    progress = countingCompletedDailyRead,
                    dailyReadings = updatedDailyRead
                )

                plan.updateStudyPlan(updatedPlan.title, updatedPlan)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun getBiblePlan(title: String) {
        val plan = _uiState.value.biblePlans.find { it.title == title }
        _uiState.update { it.copy(biblePlan = plan) }
    }

    private fun getBiblePlans() {
        viewModelScope.launch {
            plan.getStudyPlansFlow().collect { biblePlans ->
                _uiState.update { it.copy(biblePlans = biblePlans, loadingPlans = false) }
            }
        }
    }
}