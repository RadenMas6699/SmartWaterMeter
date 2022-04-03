/*
 * Created by RadenMas on 31/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.google.firebase.database.*
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.ui.auth.AuthActivity

/**
 * Created by RadenMas on 31/03/2022.
 */
class MyForegroundService : Service() {

    var pdam1: String = null.toString()
    var pdam2: String = null.toString()

    private val CHANNEL_ID = "App"
//    private val NOTIFICATION_ID = 123

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        notification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

//        showNotification()

//        cekPerubahanData()


        return super.onStartCommand(intent, flags, startId)
//        return START_NOT_STICKY
    }

    private fun cekPerubahanData() {
        val database1: DatabaseReference = FirebaseDatabase.getInstance().getReference("pdam11")
        database1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pdam1 = snapshot.value.toString()
                if (pdam1 == "11") {
                    showNotification(1, pdam1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun showNotification(id: Int, value1: String) {
        val notificationIntent = Intent(this, AuthActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification = Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(resources.getString(R.string.app_name))
                .setContentText(value1)
                .setSmallIcon(R.drawable.ic_water_drop)
                .setBadgeIconType(R.drawable.ic_water_drop)
                .setContentIntent(pendingIntent)
                .build()

            startForeground(id, notification)
        }
    }

    private fun notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(notificationChannel)
        }
    }

}