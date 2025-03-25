package com.toquemedia.ekklesia.routes
import android.os.Parcelable
import com.toquemedia.ekklesia.model.CommunityType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object Login: Screen()

    @Serializable
    data class Chapters(val bookName: String) : Screen()

    @Serializable
    data class Verses(
        val bookName: String?,
        val chapterNumber: String
    ): Screen()

    @Serializable
    object CreateCommunity: Screen()

    @Serializable
    object Profile: Screen()

    @Serializable
    @Parcelize
    data class Chat(
        val community: CommunityType
    ): Screen(), Parcelable

    @Serializable
    @Parcelize
    data class CommunityFeed(
        val community: CommunityType
    ): Screen(), Parcelable
}