package com.toquemedia.ekklesia.services

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.toquemedia.ekklesia.extension.convertToString
import com.toquemedia.ekklesia.model.CommentType
import kotlinx.coroutines.tasks.await
import retrofit2.http.GET
import java.util.Date
import javax.inject.Inject

data class OurmannaResponse(
    val verse: Verse
)

data class Verse(
    val details: Details
)

data class Details(
    val reference: String
)

data class StatsVerseOfDay(
    val createdAt: Date = Date(),
    val shares: Long = 0,
    val likes: Long = 0,
    val commentsList: List<CommentType> = emptyList(),
    val id: String = Date().convertToString()
)

interface OurmannaService {
    @GET("api/v1/get?format=json&order=daily")
    suspend fun getVerseOfDay(): OurmannaResponse
}

class VerseOfDayService @Inject constructor(
    val db: FirebaseFirestore
) {

    private val collection = db.collection("verseOfDay")
    private val currentDay = Date().convertToString()

    suspend fun getVerseOfDay(): StatsVerseOfDay {
        collection.document(currentDay)
            .get()
            .await()
            .toObject(StatsVerseOfDay::class.java)
            ?.let {
                return it
            } ?: collection.document(StatsVerseOfDay().id).set(StatsVerseOfDay()).await()
        return StatsVerseOfDay()
    }

    suspend fun addComment(comment: CommentType) {
        db.collection("${currentDay}/comments").add(comment).await()
    }

    suspend fun saveLikedVerseOfDay(isForLike: Boolean) {
        collection.document(currentDay).update("likes", if (isForLike) FieldValue.increment(-1) else FieldValue.increment(1)).await()
    }

    suspend fun saveSharedVerseOfDay() {
        collection.document(currentDay).update("shares", FieldValue.increment(1)).await()
    }
}