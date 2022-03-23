/*
 * Created by RadenMas on 21/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.utils

import android.app.Dialog
import android.content.Context
import com.radenmas.smart.water.meter.R

/**
 * Created by RadenMas on 21/03/2022.
 */
object AppUtils {
    private lateinit var progress: Dialog

    fun showLoading(context: Context) {
        progress = Dialog(context)
        progress.setContentView(R.layout.progress_layout)
        progress.window!!.setBackgroundDrawableResource(R.drawable.bg_progress)
        progress.show()
    }

    fun dismissLoading() {
        progress.dismiss()
    }
}