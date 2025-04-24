package com.toquemedia.ekklesia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "worships")
data class WorshipEntity(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "book_name") val bookName: String = "",
    @ColumnInfo(name = "chapter") val chapter: Int = 0,
    @ColumnInfo(name = "versicle") val versicle: Int = 0,
    @ColumnInfo(name = "verse") val verse: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "worship") val worship: String = "",
    @ColumnInfo(name = "background_color") val backgroundColor: String = "",
    @ColumnInfo(name = "video") val video: String? = null
)
