package com.toquemedia.ekklesia.repository

import com.toquemedia.ekklesia.dao.NoteDao
import com.toquemedia.ekklesia.model.interfaces.NoteRepository
import com.toquemedia.ekklesia.model.NoteType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {

    override suspend fun addNoteToVerse(note: NoteType) = dao.save(note)
    override fun getAllNotes(): Flow<List<NoteType>> = dao.getAll()
}