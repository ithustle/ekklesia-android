package com.toquemedia.ekklesia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "communities")
data class CommunityType(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "community_name") val communityName: String,
    @ColumnInfo(name = "community_description") val communityDescription: String,
    @ColumnInfo(name = "community_image") val communityImage: String,
)