/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.user

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentChangeProfileUserBinding
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.AppUtils
import com.radenmas.smart.water.meter.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserChangeProfileFragment : Fragment() {
    private lateinit var b: FragmentChangeProfileUserBinding
    private lateinit var progress: Dialog

    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    private val args: UserChangeProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentChangeProfileUserBinding.inflate(layoutInflater, container, false)
        val v = b.root

        sharedPref = activity?.getSharedPreferences(
            Constant.app_pref, Context.MODE_PRIVATE
        )!!
        editor = sharedPref.edit()

        onClick()

        val avatar = args.image
        val fullName = args.fullName
        val phone = args.phone
        val address = args.address

        if (avatar == Constant.default) {
            Glide.with(this)
                .load(R.drawable.ic_profile_default)
                .into(b.imgProfile)
        } else {
            Glide.with(this)
                .load(avatar)
                .into(b.imgProfile)
        }
        b.etFullName.hint = fullName
        b.etPhone.hint = phone
        b.etAddress.hint = address

        return v
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.btnSaveProfile.setOnClickListener {
            val fullname: String = b.etFullName.text.toString()
            val phone: String = b.etPhone.text.toString()
            val address: String = b.etAddress.text.toString()

            if (fullname.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                AppUtils.toast(requireContext(),"Lengkapi yang masih kosong")
            } else {
                progress = Dialog(requireActivity())
                progress.setContentView(R.layout.progress_layout)
                progress.window!!.setBackgroundDrawableResource(R.drawable.bg_progress)
                progress.show()

                updateProfile(args.idPelanggan, fullname, phone, address)
            }
        }
    }

    private fun updateProfile(
        idPelanggan: String,
        fullname: String,
        phone: String,
        address: String
    ) {
        Retro.instance.updateDataUser(
            idPelanggan, fullname, phone, address
        ).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                saveData(
                    fullname,
                    phone,
                    address
                )
                progress.dismiss()
                AppUtils.toast(requireContext(),"Data Pelanggan Berhasil Diupdate")
                activity?.onBackPressed()
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                progress.dismiss()
                AppUtils.toast(requireContext(),"Data Pelanggan Gagal Diupdate")
            }

        })
    }

    private fun saveData(fullname: String, phone: String, address: String) {
        editor.putString(Constant.data_name, fullname)
        editor.putString(Constant.data_phone, phone)
        editor.putString(Constant.data_address, address)

        editor.apply()
    }


}