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
import com.radenmas.smart.water.meter.databinding.FragmentAddUserAdminBinding
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminAddUserFragment : Fragment() {
    private lateinit var b: FragmentAddUserAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentAddUserAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        onClick()

        return v
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.dateRegitrasi.setOnClickListener {

        }

        b.btnAddUser.setOnClickListener {

            val userID = b.etUserID.text.toString()
            val fullname = b.etFullName.text.toString()
            val ktp = b.etNoKtp.text.toString()
            val phone = b.etPhone.text.toString()
            val address = b.etAddress.text.toString()
            val date = b.dateRegitrasi.text.toString()
            val year = b.etYearMeteran.text.toString()
            val type = b.etTypeMeteran.text.toString()
            val status = 1

            if (userID.isBlank() ||
                fullname.isBlank() ||
                ktp.isBlank() ||
                phone.isBlank() ||
                address.isBlank() ||
                date.isBlank() ||
                year.isBlank() ||
                type.isBlank()
            ) {
                AppUtils.toast(requireContext(), "Lengkapi yang masih kosong")
            } else {
                AppUtils.showLoading(requireContext())

                addUser(userID.toInt(), fullname, ktp, phone, address, date, year, type, status)
            }
        }
    }

    private fun addUser(
        userID: Int,
        fullname: String,
        ktp: String,
        phone: String,
        address: String,
        date: String,
        year: String,
        type: String,
        status: Int
    ) {
        Retro.instance.addUser(
            userID,
            fullname,
            ktp,
            phone,
            address,
            date,
            year,
            type,
            status
        ).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                AppUtils.dismissLoading()
                AppUtils.toast(requireContext(), "Pelanggan berhasil ditambahkan")

                b.etUserID.text.clear()
                b.etFullName.text.clear()
                b.etNoKtp.text.clear()
                b.etPhone.text.clear()
                b.etAddress.text.clear()
                b.dateRegitrasi.text.clear()
                b.etYearMeteran.text.clear()
                b.etTypeMeteran.text.clear()
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                AppUtils.dismissLoading()
                AppUtils.toast(requireContext(), "Pelanggan gagal ditambahkan")
            }

        })
    }

}