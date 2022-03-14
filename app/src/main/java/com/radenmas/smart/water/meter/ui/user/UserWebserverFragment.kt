/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentHomeUserBinding
import com.radenmas.smart.water.meter.databinding.FragmentWebserverUserBinding

class UserWebserverFragment : Fragment() {
    private lateinit var b: FragmentWebserverUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentWebserverUserBinding.inflate(layoutInflater,container,false)
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