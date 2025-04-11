package com.toquemedia.ekklesia.routes
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen() {

    @Serializable
    object AuthScreenGraph: Screen()

    @Serializable
    object HomeScreenGraph: Screen()

    @Serializable
    object BibleScreenGraph: Screen()

    @Serializable
    object CommunityScreenGraph: Screen()

    @Serializable
    object Login: Screen()

    @Serializable
    object Home: Screen()

    @Serializable
    object Bible: Screen()

    @Serializable
    object Communities: Screen()

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
    data class Chat(
        val communityId: String
    ): Screen()

    @Serializable
    data class CommentPost(
        val postId: String
    ): Screen()

    @Serializable
    data class CommunityFeed(
        val communityId: String,
        val communityName: String
    ): Screen()
}