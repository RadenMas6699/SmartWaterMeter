/*
 * Created by RadenMas on 1/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

/**
 * Created by RadenMas on 01/04/2022.
 */
class MyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)){
            val serviceIntent = Intent(context,MyForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context?.startForegroundService(serviceIntent)
            }
        }
    }
}