/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentDetailUserAdminBinding
import com.radenmas.smart.water.meter.utils.Utils
import com.radenmas.smart.water.meter.utils.Constant

class AdminDetailUserFragment : Fragment() {

    private lateinit var b: FragmentDetailUserAdminBinding

    private val args: AdminDetailUserFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentDetailUserAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        initView()
        onClick()

        return v
    }

    private fun initView() {
        val avatar: String = args.avatar
        val name: String = args.name
        val idPelanggan: String = args.idPelanggan
        val ktp: String = args.ktp
        val phone: String = args.phone
        val address: String = args.address
        val registered: String = args.registered

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
        b.tvUserID.text = idPelanggan
        b.tvUserKTP.text = ktp
        b.tvUserPhone.text = phone
        b.tvUserAddress.text = address
        b.tvUserRegistered.text = Utils.formatDate(registered)
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
        b.rlAddress.setOnClickListener {
            val addr = b.tvUserAddress.text.toString()
            val gmmIntentUri = Uri.parse("geo:0,0?q=$addr")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

}