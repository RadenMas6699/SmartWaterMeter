/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentWebServerAdminBinding
import com.radenmas.smart.water.meter.network.RetroWebServer
import com.radenmas.smart.water.meter.utils.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminWebServerFragment : Fragment() {

    private lateinit var b: FragmentWebServerAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentWebServerAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        onClick()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTagihan()
    }

    private fun getTagihan() {
        RetroWebServer.instance.getTotalWebServer().enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                val responseBody = response.body()

                Log.d(
                    "WEB",
                    "response Total R : $responseBody "
                )
                b.tvTagihan.text = responseBody.toString()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("WEB", "response Total F : $t")
            }

        })
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
                RetroWebServer.instance.setRelayOn().enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("WEB", "response On R : $response")

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("WEB", "response On F : $t)")
                    }
                })
            } else {
                RetroWebServer.instance.setRelayOff().enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("WEB", "response Off R : $response")

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("WEB", "response Off F : $t")
                    }
                })
            }
        }
    }

}