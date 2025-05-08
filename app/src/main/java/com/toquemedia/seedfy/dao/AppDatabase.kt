package com.toquemedia.seedfy.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.toquemedia.seedfy.model.MessageEntity
import com.toquemedia.seedfy.model.NoteEntity
import com.toquemedia.seedfy.model.WorshipEntity

@Database(
    entities = [NoteEntity::class, WorshipEntity::class, MessageEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun NoteDao(): NoteDao
    abstract fun WorshipDao(): WorshipDao
    abstract fun MessageDao() : MessageDao
}