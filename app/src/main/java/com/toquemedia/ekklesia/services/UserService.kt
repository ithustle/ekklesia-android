package com.toquemedia.ekklesia.services

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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.dao.LikeDao
import com.toquemedia.ekklesia.model.UserType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserService @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val dao: LikeDao
) {

    private val collection = "users"

    suspend fun saveCommunityIn(id: String) {
        val user = this.getCurrentUser()

        try {
            db.collection(collection).document(user?.email.toString()).update("communitiesIn", FieldValue.arrayUnion(id)).await()
        } catch (e: Exception) {
            e.printStackTrace()
            val data = mapOf(
                "communitiesIn" to listOf(id),
                "postsLiked" to emptyList<String>(),
                "id" to user?.id,
            )
            db.collection(collection).document(user?.email.toString()).set(data).await()
        }
    }

    suspend fun saveLikes(id: String) {
        val user = this.getCurrentUser()

        try {
            db.collection(collection).document(user?.email.toString()).update("postsLiked", FieldValue.arrayUnion(id)).await()
        } catch (e: Exception) {
            e.printStackTrace()
            val data = mapOf(
                "postsLiked" to listOf(id),
                "communitiesIn" to emptyList<String>(),
                "id" to user?.id,
            )
            db.collection(collection).document(user?.email.toString()).set(data).await()
        }
    }

    suspend fun getCommunitiesIn(): List<String> {
        val user = this.getCurrentUser()
        val snapshot = db.collection(collection).document(user?.email.toString()).get().await()
        return snapshot.toObject(UserType::class.java)?.communitiesIn ?: emptyList()
    }

    suspend fun googleSignIn(activityContext: Activity): UserType? {
        val user = this.handleSignIn(activityContext)

        return user?.let { user ->
            val userInfo = db.collection(collection).document(user.email.toString()).get().await().toObject(UserType::class.java)

            userInfo?.let {
                it.postsLiked.forEach { postId ->
                    dao.saveLikeRegister(postId)
                }
            }

            UserType(
                id = user.uid,
                displayName = user.displayName,
                email = user.email,
                photo = user.photoUrl.toString(),
                communitiesIn = userInfo?.communitiesIn ?: emptyList(),
                postsLiked = userInfo?.postsLiked ?: emptyList()
            )
        }
    }

    fun getCurrentUser(): UserType? {
        auth.currentUser?.let { user ->
            return UserType(
                id = user.uid,
                displayName = user.displayName,
                email = user.email,
                photo = user.photoUrl.toString()
            )
        }

        return null
    }

    suspend fun signOut() {
        val credentialManager = CredentialManager.create(applicationContext)
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
        auth.signOut()
    }

    private suspend fun buildCredentialRequest(
        credentialManager: CredentialManager,
        activity: Activity
    ): GetCredentialResponse {
        val option = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(getString(applicationContext, R.string.web_client_id))
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(option)
            .build()

        return credentialManager.getCredential(request = request, context = activity)
    }

    private suspend fun handleSignIn(activityContext: Activity): FirebaseUser? {
        try {
            val credentialManager = CredentialManager.create(activityContext)
            val result = buildCredentialRequest(credentialManager, activityContext)
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