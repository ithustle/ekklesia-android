package com.toquemedia.seedfy.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.toquemedia.seedfy.MainActivity
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.repository.VerseRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.jvm.java

object NotificationHelper {
    private const val CHANNEL_ID = "daily_reminder_channel"
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Versículos do dia",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "O Seedfy irá notificar-te todos os dias as 07h57 sobre o versículo do dia"
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(context: Context, verseRepository: VerseRepositoryImpl) {
        scope.launch {
            val intent = Intent(context, MainActivity::class.java).apply {
                //flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val verseOfDay = verseRepository.getVerseOfDay()

            verseOfDay?.let {
                val book = it.verseOfDay.first
                val cap = it.verseOfDay.second
                val verse = it.verseOfDay.third

                val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.icone_seedfy)
                    .setContentTitle("Versículo do dia")
                    .setContentText("O versículo de hoje é de $book ${cap}:$verse. Toque para meditar nele \uD83D\uDE4F\uD83C\uDFFE")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()

                NotificationManagerCompat.from(context).notify(1, notification)
            }
        }
    }
}
