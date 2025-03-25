package com.toquemedia.ekklesia.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.toquemedia.ekklesia.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun save(vararg note: NoteEntity)

    @Query("SELECT * FROM notes")
    fun getAll(): Flow<List<NoteEntity>>

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}