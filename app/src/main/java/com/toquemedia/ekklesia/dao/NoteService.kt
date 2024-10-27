package com.toquemedia.ekklesia.dao

import com.toquemedia.ekklesia.model.NoteType

class NoteService : FirebaseFirestoreDao() {

    private val collection: String = "notes"

    fun shareNote(note: NoteType) {
        save(this.collection, note)
    }
}