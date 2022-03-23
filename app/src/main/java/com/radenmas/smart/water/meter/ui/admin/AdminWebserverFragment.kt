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
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.RetroWebserver
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
                Log.d("WIFI", response.toString())
                b.tvTagihan.text = response.toString()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {

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



                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                    }
                })
            } else {
                RetroWebserver.instance.setRelayOff().enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {



                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                    }
                })
            }
        }
    }

}