/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.DialogLogoutBinding
import com.radenmas.smart.water.meter.databinding.FragmentProfileAdminBinding
import com.radenmas.smart.water.meter.ui.auth.AuthActivity
import com.radenmas.smart.water.meter.utils.Utils
import com.radenmas.smart.water.meter.utils.Constant

class AdminProfileFragment : Fragment() {

    private lateinit var b: FragmentProfileAdminBinding
    private lateinit var logout: Dialog
    private lateinit var dl: DialogLogoutBinding

    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var name: String
    private lateinit var avatar: String
    private lateinit var idAdmin: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentProfileAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.WHITE

        sharedPref = activity?.getSharedPreferences(
            Constant.app_pref, Context.MODE_PRIVATE
        )!!
        editor = sharedPref.edit()

        initView()
        onClick()

        return v
    }

    private fun initView() {
        name = sharedPref.getString(Constant.data_name, null).toString()
        avatar = sharedPref.getString(Constant.data_avatar, null).toString()
        idAdmin = sharedPref.getString(Constant.data_id_admin, null).toString()

        if (avatar == Constant.default) {
            Glide.with(this)
                .load(R.drawable.ic_profile_default)
                .into(b.imgProfile)
        } else {
            Glide.with(this)
                .load(avatar)
                .into(b.imgProfile)
        }
        b.tvName.text = name
        b.tvUserID.text = idAdmin
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.imgProfile.setOnClickListener {
            if (avatar == Constant.default) {
                Utils.toast(requireContext(), "Tidak ada foto profil")
            } else {
                val seeImage =
                    AdminProfileFragmentDirections.actionAdminProfileFragmentToLihatFotoFragment(
                        avatar, name
                    )
                findNavController().navigate(seeImage)
            }
        }

        b.rvChangeProfile.setOnClickListener {

        }

        b.rvTerm.setOnClickListener {
            findNavController().navigate(R.id.action_adminProfileFragment_to_termsConditionsFragment)
        }

        b.rvPrivacy.setOnClickListener {
            findNavController().navigate(R.id.action_adminProfileFragment_to_privacyPolicyFragment)
        }

        b.btnLogout.setOnClickListener {
            dl = DialogLogoutBinding.inflate(
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