package com.toquemedia.ekklesia.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.toquemedia.ekklesia.model.NoteType
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun save(vararg note: NoteType)

    @Query("SELECT * FROM notetype")
    fun getAll(): Flow<List<NoteType>>

    @Delete
    suspend fun deleteNote(note: NoteType)
}