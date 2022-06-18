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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentHomeAdminBinding
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.Constant
import com.radenmas.smart.water.meter.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminHomeFragment : Fragment() {

    private lateinit var b: FragmentHomeAdminBinding
    private lateinit var sharedPref: SharedPreferences

    private lateinit var name: String
    private lateinit var id_admin: String
    private lateinit var avatar: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentHomeAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        sharedPref = activity?.getSharedPreferences(
            Constant.app_pref, Context.MODE_PRIVATE
        )!!

        initView()
        onClick()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            val token = task.result.toString()
//            b.token.text = token
            val dbToken = Firebase.database.getReference("Token")
            dbToken.child(id_admin).child("token").setValue(token)
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCountPelanggan()
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
        id_admin = sharedPref.getString(Constant.data_id_admin, null).toString()
        name = sharedPref.getString(Constant.data_name, null).toString()
        avatar = sharedPref.getString(Constant.data_avatar, null).toString()

        Glide.with(this).load(avatar).into(b.imgProfile)
        b.tvName.text = name
    }

    private fun onClick() {
        b.imgProfile.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_adminProfileFragment)
        }

        b.imgWebServer.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_adminWebserverFragment)
        }

        b.cardUser.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_adminUserFragment)
        }

        b.cardBilling.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_adminBillingFragment)
        }

        b.cardKeluhan.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_adminPengaduanFragment)
        }
    }

}