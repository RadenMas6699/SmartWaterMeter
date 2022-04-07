package com.radenmas.smart.water.meter.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(val context: Context) {
    val pref: SharedPreferences
    val editor: SharedPreferences.Editor

    val PRIVATE_MODE = 0

    companion object {
        private const val PREF_NAME = "app_pref"
        private const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        editor.commit()
    }

    fun isFirstTimeLaunch(): Boolean {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
    }
}