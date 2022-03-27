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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentHomeAdminBinding
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminHomeFragment : Fragment() {

    private lateinit var b: FragmentHomeAdminBinding
    private lateinit var sharedPref: SharedPreferences

    private lateinit var name: String
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

        getNotifAdmin()

        return v
    }

    private fun getNotifAdmin() {
        val database = Firebase.database
        var pdam1: Int? = null
        var pdam11: Int? = null
        var pdam12: Int? = null
        var pdam2: Int? = null
        var pdam21: Int? = null
        var pdam22: Int? = null

        val reffPDAM1 = database.getReference("pdam1")
        reffPDAM1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pdam1 = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        val reffPDAM11 = database.getReference("pdam11")
        reffPDAM11.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pdam11 = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        val reffPDAM12 = database.getReference("pdam12")
        reffPDAM12.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pdam12 = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        val reffPDAM2 = database.getReference("pdam2")
        reffPDAM2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pdam2 = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        val reffPDAM21 = database.getReference("pdam21")
        reffPDAM21.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pdam21 = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        val reffPDAM22 = database.getReference("pdam22")
        reffPDAM22.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pdam22 = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
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
        name = sharedPref.getString(Constant.data_name, null).toString()
        avatar = sharedPref.getString(Constant.data_avatar, null).toString()

        Glide.with(this).load(avatar).into(b.imgProfile)
        b.tvFullName.text = name
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

        b.cardBilling.setOnClickListener {
//            findNavController().navigate(R.id.)
        }

        b.cardKeluhan.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_adminPengaduanFragment)
        }
    }

}