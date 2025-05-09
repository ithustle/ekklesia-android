package com.toquemedia.seedfy.model.interfaces

import android.app.Activity
import com.toquemedia.seedfy.model.UserType

interface AuthRepository {
    suspend fun googleSignIn(activityContext: Activity): UserType?
    fun getCurrentUser(): UserType?
    suspend fun signOut()
    suspend fun getCommunitiesId(): List<String>
    suspend fun registerOnboarding()
    suspend fun getRegisteredOnboarding(): Boolean
}