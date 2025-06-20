package com.toquemedia.seedfy.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.toquemedia.seedfy.MainActivity
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.di.modules.UserServiceEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlin.random.Random

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val userService = EntryPointAccessors.fromApplication(
            applicationContext, UserServiceEntryPoint::class.java
        ).userService()

        val title = remoteMessage.notification?.title ?: "Nova notificação"
        val body = remoteMessage.notification?.body ?: "Você recebeu uma nova mensagem"
        val data = remoteMessage.data

        val postId = data["postId"]
        val communityId = data["communityId"]

        val userEmail = data["email"]
        val user = userService.getCurrentUser()

        if (user?.email != userEmail) {
            showNotification(title, body, postId ?: "", communityId ?: "")
        }
    }

    private fun showNotification(
        title: String, message: String, postId: String, communityId: String
    ) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt(3000)

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("postId", postId)
            putExtra("communityId", communityId)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        setupNotificationChannels(notificationManager)

        val notification =
            NotificationCompat.Builder(this, "2310").setContentTitle(title).setContentText(message)
                .setSmallIcon(R.drawable.icone_seedfy).setAutoCancel(true)
                .setContentIntent(pendingIntent).setPriority(PRIORITY_HIGH).build()

        notificationManager.notify(notificationId, notification)
    }

    private fun setupNotificationChannels(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "2310", "Notificações do App", NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para todas as notificações do aplicativo"
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}