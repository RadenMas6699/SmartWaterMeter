/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.adapter.AduanAdapterAdmin
import com.radenmas.smart.water.meter.databinding.FragmentPengaduanAdminBinding
import com.radenmas.smart.water.meter.model.AduanResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.Utils
import com.radenmas.smart.water.meter.utils.Constant
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
        Utils.showLoading(requireContext())
        getHistoryKeluhanFilter(resources.getString(R.string.sent))
    }

    private fun getHistoryKeluhanFilter(filter: String) {
        Retro.instance.getKeluhanAdminFilter(filter)
            .enqueue(object : Callback<List<AduanResponse>> {
                override fun onResponse(
                    call: Call<List<AduanResponse>>,
                    response: Response<List<AduanResponse>>
                ) {
                    Utils.dismissLoading()
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
            b.tvTitleAduan.setTextColor(ResourcesCompat.getColor(resources, R.color.primary_text, null))
            b.tvTitleHistory.setTextColor(ResourcesCompat.getColor(resources, R.color.hint, null))
            getHistoryKeluhanFilter(resources.getString(R.string.sent))
        }

        b.tvTitleHistory.setOnClickListener {
            b.sortir.visibility = View.VISIBLE
            b.tvTitleHistory.setTextColor(ResourcesCompat.getColor(resources, R.color.primary_text, null))
            b.tvTitleAduan.setTextColor(ResourcesCompat.getColor(resources, R.color.hint, null))
            setButtonActive(
                b.btnHistoryAll,
                b.btnHistoryProses,
                b.btnHistoryDone,
                b.btnHistoryReject
            )
            getHistoryKeluhanAll()
        }

        b.btnHistoryAll.setOnClickListener {
            setButtonActive(
                b.btnHistoryAll,
                b.btnHistoryProses,
                b.btnHistoryDone,
                b.btnHistoryReject
            )
            getHistoryKeluhanAll()
        }
        b.btnHistoryProses.setOnClickListener {
            setButtonActive(
                b.btnHistoryProses,
                b.btnHistoryAll,
                b.btnHistoryDone,
                b.btnHistoryReject
            )
            getHistoryKeluhanFilter(resources.getString(R.string.processed))
        }
        b.btnHistoryDone.setOnClickListener {
            setButtonActive(
                b.btnHistoryDone,
                b.btnHistoryAll,
                b.btnHistoryProses,
                b.btnHistoryReject
            )
            getHistoryKeluhanFilter(resources.getString(R.string.finish))
        }
        b.btnHistoryReject.setOnClickListener {
            setButtonActive(
                b.btnHistoryReject,
                b.btnHistoryAll,
                b.btnHistoryProses,
                b.btnHistoryDone
            )
            getHistoryKeluhanFilter(resources.getString(R.string.rejected))
        }
    }

    private fun setButtonActive(
        btnActive: MaterialButton,
        btnNotActive1: MaterialButton,
        btnNotActive2: MaterialButton,
        btnNotActive3: MaterialButton
    ) {
        btnActive.setTextColor(ResourcesCompat.getColor(resources, R.color.primary_text, null))
        btnActive.strokeColor = ColorStateList.valueOf(Color.parseColor(Constant.color_primary))

        btnNotActive1.setTextColor(ResourcesCompat.getColor(resources, R.color.hint, null))
        btnNotActive1.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))

        btnNotActive2.setTextColor(ResourcesCompat.getColor(resources, R.color.hint, null))
        btnNotActive2.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))

        btnNotActive3.setTextColor(ResourcesCompat.getColor(resources, R.color.hint, null))
        btnNotActive3.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))
    }
}