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

/**
 * Created by RadenMas on 07/04/2022.
 */
class PrivacyPolicyFragment : Fragment() {
    private lateinit var b: FragmentPrivacyPolicyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentPrivacyPolicyBinding.inflate(layoutInflater, container, false)
        val v = b.root

        initView()
        onClick()

        return v
    }

    private fun initView() {
        b.webViewPrivacyPolicy.loadUrl("https://www.privacypolicyonline.com/live.php?token=71mMGeupFKxMRwJGwoquRZ8pGg6c23yi")
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}