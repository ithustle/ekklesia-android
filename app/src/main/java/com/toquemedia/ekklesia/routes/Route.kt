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
        val chapterNumber: Int
    ): Screen()

    @Serializable
    object CreateCommunity: Screen()

    @Serializable
    data class CreateDevocional(
        val bookName: String?,
        val chapterNumber: String,
        val versicle: Int,
        val verse: String
    ): Screen()

    @Serializable
    object Profile: Screen()

    @Serializable
    data class Chat(
        val communityId: String
    ): Screen()

    @Serializable
    data class CommentPost(
        val postId: String,
        val communityId: String
    ): Screen()

    @Serializable
    data class CommunityFeed(
        val communityId: String,
        val communityName: String
    ): Screen()

    @Serializable
    data class NoteVerse(
        val bookName: String?,
        val chapterNumber: String,
        val verse: String,
        val versicle: Int,
    ): Screen()
}