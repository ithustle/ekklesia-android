package com.toquemedia.seedfy.model.interfaces
import com.toquemedia.seedfy.model.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNoteToVerse(note: NoteEntity)
    suspend fun shareNote(note: NoteEntity)
    fun getAllNotes(): Flow<List<NoteEntity>>
}