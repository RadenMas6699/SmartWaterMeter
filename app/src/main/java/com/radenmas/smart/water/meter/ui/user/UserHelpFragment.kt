/*
 * Created by RadenMas on 10/4/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.user

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentHelpUserBinding


class UserHelpFragment : Fragment() {
    private lateinit var b: FragmentHelpUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentHelpUserBinding.inflate(layoutInflater, container, false)
        val v = b.root

        onClick()

        return v
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.viewWebsite.setOnClickListener {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(resources.getString(R.string.url_smartpdam))
            )
            startActivity(webIntent)
        }

        b.viewEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("radenmas.dev@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Aduan Smart Water Meter")
                putExtra(Intent.EXTRA_TEXT, "Halo Admin Smart Water Meter")
            }
            startActivity(emailIntent)
        }

        b.viewPhone.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:085298106699"))
            startActivity(callIntent)
        }

        b.viewWhatsApp.setOnClickListener {
            val linkChat =
                "https://wa.me/6285298106699?text=Halo%20Admin%20Smart%20Water%20Meter"
            val chatWhatsApp = Intent(Intent.ACTION_VIEW, Uri.parse(linkChat))
            startActivity(chatWhatsApp)
        }
    }

}