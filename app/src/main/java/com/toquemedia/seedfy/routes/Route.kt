package com.toquemedia.seedfy.routes
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
    object FirstPageOnboarding: Screen()

    @Serializable
    object SecondPageOnboarding: Screen()

    @Serializable
    object ThirdPageOnboarding: Screen()

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
    data class CreateWorship(
        val bookName: String?,
        val chapterNumber: String,
        val versicle: Int,
        val verse: String
    ): Screen()

    @Serializable
    object CreateVideo: Screen()

    @Serializable
    object Profile: Screen()

    @Serializable
    object MyWorships: Screen()

    @Serializable
    object EditWorship: Screen()

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
    data class VideoPlayer(
        val videoUrl: String
    ): Screen()

    @Serializable
    object CommunityFeed: Screen()

    @Serializable
    data class StoryCreator(
        val verse: String,
        val bookWithVersicle: String
    ): Screen()

    @Serializable
    data class StoriesNavigation(val email: String): Screen()

    @Serializable
    data class NoteVerse(
        val bookName: String?,
        val chapterNumber: String,
        val verse: String,
        val versicle: Int,
    ): Screen()
}