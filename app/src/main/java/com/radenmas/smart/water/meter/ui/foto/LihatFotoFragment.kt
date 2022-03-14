/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.foto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.radenmas.smart.water.meter.databinding.FragmentLihatFotoBinding

class LihatFotoFragment : Fragment() {
    private lateinit var b: FragmentLihatFotoBinding

    val args: LihatFotoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentLihatFotoBinding.inflate(layoutInflater, container, false)
        val v = b.root

        Glide.with(this).load(args.image).into(b.imgProfile)
        b.tvFullName.text = args.fullName

        onClick()

        return v
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}