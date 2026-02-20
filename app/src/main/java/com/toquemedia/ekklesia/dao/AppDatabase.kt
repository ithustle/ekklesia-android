package com.toquemedia.ekklesia.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.toquemedia.ekklesia.model.MessageEntity
import com.toquemedia.ekklesia.model.NoteEntity
import com.toquemedia.ekklesia.model.WorshipEntity

@Database(
    entities = [NoteEntity::class, WorshipEntity::class, MessageEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun NoteDao(): NoteDao
    abstract fun WorshipDao(): WorshipDao
    abstract fun MessageDao() : MessageDao
}