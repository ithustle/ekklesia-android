package com.toquemedia.ekklesia.services

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import okio.buffer
import okio.source
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path
import java.io.File


data class BunnyServiceResponse(
    val HttpCode: String?,
    val Message: String?
)

interface BunnyService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/octet-stream",
        "AccessKey: a0a4e00f-3d78-4506-9bdb92628370-7535-4001"
    )
    @PUT("ekklesia/{filename}")
    suspend fun uploadVideo(
        @Path("filename") filename: String,
        @Body file: RequestBody
    ): BunnyServiceResponse
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