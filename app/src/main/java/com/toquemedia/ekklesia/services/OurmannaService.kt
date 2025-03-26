package com.toquemedia.ekklesia.services

import retrofit2.http.GET

data class OurmannaResponse(
    val verse: Verse
)

data class Verse(
    val details: Details
)

data class Details(
    val reference: String
)

interface OurmannaService {
    @GET("api/v1/get?format=json&order=daily")
    suspend fun getVerseOfDay(): OurmannaResponse
}