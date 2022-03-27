/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.auth

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentLoginBinding
import com.radenmas.smart.water.meter.model.LoginResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.ui.admin.AdminMainActivity
import com.radenmas.smart.water.meter.ui.user.UserMainActivity
import com.radenmas.smart.water.meter.utils.AppUtils
import com.radenmas.smart.water.meter.utils.Constant
import retrofit2.Call
import retrofit2.Callback

class LoginFragment : Fragment() {

    private lateinit var b: FragmentLoginBinding

    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentLoginBinding.inflate(layoutInflater, container, false)
        val v = b.root

        sharedPref = activity?.getSharedPreferences(
            Constant.app_pref, Context.MODE_PRIVATE
        )!!
        editor = sharedPref.edit()

        val loginStatus: String? =
            sharedPref.getString(Constant.data_level, "")

        when {
            loginStatus.equals(
                Constant.user,
                ignoreCase = true
            ) -> {
                startActivity(Intent(context, UserMainActivity::class.java))
                activity?.finish()
            }
            loginStatus.equals(
                Constant.admin,
                ignoreCase = true
            ) -> {
                startActivity(Intent(context, AdminMainActivity::class.java))
                activity?.finish()
            }
        }

        b.btnLogin.setOnClickListener {
            val strUsername: String = b.etUsername.text.toString()
            val strPassword: String = b.etPassword.text.toString()

            if (strUsername.isEmpty() || strPassword.isEmpty()) {
                Toast.makeText(context, "Lengkapi yang masih kosong", Toast.LENGTH_SHORT).show()
            } else {
                AppUtils.showLoading(requireContext())

                postLogin(strUsername, strPassword)
            }
        }

        return v
    }

    private fun postLogin(username: String, password: String) {
        Retro.instance.postLogin(
            username,
            password
        ).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: retrofit2.Response<LoginResponse>
            ) {
                AppUtils.dismissLoading()
                saveData(
                    response.body()?.id_admin.toString(),
                    response.body()?.id_pelanggan.toString(),
                    response.body()?.name.toString(),
                    response.body()?.level.toString(),
                    response.body()?.ktp.toString(),
                    response.body()?.phone.toString(),
                    response.body()?.address.toString(),
                    response.body()?.avatar.toString(),
                    response.body()?.registered.toString()
                )

                when {
                    response.body()?.level.equals(
                        Constant.user,
                        ignoreCase = true
                    ) -> {
                        startActivity(Intent(context, UserMainActivity::class.java))
                        activity?.finish()
                    }
                    response.body()?.level.equals(
                        Constant.admin,
                        ignoreCase = true
                    ) -> {
                        startActivity(Intent(context, AdminMainActivity::class.java))
                        activity?.finish()
                    }
                    else -> {
                        AppUtils.toast(requireContext(), "Gagal Masuk")
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                AppUtils.dismissLoading()
                AppUtils.toast(requireContext(), "Gagal Masuk")
            }
        })
    }

    private fun saveData(
        id_admin: String,
        id_pelanggan: String,
        name: String,
        level: String,
        ktp: String,
        phone: String,
        address: String,
        avatar: String,
        registered: String
    ) {
        editor.putString(Constant.data_id_admin, id_admin)
        editor.putString(Constant.data_id_pelanggan, id_pelanggan)
        editor.putString(Constant.data_name, name)
        editor.putString(Constant.data_level, level)
        editor.putString(Constant.data_ktp, ktp)
        editor.putString(Constant.data_phone, phone)
        editor.putString(Constant.data_address, address)
        editor.putString(Constant.data_avatar, avatar)
        editor.putString(Constant.data_registered, registered)

        editor.apply()
    }
}