/*
 * Created by RadenMas on 7/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.radenmas.smart.water.meter.databinding.FragmentPrivacyPolicyBinding
import com.radenmas.smart.water.meter.databinding.FragmentTermsConditionsBinding

/**
 * Created by RadenMas on 07/04/2022.
 */
class TermsConditionsFragment : Fragment() {
    private lateinit var b: FragmentTermsConditionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentTermsConditionsBinding.inflate(layoutInflater, container, false)
        val v = b.root

        initView()
        onClick()

        return v
    }

    private fun initView() {
        b.webViewTermsConditions.loadUrl("https://www.privacypolicyonline.com/live.php?token=NDSBNq54Qkku1r0a7oi2eTfiglA1a3q3")
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}