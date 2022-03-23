/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.adapter.AduanAdapterAdmin
import com.radenmas.smart.water.meter.databinding.FragmentPengaduanAdminBinding
import com.radenmas.smart.water.meter.model.AduanResponse
import com.radenmas.smart.water.meter.network.Retro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminPengaduanFragment : Fragment() {
    private lateinit var b: FragmentPengaduanAdminBinding
    private lateinit var aduanAdapterAdmin: AduanAdapterAdmin

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentPengaduanAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        b.rvAduanUser.layoutManager = LinearLayoutManager(activity)
        aduanAdapterAdmin = AduanAdapterAdmin(requireActivity())
        b.rvAduanUser.adapter = aduanAdapterAdmin

        onClick()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Loading.showLoading(requireContext())

    }

    private fun getHistoryKeluhanFilter(filter: String) {
        Retro.instance.getKeluhanAdminFilter(filter)
            .enqueue(object : Callback<List<AduanResponse>> {
                override fun onResponse(
                    call: Call<List<AduanResponse>>,
                    response: Response<List<AduanResponse>>
                ) {
                    val dataKeluhan = response.body()
                    for (c in dataKeluhan!!) {
                        aduanAdapterAdmin.setKeluhan(dataKeluhan)
                    }
                }

                override fun onFailure(call: Call<List<AduanResponse>>, t: Throwable) {
                }
            })
    }

    private fun getHistoryKeluhanAll() {
        Retro.instance.getKeluhanAdmin().enqueue(object : Callback<List<AduanResponse>> {
            override fun onResponse(
                call: Call<List<AduanResponse>>,
                response: Response<List<AduanResponse>>
            ) {
                val dataKeluhan = response.body()
                for (c in dataKeluhan!!) {
                    aduanAdapterAdmin.setKeluhan(dataKeluhan)
                }
            }

            override fun onFailure(call: Call<List<AduanResponse>>, t: Throwable) {
            }
        })
    }


    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.tvTitleAduan.setOnClickListener {
            b.sortir.visibility = View.GONE
            getHistoryKeluhanFilter(resources.getString(R.string.sent))
        }

        b.tvTitleHistory.setOnClickListener {
            b.sortir.visibility = View.VISIBLE
            getHistoryKeluhanAll()
        }

        b.btnHistoryAll.setOnClickListener {
            getHistoryKeluhanAll()
        }
        b.btnHistoryProses.setOnClickListener {
            getHistoryKeluhanFilter(resources.getString(R.string.processed))
        }
        b.btnHistoryDone.setOnClickListener {
            getHistoryKeluhanFilter(resources.getString(R.string.finish))
        }
        b.btnHistoryReject.setOnClickListener {
            getHistoryKeluhanFilter(resources.getString(R.string.rejected))
        }
    }


}