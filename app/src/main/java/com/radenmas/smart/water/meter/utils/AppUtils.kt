/*
 * Created by RadenMas on 21/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.utils

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import com.radenmas.smart.water.meter.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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

    fun toast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun formatDate(stringDate: String): String {
        val inputFormat: DateFormat = SimpleDateFormat(
            Constant.pattern_input_date,
            Locale("ID")
        )
        val outputFormat: DateFormat =
            SimpleDateFormat(Constant.pattern_output_date, Locale("ID"))
        val date: Date = inputFormat.parse(stringDate)
        return outputFormat.format(date)
    }

    fun formatNumber(value:Int):String{
        return String.format("%,d", value)
    }

    fun formatRupiah(value:Int):String{
        val number: String =
            String.format("%,d", value)
        return "Rp. $number"
    }
}