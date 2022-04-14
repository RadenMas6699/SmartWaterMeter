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
import com.radenmas.smart.water.meter.databinding.FragmentSettingAdminBinding
import com.radenmas.smart.water.meter.utils.Utils

class AdminSettingFragment : Fragment() {

    private lateinit var b: FragmentSettingAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentSettingAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        onClick()

        return v
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.calibrationWater.setOnClickListener {
            Utils.toast(requireContext(),"Kalibrasi Smart Water Meter")
        }

        b.btnReset.setOnClickListener {
            Utils.toast(requireContext(),"Reset Smart Water Meter")
        }
    }

}