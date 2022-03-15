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
import retrofit2.Call
import retrofit2.Callback

class LoginFragment : Fragment() {

    private lateinit var b: FragmentLoginBinding
    private lateinit var progress: Dialog

    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentLoginBinding.inflate(layoutInflater, container, false)
        val v = b.root

        sharedPref = activity?.getSharedPreferences(
            getString(R.string.app_pref), Context.MODE_PRIVATE
        )!!
        editor = sharedPref.edit()


        val loginStatus: String? =
            sharedPref.getString(resources.getString(R.string.level_user), "")

        when {
            loginStatus.equals(
                resources.getString(R.string.user),
                ignoreCase = true
            ) -> {
                startActivity(Intent(context, UserMainActivity::class.java))
                activity?.finish()
            }
            loginStatus.equals(
                resources.getString(R.string.admin),
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
                progress = Dialog(requireActivity())
                progress.setContentView(R.layout.progress_layout)
                progress.window!!.setBackgroundDrawableResource(R.drawable.bg_progress)
                progress.show()

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
                progress.dismiss()
                saveData(
                    response.body()?.username.toString(),
                    response.body()?.level_user.toString(),
                    response.body()?.nama.toString(),
                    response.body()?.id_user.toString(),
                    response.body()?.no_ktp.toString(),
                    response.body()?.no_telp.toString(),
                    response.body()?.alamat.toString(),
                    response.body()?.image.toString(),
                    response.body()?.terdaftar.toString()
                )

                when {
                    response.body()?.level_user.equals(
                        resources.getString(R.string.user),
                        ignoreCase = true
                    ) -> {
                        startActivity(Intent(context, UserMainActivity::class.java))
                        activity?.finish()
                    }
                    response.body()?.level_user.equals(
                        resources.getString(R.string.admin),
                        ignoreCase = true
                    ) -> {
                        startActivity(Intent(context, AdminMainActivity::class.java))
                        activity?.finish()
                    }
                    else -> {
                        Toast.makeText(context, "Gagal Masuk", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                progress.dismiss()
                Toast.makeText(context, "Gagal Masuk", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveData(
        username: String,
        levelUser: String,
        nama: String,
        idUser: String,
        noKtp: String,
        noTelp: String,
        alamat: String,
        image: String,
        terdaftar: String
    ) {
        editor.putString("username", username)
        editor.putString(resources.getString(R.string.level_user), levelUser)
        editor.putString("nama", nama)
        editor.putString("idPelanggan", idUser)
        editor.putString("noKtp", noKtp)
        editor.putString("noTelp", noTelp)
        editor.putString("alamat", alamat)
        editor.putString("image", image)
        editor.putString("terdaftar", terdaftar)

        editor.apply()
    }
}