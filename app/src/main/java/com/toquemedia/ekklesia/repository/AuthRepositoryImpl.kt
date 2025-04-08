package com.toquemedia.ekklesia.repository

import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.model.interfaces.AuthRepository
import com.toquemedia.ekklesia.services.UserService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val service: UserService,
) : AuthRepository {

    override suspend fun googleSignIn(activityContext: Activity): UserType? = service.googleSignIn(activityContext)
    override fun getCurrentUser(): UserType? = service.getCurrentUser()
    override suspend fun signOut() = service.signOut()
}