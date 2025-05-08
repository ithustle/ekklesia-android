package com.toquemedia.seedfy.utils.mocks

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.core.content.FileProvider
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toColorInt
import androidx.core.graphics.withTranslation
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.VerseType
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object BitmapUtil {
    private fun createVerseOfDayBitmap(
        context: Context,
        verse: VerseType?,
        widthPx: Int = 1080,
        heightPx: Int = 1920,
        logoDrawableId: Int = R.drawable.icone_seedfy
    ): Bitmap {
        val bitmap = createBitmap(widthPx, heightPx)
        val canvas = android.graphics.Canvas(bitmap)

        val gradientPaint = Paint()

        val gradient = android.graphics.LinearGradient(
            0f, 0f, widthPx.toFloat(), heightPx.toFloat(), // Diagonal
            intArrayOf(
                "#633B48".toColorInt(),
                "#9B3A6A".toColorInt(),
                "#D94A8A".toColorInt(),
                "#FFA3C4".toColorInt()
            ),
            null,
            android.graphics.Shader.TileMode.CLAMP
        )
        gradientPaint.shader = gradient

        canvas.drawRect(0f, 0f, widthPx.toFloat(), heightPx.toFloat(), gradientPaint)

        val textPaint = Paint().apply {
            isAntiAlias = true
            color = Color.WHITE
            textAlign = Paint.Align.LEFT
        }

        textPaint.apply {
            textSize = widthPx * 0.04f
            alpha = 255
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        }
        canvas.drawText("Versículo do dia", widthPx * 0.04f, heightPx * 0.05f, textPaint)

        textPaint.apply {
            textSize = widthPx * 0.05f
            alpha = 255
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        }
        canvas.drawText("${verse?.bookName} ${verse?.chapter}:${verse?.versicle}", widthPx * 0.04f, heightPx * 0.08f, textPaint)

        textPaint.apply {
            textSize = widthPx * 0.06f
            alpha = 255
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        }

        val verseTextLayout = StaticLayout.Builder.obtain(
            verse?.text.toString(),
            0,
            verse?.text.toString().length,
            TextPaint(textPaint),
            (widthPx * 0.92f).toInt()
        )
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(8f, 1f)
            .setIncludePad(true)
            .build()

        val textHeight = verseTextLayout.height
        val centerY = (heightPx - textHeight) / 2f

        canvas.withTranslation(widthPx * 0.04f, centerY) {
            verseTextLayout.draw(this)
        }

        try {
            val logo = BitmapFactory.decodeResource(context.resources, logoDrawableId)
            val logoWidth = widthPx * 0.15f
            val scaleFactor = logoWidth / logo.width
            val logoHeight = logo.height * scaleFactor

            val logoRect = RectF(
                (widthPx - logoWidth) / 2,
                heightPx * 0.9f,
                (widthPx + logoWidth) / 2,
                heightPx * 0.9f + logoHeight
            )

            canvas.drawBitmap(logo, null, logoRect, null)
        } catch (e: Exception) {
            e.printStackTrace()
            val paint = Paint()
            paint.color = Color.WHITE
            paint.style = Paint.Style.FILL
            paint.isAntiAlias = true

            val centerX = widthPx / 2f
            val centerY = heightPx * 0.9f
            val radius = widthPx * 0.03f

            canvas.drawCircle(centerX, centerY, radius, paint)

            paint.style = Paint.Style.STROKE
            paint.strokeWidth = radius * 0.2f

            canvas.drawLine(
                centerX,
                centerY - radius * 0.6f,
                centerX,
                centerY + radius * 0.6f,
                paint
            )
            canvas.drawLine(
                centerX - radius * 0.6f,
                centerY,
                centerX + radius * 0.6f,
                centerY,
                paint
            )

            val dotRadius = radius * 0.15f
            paint.style = Paint.Style.FILL
            canvas.drawCircle(centerX, centerY - radius * 1.2f, dotRadius, paint)
            canvas.drawCircle(centerX + radius * 1.2f, centerY, dotRadius, paint)
            canvas.drawCircle(centerX, centerY + radius * 1.2f, dotRadius, paint)
            canvas.drawCircle(centerX - radius * 1.2f, centerY, dotRadius, paint)
        }

        return bitmap
    }

    fun shareVerseBitmap(
        context: Context,
        shareLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        verse: VerseType?
    ) {
        try {
            val bitmap = createVerseOfDayBitmap(
                context = context,
                verse = verse
            )

            shareBitmap(context, shareLauncher, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun shareBitmap(
        context: Context,
        shareLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        bitmap: Bitmap,
        fileName: String = "VerseOfDay"
    ) {
        var outputStream: OutputStream? = null
        var uri: Uri? = null

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.png")
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                context.contentResolver.let {
                    uri = it.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    outputStream = uri?.let { uri -> it.openOutputStream(uri) }
                }
            } else {
                val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val image = File(imagesDir, "$fileName.png")
                outputStream = FileOutputStream(image)

                uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    image
                )
            }
            outputStream?.use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "image/png"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            shareLauncher.launch(Intent.createChooser(shareIntent, "Partilhar Versículo"))

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}