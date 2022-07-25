/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentSettingAdminBinding
import com.radenmas.smart.water.meter.network.Config
import com.radenmas.smart.water.meter.utils.Utils

class AdminSettingFragment : Fragment() {

    private lateinit var b: FragmentSettingAdminBinding

//    private lateinit var confirm: Dialog
//    private lateinit var dl: DialogConfirmBinding

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
            val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.bottom_sheet_callibration_water)
            dialog.show()

            val imgDismiss: ImageView? = dialog.findViewById(R.id.imgDismiss)
            val etValue: TextView? = dialog.findViewById(R.id.etValue)
            val btnSave: TextView? = dialog.findViewById(R.id.btnSave)

            imgDismiss?.setOnClickListener {
                dialog.dismiss()
            }

            btnSave?.setOnClickListener {
                val value = etValue?.text.toString()
                if (value.isBlank()) {
                    Utils.toast(requireContext(), "Masukkan Nilai Kalibrasi")
                } else {
                    val floatValue = value.toFloat()

                    val requestQueue = Volley.newRequestQueue(context)
                    val stringRequest = StringRequest(
                        Request.Method.GET,
                        Config.URL_SET_CALIBRATION + floatValue,
                        {
                            Utils.toast(requireContext(), it.toString())
                            dialog.dismiss()
                        },
                        {
                            Utils.toast(requireContext(), "Gagal Atur Kalibrasi Air")
                            dialog.dismiss()
                        })
                    requestQueue.add(stringRequest)
                }
            }
        }

        b.btnReset.setOnClickListener {

            val requestQueue = Volley.newRequestQueue(context)
            val stringRequest = StringRequest(
                Request.Method.GET,
                Config.URL_RESET,
                {
                    Utils.toast(requireContext(), "Berhasil Mereset Data Air")
                },
                {
                    Utils.toast(requireContext(), "Gagal Mereset Data Air")
                })
            requestQueue.add(stringRequest)

//            dl = DialogConfirmBinding.inflate(
//                layoutInflater
//            )
//            val v = dl.root
//
//            confirm = Dialog(requireContext())
//            confirm.setContentView(v)
//            confirm.setCancelable(false)
//            confirm.window!!.setBackgroundDrawableResource(R.drawable.bg_progress)
//            confirm.show()
//
//            dl.tvDescConfirm.text = resources.getString(R.string.desc_reset)
//
//            dl.btnNo.setOnClickListener {
//                confirm.dismiss()
//            }
//
//            dl.btnYes.setOnClickListener {
//                val requestQueue = Volley.newRequestQueue(context)
//                val stringRequest = StringRequest(
//                    Request.Method.GET,
//                    Config.URL_RESET,
//                    {
//                        Utils.toast(requireContext(), "Berhasil Mereset Data Air")
//                    },
//                    {
//                        Utils.toast(requireContext(), "Gagal Mereset Data Air")
//                    })
//                requestQueue.add(stringRequest)
//                confirm.dismiss()
//            }
        }
    }

}