package com.toquemedia.seedfy.extension

import android.content.Context
import android.net.Uri
import android.graphics.Bitmap
import java.io.File

fun Bitmap.toUri(context: Context): Uri? {
    val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.png")
    return try {
        val outputStream = file.outputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()
        Uri.fromFile(file)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}