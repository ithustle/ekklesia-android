package com.toquemedia.ekklesia.model.interfaces
import com.toquemedia.ekklesia.model.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNoteToVerse(note: NoteEntity)
    suspend fun shareNote(note: NoteEntity)
    fun getAllNotes(): Flow<List<NoteEntity>>
}