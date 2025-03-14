package com.toquemedia.ekklesia.services

import com.toquemedia.ekklesia.dao.FirebaseFirestoreDao
import com.toquemedia.ekklesia.model.NoteType

class NoteService : FirebaseFirestoreDao() {

    private val collection: String = "notes"

    suspend fun shareNote(note: NoteType) {
        save(this.collection, note)
    }
}