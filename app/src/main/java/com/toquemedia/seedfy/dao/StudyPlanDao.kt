package com.toquemedia.seedfy.dao

import android.content.Context
import com.toquemedia.seedfy.model.StudyPlan
import com.toquemedia.seedfy.model.interfaces.EkklesiaDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class StudyPlanDao(context: Context) : EkklesiaDataStore(context, "ekklesia_study_plans") {

    private val json = Json { 
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val _studyPlansFlow = MutableStateFlow<List<StudyPlan>>(emptyList())
    var studyPlans: MutableStateFlow<List<StudyPlan>> = _studyPlansFlow

    suspend fun saveStudyPlan(studyPlan: StudyPlan) {
        val planJson = json.encodeToString(studyPlan)
        val planId = generatePlanId(studyPlan.title)
        super.savePreference(planId, planJson)
        updateStudyPlansFlow()
    }

    suspend fun getStudyPlan(title: String): StudyPlan? {
        val planId = generatePlanId(title)
        val planJson = super.getPreference(planId)
        return planJson?.let { 
            try {
                json.decodeFromString<StudyPlan>(it)
            } catch (e: Exception) {
                null
            }
        }
    }

    fun getAllStudyPlans(): List<StudyPlan> {
        val preferences = super.getPreferences()
        val allPlans = mutableListOf<StudyPlan>()
        
        preferences.map { prefs ->
            prefs.asMap().values.forEach { value ->
                try {
                    val studyPlan = json.decodeFromString<StudyPlan>(value.toString())
                    allPlans.add(studyPlan)
                } catch (e: Exception) {
                    throw e
                }
            }
        }
        
        return allPlans
    }

    suspend fun deleteStudyPlan(title: String) {
        val planId = generatePlanId(title)
        super.clearPreference(planId)
        updateStudyPlansFlow()
    }

    suspend fun updateStudyPlan(oldTitle: String, newStudyPlan: StudyPlan) {
        if (oldTitle != newStudyPlan.title) {
            deleteStudyPlan(oldTitle)
        }
        saveStudyPlan(newStudyPlan)
    }

    suspend fun studyPlanExists(title: String): Boolean {
        return getStudyPlan(title) != null
    }

    fun getStudyPlansFlow(): Flow<List<StudyPlan>> {
        return super.getPreferences().map { preferences ->
            val plans = mutableListOf<StudyPlan>()
            preferences.asMap().values.forEach { value ->
                try {
                    val studyPlan = json.decodeFromString<StudyPlan>(value.toString())
                    plans.add(studyPlan)
                } catch (e: Exception) {
                    throw e
                }
            }
            plans.sortedBy { it.title }
        }
    }

    private fun generatePlanId(title: String): String {
        return "study_plan_${title.lowercase().replace(" ", "_")}"
    }

    private fun updateStudyPlansFlow() {
        _studyPlansFlow.value = getAllStudyPlans()
    }
}

// Código gerado pela IA