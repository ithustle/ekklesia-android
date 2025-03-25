package com.toquemedia.ekklesia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "book_name") val bookName: String,
    @ColumnInfo(name = "chapter") val chapter: Int,
    @ColumnInfo(name = "versicle") val versicle: Int,
    @ColumnInfo(name = "verse") val verse: String,
    @ColumnInfo(name = "note") val note: String
)