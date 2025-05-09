package com.toquemedia.seedfy.services


import com.google.firebase.messaging.FirebaseMessaging
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class NotificationService @Inject constructor(
    private val messagingService: FirebaseMessaging
) {

    suspend fun subscribeToTopicForNotification(topic: String) {
        messagingService.subscribeToTopic(topic).await()
    }
}