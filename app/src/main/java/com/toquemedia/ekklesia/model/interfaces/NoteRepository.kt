package com.toquemedia.ekklesia.model.interfaces
import com.toquemedia.ekklesia.model.NoteType
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNoteToVerse(note: NoteType)
    suspend fun shareNote(note: NoteType)
    fun getAllNotes(): Flow<List<NoteType>>
}