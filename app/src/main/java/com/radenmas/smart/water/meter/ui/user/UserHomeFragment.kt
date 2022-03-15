/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.user

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentHomeUserBinding
import com.radenmas.smart.water.meter.network.Constant

class UserHomeFragment : Fragment() {
    private lateinit var b: FragmentHomeUserBinding

    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentHomeUserBinding.inflate(layoutInflater, container, false)
        val v = b.root

        sharedPref = activity?.getSharedPreferences(
            getString(R.string.app_pref), Context.MODE_PRIVATE
        )!!

        initView()
        onClick()

        return v
    }

    private fun initView() {
        Glide.with(this).load(sharedPref.getString("image", null)).into(b.imgProfile)
        b.tvFullName.text = sharedPref.getString("nama", null)
    }

    private fun onClick() {
        b.imgProfile.setOnClickListener {
            findNavController().navigate(R.id.action_userHomeFragment_to_userProfileFragment)
        }
        b.imgWebServer.setOnClickListener {
            findNavController().navigate(R.id.action_userHomeFragment_to_userWebserverFragment)
        }
        b.imgNotif.setOnClickListener {
            findNavController().navigate(R.id.action_userHomeFragment_to_userNotificationFragment)
        }
        b.btnDay.setOnClickListener {
            setButtonActive(
                b.btnDay,
                b.btnWeek,
                b.btnMonth
            )
        }
        b.btnWeek.setOnClickListener {
            setButtonActive(
                b.btnWeek,
                b.btnDay,
                b.btnMonth
            )
        }
        b.btnMonth.setOnClickListener {
            setButtonActive(
                b.btnMonth,
                b.btnDay,
                b.btnWeek
            )
        }

        b.tvViewAll.setOnClickListener {
            findNavController().navigate(R.id.action_userHomeFragment_to_userPaymentFragment)
        }
    }

    private fun setButtonActive(
        btnActive: MaterialButton,
        btnNotActive1: MaterialButton,
        btnNotActive2: MaterialButton
    ) {
        btnActive.setTextColor(resources.getColor(R.color.primary_text))
        btnActive.strokeColor = ColorStateList.valueOf(Color.parseColor(Constant.color_primary))

        btnNotActive1.setTextColor(resources.getColor(R.color.hint))
        btnNotActive1.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))

        btnNotActive2.setTextColor(resources.getColor(R.color.hint))
        btnNotActive2.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))
    }

}