/*
 * Created by RadenMas on 21/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.radenmas.smart.water.meter.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * Created by RadenMas on 21/03/2022.
 */
object Utils {
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

    fun snack(view: View, msg: String) {
        Snackbar.make(
            view,
            msg,
            Snackbar.LENGTH_LONG
        ).show()
    }

    fun snackInfinite(view: View, msg: String): Snackbar {
        return Snackbar.make(
            view,
            msg, Snackbar.LENGTH_INDEFINITE
        )
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
//        return "$value M\u00B3"
        return "$value L"
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

    fun reduceBitmapSize(bitmap: Bitmap, MAX_SIZE: Int): Bitmap? {
        val ratioSquare: Double
        val bitmapHeight: Int = bitmap.height
        val bitmapWidth: Int = bitmap.width
        ratioSquare = (bitmapHeight * bitmapWidth / MAX_SIZE).toDouble()
        if (ratioSquare <= 1) return bitmap
        val ratio = sqrt(ratioSquare)
        val requiredHeight = (bitmapHeight / ratio).roundToInt()
        val requiredWidth = (bitmapWidth / ratio).roundToInt()
        return Bitmap.createScaledBitmap(bitmap, requiredWidth, requiredHeight, true)
    }
}