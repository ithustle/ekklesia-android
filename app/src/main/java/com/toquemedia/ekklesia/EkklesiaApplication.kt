package com.toquemedia.ekklesia

import android.app.Application
import android.util.Base64
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.IntegrityTokenRequest
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import java.security.SecureRandom


@HiltAndroidApp
class EkklesiaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Firebase.appCheck.installAppCheckProviderFactory(
            if (true) DebugAppCheckProviderFactory.getInstance() else PlayIntegrityAppCheckProviderFactory.getInstance(),
        )

        val integrityManager = IntegrityManagerFactory.create(applicationContext)
        val nonce = generateNonce()
        println("Generated nonce: $nonce")
        val request = integrityManager.requestIntegrityToken(
            IntegrityTokenRequest.builder().setNonce(nonce).build()
        )
        request.addOnSuccessListener { response ->
            val integrityToken = response.token()
            println("Integrity token: $integrityToken")
        }.addOnFailureListener { exception ->
            println("Integrity error: ${exception.message}")
            println("Integrity error details: ${exception.cause}")
        }
    }

    private fun generateNonce(): String {
        val secureRandom = SecureRandom()
        val bytes = ByteArray(16) // 16 bytes minimum
        secureRandom.nextBytes(bytes)
        return Base64.encodeToString(bytes, Base64.URL_SAFE or Base64.NO_WRAP)
    }
}