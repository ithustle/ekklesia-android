package com.toquemedia.seedfy.repository

import android.app.Activity
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.model.interfaces.AuthRepository
import com.toquemedia.seedfy.services.UserService
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val service: UserService,
) : AuthRepository {

    override suspend fun googleSignIn(activityContext: Activity): UserType? = service.googleSignIn(activityContext)
    override fun getCurrentUser(): UserType? = service.getCurrentUser()
    override suspend fun signOut() = service.signOut()
    override suspend fun getCommunitiesId(): List<String> = service.getCommunitiesIn()
}