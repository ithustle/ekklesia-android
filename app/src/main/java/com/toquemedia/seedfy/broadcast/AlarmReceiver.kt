package com.toquemedia.seedfy.broadcast

import com.toquemedia.seedfy.utils.AlarmScheduler
import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.toquemedia.seedfy.repository.VerseRepositoryImpl
import com.toquemedia.seedfy.utils.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var verseRepository: VerseRepositoryImpl

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        NotificationHelper.showNotification(context, verseRepository)
        AlarmScheduler.scheduleDailyAlarm(context)
    }
}