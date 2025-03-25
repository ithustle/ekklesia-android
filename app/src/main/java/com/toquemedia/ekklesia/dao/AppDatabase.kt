package com.toquemedia.ekklesia.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.toquemedia.ekklesia.model.CommunityEntity
import com.toquemedia.ekklesia.model.DevocionalEntity
import com.toquemedia.ekklesia.model.MessageEntity
import com.toquemedia.ekklesia.model.NoteEntity

@Database(
    entities = [NoteEntity::class, DevocionalEntity::class, CommunityEntity::class, MessageEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun NoteDao(): NoteDao
    abstract fun DevocionalDao(): DevocionalDao
    abstract fun CommunityDao() : CommunityDao
    abstract fun MessageDao() : MessageDao
}