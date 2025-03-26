package com.toquemedia.ekklesia.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity(tableName = "communities")
data class CommunityEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "community_name") val communityName: String,
    @ColumnInfo(name = "community_description") val communityDescription: String,
    @ColumnInfo(name = "members") val members: Int,
    @ColumnInfo(name = "community_image") var communityImage: String,
    @ColumnInfo(name = "email") var email: String,
)

@Parcelize
@Serializable
data class CommunityType(
    val id: String,
    val communityName: String,
    val communityDescription: String,
    val members: Int,
    val communityImage: String,
    val email: String,
): Parcelable
