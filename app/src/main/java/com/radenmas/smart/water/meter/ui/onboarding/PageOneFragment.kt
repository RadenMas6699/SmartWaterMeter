/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.radenmas.smart.water.meter.databinding.FragmentAddUserAdminBinding

class PageOneFragment : Fragment() {
    private lateinit var b: FragmentAddUserAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentAddUserAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        onClick()

        return v
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}