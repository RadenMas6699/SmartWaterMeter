/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pusher.pushnotifications.PushNotifications
import com.radenmas.smart.water.meter.databinding.ActivityAdminMainBinding

class AdminMainActivity : AppCompatActivity() {
    private lateinit var b: ActivityAdminMainBinding

    private var timerCekKoneksi: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        b = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(b.root)

//        runnabelCekKoneksi()

//        pusher()

//        PushNotifications.start(applicationContext, "8184bf14-ce14-41d9-959a-5473a36cd2bf")
//        PushNotifications.addDeviceInterest("hello")
    }

    private fun pusher() {
//        val options = PusherOptions()
//        options.setCluster("ap1")
//
//        val pusher = Pusher("4a73725a5a62ffc1fcc9", options)
//
//        pusher.connect(object : ConnectionEventListener {
//            override fun onConnectionStateChange(change: ConnectionStateChange) {
//                Log.i("Pusher", "State changed from ${change.previousState} to ${change.currentState}")
//            }
//
//            override fun onError(
//                message: String,
//                code: String,
//                e: Exception
//            ) {
//                Log.i("Pusher", "There was a problem connecting! code ($code), message ($message), exception($e)")
//            }
//        }, ConnectionState.ALL)
//
//        val channel = pusher.subscribe("my-channel")
//        channel.bind("my-event") { event ->
//            Log.i("Pusher","Received event with data: $event")
//        }
    }

//    @Suppress("DEPRECATION")
//    private fun isNetworkAvailable(): Boolean {
//        val connectivityManager =
//            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == android.net.NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
//            ConnectivityManager.TYPE_WIFI
//        )!!.state == android.net.NetworkInfo.State.CONNECTED
//    }

//    private fun runnabelCekKoneksi() {
//        val snackbar = Utils.snackInfinite(b.root, "Mohon periksa koneksi internet Anda")
//        timerCekKoneksi = object : CountDownTimer(300000, 5000) {
//            override fun onTick(millisUntilFinished: Long) {
//                if (!isNetworkAvailable()) {
//                    snackbar.show()
//                } else {
//                    snackbar.dismiss()
//                }
//            }
//
//            override fun onFinish() {}
//        }.start()
//    }
}