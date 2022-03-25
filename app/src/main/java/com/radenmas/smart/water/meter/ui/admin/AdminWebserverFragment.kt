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
import com.radenmas.smart.water.meter.databinding.FragmentWebserverAdminBinding
import com.radenmas.smart.water.meter.network.RetroWebserver
import com.radenmas.smart.water.meter.utils.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminWebserverFragment : Fragment() {

    private lateinit var b: FragmentWebserverAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentWebserverAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        onClick()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTagihan()
    }

    private fun getTagihan() {
        RetroWebserver.instance.getTotal().enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                val responseBody = response.body()

                Log.d(
                    "WEB",
                    "response Total : $responseBody "
                )
                b.tvTagihan.text = responseBody.toString()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                AppUtils.toast(requireActivity(), t.message.toString())
                Log.d("WEB", "response Total : $t")
            }

        })
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.imgSetting.setOnClickListener {

        }

        b.switchPower.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                RetroWebserver.instance.setRelayOn().enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("WEB", "response On : $response")

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("WEB", "response On : $t)")
                    }
                })
            } else {
                RetroWebserver.instance.setRelayOff().enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("WEB", "response Off : $response")

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("WEB", "response Off : $t")
                    }
                })
            }
        }
    }

}