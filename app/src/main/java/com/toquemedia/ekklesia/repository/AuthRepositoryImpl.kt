package com.toquemedia.ekklesia.repository

import android.app.Activity
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.gson.reflect.TypeToken
import com.toquemedia.ekklesia.dao.AppCacheDao
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.model.interfaces.AuthRepository
import com.toquemedia.ekklesia.services.UserService
import com.toquemedia.ekklesia.utils.PhoneAuthResult
import com.toquemedia.ekklesia.utils.PhoneAuthVerificationResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val service: UserService,
    private val cache: AppCacheDao
) : AuthRepository {

    override suspend fun googleSignIn(activityContext: Activity): UserType? = service.googleSignIn(activityContext)
    override suspend fun saveMyNotes(myNote: String) {
        cache.saveCache("myNotes", myNote)
        service.saveMyNotes(myNote)
    }

    override suspend fun getMyNotes(): String =
        cache.getCache("myNotes", object : TypeToken<String>() {}).toString()

    override fun getCurrentUser(): UserType? = service.getCurrentUser()
    override suspend fun signOut() = service.signOut()
    override suspend fun getCommunitiesId(): List<String> = service.getCommunitiesIn()
    override suspend fun registerOnboarding() = cache.saveCache<Boolean>("onboarding", true)
    override suspend fun getRegisteredOnboarding(): Boolean =
        cache.getCache<Boolean>("onboarding", object : TypeToken<Boolean>() {}) == true

    fun sendPhoneVerificationCode(
        phoneNumber: String,
        activity: Activity
    ): Flow<PhoneAuthResult> {
        return service.sendPhoneVerificationCode(phoneNumber, activity)
    }

    suspend fun updateUserProfile(
        userId: String,
        displayName: String
    ): UserType? {
        return service.updateUserProfile(userId, displayName)
    }

    suspend fun verifyPhoneCode(
        verificationId: String,
        code: String
    ): PhoneAuthVerificationResult {
        return service.verifyPhoneCode(verificationId, code)
    }

    suspend fun signInWithCredential(credential: PhoneAuthCredential): PhoneAuthVerificationResult {
        return service.signInWithPhoneCredential(credential)
    }

    fun resendPhoneCode(
        phoneNumber: String,
        activity: Activity,
        token: PhoneAuthProvider.ForceResendingToken
    ): Flow<PhoneAuthResult> {
        return service.resendPhoneCode(phoneNumber, activity, token)
    }
}