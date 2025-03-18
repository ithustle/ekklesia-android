package com.toquemedia.ekklesia.repository

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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auth: FirebaseAuth
) : AuthRepository {

    private val credentialManager = CredentialManager.create(context)

    override suspend fun googleSignIn(): UserType? {
        val user = this.handleSignIn()
        return user?.let { user ->
            UserType(
                id = user.uid,
                displayName = user.displayName,
                email = user.email,
                photo = user.photoUrl
            )
        }
    }

    override fun getCurrentUser(): UserType? {
        auth.currentUser?.let { user ->
            return UserType(
                id = user.uid,
                displayName = user.displayName,
                email = user.email,
                photo = user.photoUrl
            )
        }

        return null
    }
    override suspend fun signOut() {
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
        auth.signOut()
    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val option = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(getString(context, R.string.web_client_id))
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(option)
            .build()

        return credentialManager.getCredential(request = request, context = context)
    }

    private suspend fun handleSignIn(): FirebaseUser? {
        try {
            val result = buildCredentialRequest()
            val credential = result.credential

            if (
                credential is CustomCredential &&
                credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val firebaseCredential = GoogleAuthProvider.getCredential(tokenCredential.idToken, null)
                val authResult = auth.signInWithCredential(firebaseCredential).await()
                return authResult.user
            }

        } catch (e: GetCredentialException) {
            e.printStackTrace()
        }

        return null
    }
}