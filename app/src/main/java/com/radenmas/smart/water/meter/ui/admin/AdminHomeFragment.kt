/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentHomeAdminBinding
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Retro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminHomeFragment : Fragment() {

    private lateinit var b: FragmentHomeAdminBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentHomeAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        sharedPref = activity?.getSharedPreferences(
            getString(R.string.app_pref), Context.MODE_PRIVATE
        )!!
        getCountPelanggan()
        initView()
        onClick()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun getCountPelanggan() {

        Retro.instance.getCountUser().enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                b.tvPelanggan.text = response.body()?.message.toString()
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

            }

        })

    }

    private fun initView() {
        Glide.with(this).load(sharedPref.getString("image", null)).into(b.imgProfile)
        b.tvFullName.text = sharedPref.getString("nama", null)
    }

    private fun onClick() {
        b.imgProfile.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_adminProfileFragment)
        }

        b.imgWebServer.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_adminWebserverFragment)
        }

        b.imgNotif.setOnClickListener {

        }

        b.cardUser.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_adminUserFragment)
        }

        b.cardKeluhan.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_adminPengaduanFragment)
        }
    }

}