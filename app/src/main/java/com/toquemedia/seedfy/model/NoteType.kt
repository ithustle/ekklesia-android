package com.toquemedia.seedfy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "notes")
@Serializable
data class NoteEntity(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "book_name") val bookName: String = "",
    @ColumnInfo(name = "chapter") val chapter: Int = 0,
    @ColumnInfo(name = "versicle") val versicle: Int = 0,
    @ColumnInfo(name = "verse") val verse: String = "",
    @ColumnInfo(name = "note") val note: String = ""
)