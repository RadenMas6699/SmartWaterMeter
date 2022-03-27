/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.user

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
import com.radenmas.smart.water.meter.databinding.FragmentProfileUserBinding
import com.radenmas.smart.water.meter.databinding.LogoutLayoutBinding
import com.radenmas.smart.water.meter.ui.auth.AuthActivity
import com.radenmas.smart.water.meter.utils.Constant


class UserProfileFragment : Fragment() {

    private lateinit var b: FragmentProfileUserBinding
    private lateinit var logout: Dialog
    private lateinit var dl: LogoutLayoutBinding

    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentProfileUserBinding.inflate(layoutInflater, container, false)
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
        Glide.with(this).load(sharedPref.getString(Constant.data_avatar, null)).into(b.imgProfile)
        b.tvFullName.text = sharedPref.getString(Constant.data_name, null)
        b.tvUserID.text = sharedPref.getString(Constant.data_id_pelanggan, null)
        b.tvUserPhone.text = sharedPref.getString(Constant.data_phone, null)
        b.tvUserAddress.text = sharedPref.getString(Constant.data_address, null)
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.imgProfile.setOnClickListener {
            val lihatFoto =
                UserProfileFragmentDirections.actionUserProfileFragmentToLihatFotoFragment(
                    sharedPref.getString(Constant.data_avatar, null).toString(),
                    sharedPref.getString(Constant.data_name, null).toString(),
                )
            findNavController().navigate(lihatFoto)
        }

        b.rvChangeProfile.setOnClickListener {
            val changeProfile =
                UserProfileFragmentDirections.actionUserProfileFragmentToUserChangeProfileFragment(
                    sharedPref.getString(Constant.data_id_pelanggan, null).toString(),
                    sharedPref.getString(Constant.data_avatar, null).toString(),
                    sharedPref.getString(Constant.data_name, null).toString(),
                    sharedPref.getString(Constant.data_phone, null).toString(),
                    sharedPref.getString(Constant.data_address, null).toString()
                )
            findNavController().navigate(changeProfile)
        }

        b.rvGuide.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_guideFragment)
        }

        b.rvHelp.setOnClickListener {
            val aduan =
                UserProfileFragmentDirections.actionUserProfileFragmentToUserPengaduanFragment(
                    sharedPref.getString(Constant.data_id_pelanggan, null).toString()
                )
            findNavController().navigate(aduan)
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