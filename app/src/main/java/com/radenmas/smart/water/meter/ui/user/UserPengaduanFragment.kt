/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.user

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.adapter.AduanAdapterUser
import com.radenmas.smart.water.meter.databinding.FragmentPengaduanUserBinding
import com.radenmas.smart.water.meter.model.AduanResponse
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.utils.Constant
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPengaduanFragment : Fragment() {
    private lateinit var b: FragmentPengaduanUserBinding
    private lateinit var aduanUser: AduanAdapterUser
    private lateinit var bs: com.radenmas.smart.water.meter.databinding.BottomSheetCreateAduanBinding
    private lateinit var dialog: BottomSheetDialog
    val args: UserPengaduanFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentPengaduanUserBinding.inflate(layoutInflater, container, false)
        val v = b.root

        b.rvAduanUser.layoutManager = LinearLayoutManager(activity)
        aduanUser = AduanAdapterUser(requireActivity())
        b.rvAduanUser.adapter = aduanUser

        onClick()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Utils.showLoading(requireContext())
        getHistoryAll(args.idPelanggan)
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.btnHistoryAll.setOnClickListener {
            getHistoryAll(args.idPelanggan)
            setButtonActive(
                b.btnHistoryAll,
                b.btnHistorySend,
                b.btnHistoryProses,
                b.btnHistoryDone,
                b.btnHistoryReject
            )
        }

        b.btnHistorySend.setOnClickListener {
            getHistory(args.idPelanggan, resources.getString(R.string.sent))
            setButtonActive(
                b.btnHistorySend,
                b.btnHistoryAll,
                b.btnHistoryProses,
                b.btnHistoryDone,
                b.btnHistoryReject
            )
        }

        b.btnHistoryProses.setOnClickListener {
            getHistory(args.idPelanggan, resources.getString(R.string.processed))
            setButtonActive(
                b.btnHistoryProses,
                b.btnHistoryAll,
                b.btnHistorySend,
                b.btnHistoryDone,
                b.btnHistoryReject
            )
        }

        b.btnHistoryDone.setOnClickListener {
            getHistory(args.idPelanggan, resources.getString(R.string.finish))
            setButtonActive(
                b.btnHistoryDone,
                b.btnHistoryAll,
                b.btnHistorySend,
                b.btnHistoryProses,
                b.btnHistoryReject
            )
        }

        b.btnHistoryReject.setOnClickListener {
            getHistory(args.idPelanggan, resources.getString(R.string.rejected))
            setButtonActive(
                b.btnHistoryReject,
                b.btnHistorySend,
                b.btnHistoryAll,
                b.btnHistoryProses,
                b.btnHistoryDone
            )
        }

        b.fabAddAduan.setOnClickListener {
            bs = com.radenmas.smart.water.meter.databinding.BottomSheetCreateAduanBinding.inflate(
                layoutInflater
            )
            val v = bs.root

            dialog = BottomSheetDialog(requireActivity(), R.style.DialogStyle)
            dialog.setCancelable(true)
            dialog.setContentView(v)
            dialog.show()

            bs.imgDismiss.setOnClickListener {
                dialog.dismiss()
            }
            bs.btnSendAduan.setOnClickListener {
                val title: String = bs.etTitleAduan.text.toString()
                val desc: String = bs.etDescAduan.text.toString()
                if (title.isEmpty() || desc.isEmpty()) {
                    Utils.toast(requireContext(),"Lengkapi yang masih kosong")
                } else {
                    createAduan(args.idPelanggan, title, desc, resources.getString(R.string.sent))
                }
            }
        }
    }

    private fun setButtonActive(
        btnActive: MaterialButton,
        btnNotActive1: MaterialButton,
        btnNotActive2: MaterialButton,
        btnNotActive3: MaterialButton,
        btnNotActive4: MaterialButton
    ) {
        btnActive.setTextColor(ResourcesCompat.getColor(resources, R.color.primary_text, null))
        btnActive.strokeColor = ColorStateList.valueOf(Color.parseColor(Constant.color_primary))

        btnNotActive1.setTextColor(ResourcesCompat.getColor(resources, R.color.hint, null))
        btnNotActive1.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))

        btnNotActive2.setTextColor(ResourcesCompat.getColor(resources, R.color.hint, null))
        btnNotActive2.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))

        btnNotActive3.setTextColor(ResourcesCompat.getColor(resources, R.color.hint, null))
        btnNotActive3.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))

        btnNotActive4.setTextColor(ResourcesCompat.getColor(resources, R.color.hint, null))
        btnNotActive4.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))
    }

    private fun getHistory(kode_pelanggan: String, filter: String) {
        Retro.instance.getKeluhanUserFilter(
            kode_pelanggan, filter
        ).enqueue(object : Callback<List<AduanResponse>> {
            override fun onResponse(
                call: Call<List<AduanResponse>>,
                response: Response<List<AduanResponse>>
            ) {
                val dataHistory = response.body()
                for (c in dataHistory!!) {
                    b.rvAduanUser.visibility = View.VISIBLE
                    b.lottieEmpty.visibility = View.INVISIBLE
                    aduanUser.setKeluhan(dataHistory)
                }
                if (dataHistory.isEmpty()) {
                    b.rvAduanUser.visibility = View.INVISIBLE
                    b.lottieEmpty.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<List<AduanResponse>>, t: Throwable) {

            }

        })
    }

    private fun getHistoryAll(kode_pelanggan: String) {
        Retro.instance.getKeluhanUser(
            kode_pelanggan
        ).enqueue(object : Callback<List<AduanResponse>> {
            override fun onResponse(
                call: Call<List<AduanResponse>>,
                response: Response<List<AduanResponse>>
            ) {
                Utils.dismissLoading()
                val dataHistory = response.body()
                for (c in dataHistory!!) {
                    b.rvAduanUser.visibility = View.VISIBLE
                    b.lottieEmpty.visibility = View.INVISIBLE
                    aduanUser.setKeluhan(dataHistory)
                }
                if (dataHistory.isEmpty()) {
                    b.rvAduanUser.visibility = View.INVISIBLE
                    b.lottieEmpty.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<List<AduanResponse>>, t: Throwable) {
            }
        })
    }

    private fun createAduan(
        kodePelanggan: String,
        title: String,
        desc: String,
        status: String
    ) {
        Retro.instance.createKeluhan(
            kodePelanggan, title, desc, status
        ).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                dialog.dismiss()
                Toast.makeText(context, "Aduan Berhasil Dikirim", Toast.LENGTH_SHORT).show()
                with(bs) {
                    etTitleAduan.text.clear()
                    etDescAduan.text.clear()
                }
                getHistoryAll(args.idPelanggan)
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                dialog.dismiss()
                Toast.makeText(context, "Aduan Gagal Dikirim", Toast.LENGTH_SHORT).show()
            }

        })
    }

}