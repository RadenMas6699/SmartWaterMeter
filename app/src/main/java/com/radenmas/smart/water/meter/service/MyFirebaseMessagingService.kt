/*
 * Created by RadenMas on 1/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.ui.auth.AuthActivity

/**
 * Created by RadenMas on 01/04/2022.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {
//    private val CHANNEL_ID = "App"
//
//        val msg = message
//        MyForegroundService()
//    }
//
//    private fun showNotification(id: Int, value1: String) {
//        val notificationIntent = Intent(this, AuthActivity::class.java)
//
//        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notification = Notification.Builder(this, CHANNEL_ID)
//                .setContentTitle(resources.getString(R.string.app_name))
//                .setContentText(value1)
//                .setSmallIcon(R.drawable.ic_water_drop)
//                .setBadgeIconType(R.drawable.ic_water_drop)
//                .setContentIntent(pendingIntent)
//                .build()
//
//            startForeground(id, notification)
//        }
//    }
}