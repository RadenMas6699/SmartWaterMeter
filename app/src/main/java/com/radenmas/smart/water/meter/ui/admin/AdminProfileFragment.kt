/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentProfileAdminBinding
import com.radenmas.smart.water.meter.databinding.LogoutLayoutBinding
import com.radenmas.smart.water.meter.ui.auth.AuthActivity
import com.radenmas.smart.water.meter.ui.user.UserProfileFragmentDirections

class AdminProfileFragment : Fragment() {

    private lateinit var b: FragmentProfileAdminBinding
    private lateinit var logout: Dialog
    private lateinit var dl: LogoutLayoutBinding

    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentProfileAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        sharedPref = activity?.getSharedPreferences(
            getString(R.string.app_pref), Context.MODE_PRIVATE
        )!!
        editor = sharedPref.edit()

        initView()
        onClick()

        return v
    }

    private fun initView() {
        Glide.with(this).load(sharedPref.getString("image", null)).into(b.imgProfile)
        b.tvFullName.text = sharedPref.getString("nama", null)
        b.tvUserID.text = sharedPref.getString("idPelanggan", null)
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.imgProfile.setOnClickListener {
            val lihatFoto =
                AdminProfileFragmentDirections.actionAdminProfileFragmentToLihatFotoFragment(
                    sharedPref.getString("image", null).toString(),
                    sharedPref.getString("nama", null).toString(),
                )
            findNavController().navigate(lihatFoto)
        }

        b.rvChangeProfile.setOnClickListener {

        }

        b.rvGuide.setOnClickListener {

        }

        b.rvTerm.setOnClickListener {

        }

        b.rvPrivacy.setOnClickListener {

        }

        b.btnLogout.setOnClickListener {
            dl = LogoutLayoutBinding.inflate(
                layoutInflater
            )
            val v = dl.root

            logout = Dialog(requireActivity())
            logout.setContentView(v)
            logout.setCancelable(false)
            logout.window!!.setBackgroundDrawableResource(R.drawable.bg_progress)
            logout.show()

            dl.btnNo.setOnClickListener {
                logout.dismiss()
            }

            dl.btnYes.setOnClickListener {
                editor.clear().commit()
                startActivity(Intent(activity, AuthActivity::class.java))
                requireActivity().finish()
            }
        }
    }

}