package com.toquemedia.ekklesia.services

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storageMetadata
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    fun uploadVideo(userEmail: String, videoPath: Uri): Flow<Pair<Float, Uri?>> {
        return callbackFlow {
            val metadata = storageMetadata {
                contentType = context.contentResolver.getType(videoPath) ?: "video/mp4"
            }

            val videoRef = storageRef.child("users/${userEmail}/${System.currentTimeMillis()}.mp4")
            val uploadTask = videoRef.putFile(videoPath, metadata)

            send(Pair(0f, null))

            val progressListener = uploadTask.addOnProgressListener { snapshot ->
                val progress = snapshot.bytesTransferred.toFloat() / snapshot.totalByteCount.toFloat()
                trySend(Pair(progress, null))
            }

            val successListener = uploadTask.addOnSuccessListener {
                println("Upload concluído com sucesso!")
            }

            val failureListener = uploadTask.addOnFailureListener { exception ->
                Log.e("StorageService", "Upload failed: ${exception.message}")
                trySend(Pair(0f, null))
                close(exception)
            }

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                videoRef.downloadUrl
            }.addOnSuccessListener { downloadUri ->
                trySend(Pair(1f, downloadUri))
                close()
            }.addOnFailureListener { exception ->
                close(exception)
            }

            awaitClose {
                progressListener.cancel()
                successListener.cancel()
                failureListener.cancel()
            }
        }
    }
}