package com.toquemedia.ekklesia.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.toquemedia.ekklesia.model.NoteType

@Database(
    entities = [NoteType::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun NoteDao(): NoteDao
}