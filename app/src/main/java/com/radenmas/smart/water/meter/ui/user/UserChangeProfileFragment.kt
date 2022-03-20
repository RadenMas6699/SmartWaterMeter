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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserChangeProfileFragment : Fragment() {
    private lateinit var b: FragmentChangeProfileUserBinding
    private lateinit var progress: Dialog

    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    val args: UserChangeProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentChangeProfileUserBinding.inflate(layoutInflater, container, false)
        val v = b.root

        sharedPref = activity?.getSharedPreferences(
            getString(R.string.app_pref), Context.MODE_PRIVATE
        )!!
        editor = sharedPref.edit()

        onClick()

        val image = args.image
        val fullName = args.fullName
        val phone = args.phone
        val address = args.address

        Glide.with(this).load(image).into(b.imgProfile)
        b.etFullName.setText(fullName)
        b.etPhone.setText(phone)
        b.etAddress.setText(address)

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
                Toast.makeText(context, "Lengkapi yang masih kosong", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(context, "Data Pelanggan Berhasil Diupdate", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                progress.dismiss()
                Toast.makeText(context, "Data Pelanggan Gagal Diupdate", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun saveData(fullname: String, phone: String, address: String) {
        editor.putString("nama", fullname)
        editor.putString("noTelp", phone)
        editor.putString("alamat", address)

        editor.apply()
    }


}