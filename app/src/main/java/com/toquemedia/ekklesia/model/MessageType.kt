package com.toquemedia.ekklesia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val message: String,
    @ColumnInfo val timestamp: Int,
    @ColumnInfo(name = "sender_id") val senderId: String,
    @ColumnInfo val status: String,
    @ColumnInfo val type: String,
    @ColumnInfo(name = "message_replied_id") val messageRepliedId: String,
    @ColumnInfo val communityId: String
)

data class MessageType(
    val id: String,
    val message: String,
    val timestamp: Int,
    val senderId: String,
    val status: String,
    val type: String,
    val messageRepliedId: String,
    val communityId: String
)