package com.toquemedia.seedfy.repository

import com.toquemedia.seedfy.dao.NoteDao
import com.toquemedia.seedfy.services.NoteService
import com.toquemedia.seedfy.model.interfaces.NoteRepository
import com.toquemedia.seedfy.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao,
    private val service: NoteService
) : NoteRepository {

    override suspend fun addNoteToVerse(note: NoteEntity) = dao.save(note)
    override suspend fun shareNote(note: NoteEntity) {
        this.addNoteToVerse(note)
        service.shareNote(note)
    }
    override fun getAllNotes(): Flow<List<NoteEntity>> = dao.getAll()
}