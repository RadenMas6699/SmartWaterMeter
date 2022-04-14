/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.radenmas.smart.water.meter.databinding.ActivityAdminMainBinding
import com.radenmas.smart.water.meter.utils.Utils

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

        runnabelCekKoneksi()
    }

    @Suppress("DEPRECATION")
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == android.net.NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
            ConnectivityManager.TYPE_WIFI
        )!!.state == android.net.NetworkInfo.State.CONNECTED
    }

    private fun runnabelCekKoneksi() {
        val snackbar = Utils.snackInfinite(b.root, "Mohon periksa koneksi internet Anda")
        timerCekKoneksi = object : CountDownTimer(300000, 5000) {
            override fun onTick(millisUntilFinished: Long) {
                if (!isNetworkAvailable()) {
                    snackbar.show()
                } else {
                    snackbar.dismiss()
                }
            }

            override fun onFinish() {}
        }.start()
    }
}