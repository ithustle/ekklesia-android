package com.toquemedia.ekklesia.services

import com.toquemedia.ekklesia.dao.FirebaseFirestoreDao
import com.toquemedia.ekklesia.model.NoteEntity

class NoteService : FirebaseFirestoreDao() {

    private val collection: String = "notes"

    suspend fun shareNote(note: NoteEntity) {
        save(this.collection, note)
    }
}