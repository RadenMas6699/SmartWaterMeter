/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.user

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.ActivityUserMainBinding

class UserMainActivity : AppCompatActivity() {
    private lateinit var b: ActivityUserMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        b = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        Firebase.messaging.subscribeToTopic("user")

        val versionName = packageManager.getPackageInfo(packageName, 0).versionName
        val packageName = packageManager.getPackageInfo(packageName, 0).packageName
        val appName = resources.getString(R.string.app_name)

        checkUpdate(appName, packageName, versionName)
    }

    private fun checkUpdate(appName: String, packageName: String, versionName: String) {
        val defaultsRate = HashMap<String, Any>()
        defaultsRate["lates_app_version"] = versionName
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        val config: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        config.setConfigSettingsAsync(configSettings)
        config.setDefaultsAsync(defaultsRate)
        config.fetchAndActivate().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val newVersionCode = config.getString("lates_app_version")
                val lateAppVersion = newVersionCode.toFloat()
                if (lateAppVersion > versionName.toFloat()) {
                    showTheDialog(appName, packageName, newVersionCode)
                }
            }
        }
    }

    private fun showTheDialog(appName: String, packageName: String, versionName: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Update Aplikasi")
            .setMessage("Versi terbaru sudah tersedia! Update aplikasi $appName ke versi: $versionName")
            .setPositiveButton("Update", null)
            .show()

        dialog.setCancelable(false)

        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            val uriUpdate =
                "https://play.google.com/store/apps/details?id=$packageName"
            val uri = Uri.parse(uriUpdate)
            val updatePlayStore = Intent(Intent.ACTION_VIEW, uri)
            updatePlayStore.setPackage("com.android.vending")
            try {
                startActivity(updatePlayStore)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(uriUpdate)
                    )
                )
            }
        }
    }
}