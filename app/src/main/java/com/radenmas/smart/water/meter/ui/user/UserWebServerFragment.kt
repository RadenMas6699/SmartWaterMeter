/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.user

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.radenmas.smart.water.meter.databinding.FragmentWebServerUserBinding
import com.radenmas.smart.water.meter.network.Config
import com.radenmas.smart.water.meter.utils.AppUtils

class UserWebServerFragment : Fragment() {
    private lateinit var b: FragmentWebServerUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentWebServerUserBinding.inflate(layoutInflater, container, false)
        val v = b.root

        onClick()

        val handler = Handler()
        val task: Runnable = object : Runnable {
            override fun run() {
                handler.postDelayed(this, 1000)
                getData(Config.URL_TOTAL, b.tvBill)
                getData(Config.URL_BILLING, b.tvUsage)
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

        b.switchPower.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setRelay(Config.URL_RELAY_ON)
            } else {
                setRelay(Config.URL_RELAY_OFF)
            }
        }

    }

    private fun getDataCircle(url: String, progress: CircularProgressBar, text: TextView) {
        val requestQueue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                text.text = response
                val value = response.toFloat()
                progress.progress = value
            },
            {})
        requestQueue.add(stringRequest)
    }

    private fun getData(url: String, text: TextView) {
        val requestQueue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                text.text = response.toString()
            },
            {})
        requestQueue.add(stringRequest)
    }

    private fun setRelay(url: String) {
        val requestQueue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response -> AppUtils.toast(requireContext(), response.toString()) },
            {})
        requestQueue.add(stringRequest)
    }
}