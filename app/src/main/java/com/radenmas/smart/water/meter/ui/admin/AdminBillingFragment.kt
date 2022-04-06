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
import com.radenmas.smart.water.meter.adapter.TagihanAdapterAdmin
import com.radenmas.smart.water.meter.databinding.FragmentBillingAdminBinding
import com.radenmas.smart.water.meter.model.TagihanResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminBillingFragment : Fragment() {

    private lateinit var b: FragmentBillingAdminBinding
    private lateinit var paymentAdmin: TagihanAdapterAdmin

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentBillingAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        b.rvBillAll.layoutManager = LinearLayoutManager(activity)
        paymentAdmin = TagihanAdapterAdmin(requireActivity())
        b.rvBillAll.adapter = paymentAdmin

        AppUtils.showLoading(requireContext())

        onClick()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTagihanUser()
    }

    private fun getTagihanUser() {
        Retro.instance.getTagihanAdmin().enqueue(object : Callback<List<TagihanResponse>> {
            override fun onResponse(
                call: Call<List<TagihanResponse>>,
                response: Response<List<TagihanResponse>>
            ) {
                AppUtils.dismissLoading()
                val dataTagihan = response.body()
                for (c in dataTagihan!!) {
                    b.rvBillAll.visibility = View.VISIBLE
                    b.lottieEmpty.visibility = View.INVISIBLE
                    paymentAdmin.setTagihan(dataTagihan)
                }
                if (dataTagihan.isEmpty()) {
                    b.rvBillAll.visibility = View.INVISIBLE
                    b.lottieEmpty.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<List<TagihanResponse>>, t: Throwable) {

            }

        })
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}