/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.os.Bundle
import android.os.Handler
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
import com.radenmas.smart.water.meter.utils.AppUtils


class AdminWebServerFragment : Fragment() {

    private lateinit var b: FragmentWebServerAdminBinding

    private var requestQueue: RequestQueue? = null
    private var stringCircle: StringRequest? = null
    private var stringData: StringRequest? = null
    private var stringRelay: StringRequest? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentWebServerAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        requestQueue = Volley.newRequestQueue(context)

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

    private fun getDataCircle(url: String, progress: CircularProgressBar, text: TextView) {
        stringCircle = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                text.text = response
                val value = response.toFloat()
                progress.progress = value
            },
            {})
        requestQueue?.add(stringCircle)
    }

    private fun getData(url: String, text: TextView) {
        stringData = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                text.text = response.toString()
            },
            {})
        requestQueue?.add(stringData)
    }

    private fun setRelay(url: String) {
        stringRelay = StringRequest(
            Request.Method.GET,
            url,
            { response -> AppUtils.toast(requireContext(), response.toString()) },
            {})
        requestQueue?.add(stringRelay)
    }

    override fun onStop() {
        super.onStop()
        requestQueue?.cancelAll(stringCircle)
        requestQueue?.cancelAll(stringData)
    }

}