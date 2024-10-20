package com.toquemedia.ekklesia.model.interfaces
import com.toquemedia.ekklesia.model.NoteType

interface NoteRepository {
    suspend fun addNoteToVerse(note: NoteType)
    suspend fun getAllNotes(): List<NoteType>
}