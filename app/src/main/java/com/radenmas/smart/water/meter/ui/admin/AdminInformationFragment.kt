/*
 * Created by RadenMas on 19/7/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.radenmas.smart.water.meter.databinding.FragmentInformationAdminBinding
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminInformationFragment : Fragment() {

    private lateinit var b: FragmentInformationAdminBinding

    val TOPIC = "user"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentInformationAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        onClick()

        return v
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
        b.btnSendInformation.setOnClickListener {
            val title = b.etTitle.text.toString()
            val message = b.etMessage.text.toString()

            Retro.instance.postNotification(title, message)
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Utils.toast(requireContext(), "Informasi berhasil dikirim ke pelanggan")
                        b.etTitle.text.clear()
                        b.etMessage.text.clear()
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

                    }

                })
        }
    }
}