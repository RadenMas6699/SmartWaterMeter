/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentWebServerAdminBinding
import com.radenmas.smart.water.meter.network.Config
import com.radenmas.smart.water.meter.utils.Utils


class AdminWebServerFragment : Fragment() {

    private lateinit var b: FragmentWebServerAdminBinding

    private lateinit var requestQueue: RequestQueue
    private lateinit var stringCircle: StringRequest
    private lateinit var stringData: StringRequest
    private lateinit var stringRelay: StringRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentWebServerAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        requestQueue = Volley.newRequestQueue(context)

        onClick()

        val handler = Handler(Looper.getMainLooper())
        val task: Runnable = object : Runnable {
            override fun run() {
                handler.postDelayed(this, 1000)
                getDataNumber(Config.URL_USAGE, b.tvUsage)
                getData(Config.URL_CALIBRATION, b.tvBill)
                getDataCircle(Config.URL_DEBIT, b.progressDebit, b.tvDebit)
            }
        }
        handler.post(task)

        return v
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.imgSetting.setOnClickListener {
            findNavController().navigate(R.id.action_adminWebserverFragment_to_adminSettingFragment)
        }

        b.switchPower.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setRelay(Config.URL_RELAY_ON)
            } else if (!isChecked) {
                setRelay(Config.URL_RELAY_OFF)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDataCircle(url: String, progress: CircularProgressBar, text: TextView) {
        stringCircle = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                if (response != "nan") {
                    text.text = "$response L/s"
                    val value = response.toFloat()
                    progress.progress = value
                } else {
                    text.text = "0 L/s"
                    progress.progress = 0f
                }

            },
            { })
        requestQueue.add(stringCircle)
    }

    private fun getData(url: String, text: TextView) {
        stringData = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val value = response.toString()
                text.text = value
            },
            { })
        requestQueue.add(stringData)
    }

    private fun getDataNumber(url: String, text: TextView) {
        stringData = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val value = response.toFloat().toInt()
                text.text = Utils.formatNumber(value)
            },
            { })
        requestQueue.add(stringData)
    }

    private fun setRelay(url: String) {
        stringRelay = StringRequest(
            Request.Method.GET,
            url,
            { },
            { })
        requestQueue.add(stringRelay)
    }

    override fun onStop() {
        super.onStop()
        requestQueue.cancelAll(stringCircle)
        requestQueue.cancelAll(stringData)
    }

}