package com.toquemedia.ekklesia.services

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import okio.buffer
import okio.source
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.io.File

data class BunnyServiceResponse(
    val success: Boolean,
    val message: String,
    val statusCode: Int
)

data class CreateVideoRequest(
    val title: String
)

data class BunnyServiceResponseBody(
    val guid: String,
)

interface BunnyService {
    @Headers(
        "Content-Type: application/octet-stream",
        "AccessKey: c8bc6189-cb0e-4626-9070f90376fe-adfb-4894"
    )
    @PUT("videos/{videoId}")
    suspend fun uploadVideo(
        @Path("videoId") videoId: String,
        @Body file: RequestBody
    ): BunnyServiceResponse

    @Headers(
        "Accept: application/json",
        "AccessKey: c8bc6189-cb0e-4626-9070f90376fe-adfb-4894"
    )
    @POST("videos")
    suspend fun createVideo(@Body title: CreateVideoRequest): BunnyServiceResponseBody
}

class ProgressRequestBody(
    private val file: File,
    private val contentType: MediaType,
    private val progressListener: (Int) -> Unit
) : RequestBody() {
    override fun contentType(): MediaType = contentType

    override fun contentLength(): Long = file.length()

    override fun writeTo(sink: BufferedSink) {
        val source = file.source().buffer()
        val totalBytes = contentLength()
        var bytesWritten: Long = 0

        val buffer = Buffer()
        var read: Long

        while (source.read(buffer, 2048).also { read = it } != -1L) {
            sink.write(buffer, read)
            bytesWritten += read
            val progress = (100 * bytesWritten / totalBytes).toInt()
            progressListener(progress)
        }
    }
}