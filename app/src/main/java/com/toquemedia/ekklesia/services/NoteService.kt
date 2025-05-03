package com.toquemedia.ekklesia.services

import com.google.firebase.firestore.FirebaseFirestore
import com.toquemedia.ekklesia.dao.FirebaseFirestoreDao
import com.toquemedia.ekklesia.model.NoteEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class NoteService @Inject constructor(
    private val db: FirebaseFirestore
) {

    private val collection: String = "notes"

    suspend fun shareNote(note: NoteEntity) {
        db.collection(collection).add(note).await()
    }
}