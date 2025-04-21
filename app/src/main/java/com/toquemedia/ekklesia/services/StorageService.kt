package com.toquemedia.ekklesia.services

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storageMetadata
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageService @Inject constructor(
    @ApplicationContext private val context: Context,
    storage: FirebaseStorage
) {

    private val storageRef = storage.reference

    suspend fun uploadImage(communityId: String, imageUri: Uri?): Uri? {
        if (imageUri == null) {
            throw IllegalArgumentException("Imagem não pode ser nula")
        }

        val metadata = storageMetadata {
            contentType = context.contentResolver.getType(imageUri) ?: "image/jpeg"
        }

        val imageRef = storageRef.child("communities/${communityId}.jpg")
        val uploadTask = imageRef.putFile(imageUri, metadata).await()

        try {
            if (uploadTask.task.isSuccessful) {
                return imageRef.downloadUrl.await()
            } else {
                Log.e("StorageService", "Upload failed: ${uploadTask.task.exception?.message}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}