/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.radenmas.smart.water.meter.adapter.AduanAdapterAdmin
import com.radenmas.smart.water.meter.databinding.FragmentRiwayatKeluhanAdminBinding
import com.radenmas.smart.water.meter.model.AduanResponse
import com.radenmas.smart.water.meter.network.APIServices
import com.radenmas.smart.water.meter.network.Retro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminRiwayatKeluhanFragment : Fragment() {
    private lateinit var b: FragmentRiwayatKeluhanAdminBinding
    private lateinit var aduanAdapterAdmin: AduanAdapterAdmin

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentRiwayatKeluhanAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        b.rvHistoryKeluhanUser.layoutManager = LinearLayoutManager(activity)
        aduanAdapterAdmin = AduanAdapterAdmin(requireActivity())
        b.rvHistoryKeluhanUser.adapter = aduanAdapterAdmin

        b.swipeRefresh.setOnRefreshListener {
//            getDataKeluhan()
            Handler(Looper.getMainLooper()).postDelayed({
                b.swipeRefresh.isRefreshing = false
            }, 3000)
        }

//        onClick()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getDataKeluhan()
    }

//    private fun getDataKeluhanAll() {
//        Retro.instance.getHistoryKeluhanUser().enqueue(object : Callback<List<AduanResponse>> {
//            override fun onResponse(call: Call<List<AduanResponse>>, response: Response<List<AduanResponse>>) {
//                Log.d("RIWAYAT", aduanAdapterAdmin.itemCount.toString())
//
//                val dataKeluhan = response.body()
//                for (c in dataKeluhan!!) {
//                    aduanAdapterAdmin.setKeluhan(dataKeluhan)
//                }
//            }
//
//            override fun onFailure(call: Call<List<AduanResponse>>, t: Throwable) {
//                Log.e("Failed", t.message.toString())
//            }
//
//        })
//    }

//    private fun onClick() {
//        b.btnHistoryAll.setOnClickListener {
//            getDataKeluhanAll()
//        }
//        b.btnHistoryProses.setOnClickListener {
//            getDataKeluhanAll()
//        }
//        b.btnHistoryDone.setOnClickListener {
//            getDataKeluhanAll()
//        }
//        b.btnHistoryReject.setOnClickListener {
//            getDataKeluhanAll()
//        }
//    }

}