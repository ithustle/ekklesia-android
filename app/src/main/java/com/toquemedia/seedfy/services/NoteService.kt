package com.toquemedia.seedfy.services

import com.google.firebase.firestore.FirebaseFirestore
import com.toquemedia.seedfy.model.NoteEntity
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