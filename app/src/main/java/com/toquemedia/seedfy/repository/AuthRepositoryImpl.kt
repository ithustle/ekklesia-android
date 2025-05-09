package com.toquemedia.seedfy.repository

import android.app.Activity
import com.google.gson.reflect.TypeToken
import com.toquemedia.seedfy.dao.AppCacheDao
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.model.interfaces.AuthRepository
import com.toquemedia.seedfy.services.UserService
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val service: UserService,
    private val cache: AppCacheDao
) : AuthRepository {

    override suspend fun googleSignIn(activityContext: Activity): UserType? = service.googleSignIn(activityContext)
    override fun getCurrentUser(): UserType? = service.getCurrentUser()
    override suspend fun signOut() = service.signOut()
    override suspend fun getCommunitiesId(): List<String> = service.getCommunitiesIn()
    override suspend fun registerOnboarding() = cache.saveCache<Boolean>("onboarding", true)
    override suspend fun getRegisteredOnboarding(): Boolean =
        cache.getCache<Boolean>("onboarding", object : TypeToken<Boolean>() {}) == true
}