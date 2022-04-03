/*
 * Created by RadenMas on 1/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.service

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build


/**
 * Created by RadenMas on 01/04/2022.
 */
class MyApp:Application() {
    val CHANNEL_ID = "autoStartServiceChannel"
    val CHANNEL_NAME = "Auto Start Service Channel"
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }
}