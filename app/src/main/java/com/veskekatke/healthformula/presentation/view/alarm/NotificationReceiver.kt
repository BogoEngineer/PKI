package com.veskekatke.healthformula.presentation.view.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val notificationHelper = NotificationHelper(p0!!)
        notificationHelper.createNotification()
    }

}