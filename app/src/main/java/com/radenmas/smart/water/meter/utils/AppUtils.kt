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
        progress.setContentView(R.layout.dialog_progress)
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

    fun formatNumber(value: Int): String {
        return String.format("%,d", value)
    }

    fun formatRupiah(value: Int): String {
        val number: String =
            String.format("%,d", value)
        return "Rp $number"
    }

    fun formatUsage(value: String): String {
        return "$value M3"
    }

    fun formatToken(value: String): String {
        var number = value
        number = number.substring(0, number.length - 4) + "-" + number.substring(
            number.length - 4,
            number.length
        )
        number = number.substring(0, number.length - 8) + ")" + number.substring(
            number.length - 8,
            number.length
        )
        number = number.substring(0, number.length - 12) + "(" + number.substring(
            number.length - 12,
            number.length
        )
        return number
    }

    fun formatPeriod(month: String, year: String): String {
        var bulan: String? = null
        when (month) {
            "1" -> {
                bulan = "Januari"
            }
            "2" -> {
                bulan = "Februari"
            }
            "3" -> {
                bulan = "Maret"
            }
            "4" -> {
                bulan = "April"
            }
            "5" -> {
                bulan = "Mei"
            }
            "6" -> {
                bulan = "Juni"
            }
            "7" -> {
                bulan = "Juli"
            }
            "8" -> {
                bulan = "Agustus"
            }
            "9" -> {
                bulan = "September"
            }
            "10" -> {
                bulan = "Oktober"
            }
            "11" -> {
                bulan = "November"
            }
            "12" -> {
                bulan = "Desember"
            }
        }
        return "$bulan $year"
    }
}