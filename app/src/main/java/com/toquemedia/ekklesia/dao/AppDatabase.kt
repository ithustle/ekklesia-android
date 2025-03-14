package com.toquemedia.ekklesia.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.model.DevocionalType
import com.toquemedia.ekklesia.model.NoteType
import com.toquemedia.ekklesia.services.CommunityService

@Database(
    entities = [NoteType::class, DevocionalType::class, CommunityType::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun NoteDao(): NoteDao
    abstract fun DevocionalDao(): DevocionalDao
    abstract fun CommunityDao() : CommunityDao
}