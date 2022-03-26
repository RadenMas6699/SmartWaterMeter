/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.radenmas.smart.water.meter.adapter.TagihanAdapterUser
import com.radenmas.smart.water.meter.databinding.FragmentHistoryBillingUserBinding
import com.radenmas.smart.water.meter.model.TagihanResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserBillingFragment : Fragment() {
    private lateinit var b: FragmentHistoryBillingUserBinding
    private lateinit var paymentUser: TagihanAdapterUser
    private val args: UserBillingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentHistoryBillingUserBinding.inflate(layoutInflater, container, false)
        val v = b.root

        b.rvPaymentAll.layoutManager = LinearLayoutManager(activity)
        paymentUser = TagihanAdapterUser(requireActivity())
        b.rvPaymentAll.adapter = paymentUser

        AppUtils.showLoading(requireContext())

        onClick()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTagihanUser(args.idPelanggan)
    }

    private fun getTagihanUser(idPelanggan: String) {
        Retro.instance.getTagihanUser(idPelanggan)
            .enqueue(object : Callback<List<TagihanResponse>> {
                override fun onResponse(
                    call: Call<List<TagihanResponse>>,
                    response: Response<List<TagihanResponse>>
                ) {
                    AppUtils.dismissLoading()
                    val dataTagihan = response.body()
                    for (c in dataTagihan!!) {
                        b.rvPaymentAll.visibility = View.VISIBLE
                        b.lottieEmpty.visibility = View.INVISIBLE
                        paymentUser.setTagihan(dataTagihan)
                    }
                    if (dataTagihan.isEmpty()) {
                        b.rvPaymentAll.visibility = View.INVISIBLE
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