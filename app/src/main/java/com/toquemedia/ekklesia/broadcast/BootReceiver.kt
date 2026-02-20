package com.toquemedia.ekklesia.broadcast

import com.toquemedia.ekklesia.utils.AlarmScheduler
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            AlarmScheduler.scheduleDailyAlarm(context)
        }
    }
}