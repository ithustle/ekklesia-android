package com.toquemedia.ekklesia.model.interfaces

import android.app.Activity
import com.toquemedia.ekklesia.model.UserType

interface AuthRepository {
    suspend fun googleSignIn(activityContext: Activity): UserType?
    fun getCurrentUser(): UserType?
    suspend fun signOut()
    suspend fun getCommunitiesId(): List<String>
}