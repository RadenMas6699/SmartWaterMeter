/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.user

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.adapter.AduanAdapterUser
import com.radenmas.smart.water.meter.databinding.FragmentPengaduanUserBinding
import com.radenmas.smart.water.meter.model.AduanResponse
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Retro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPengaduanFragment : Fragment() {
    private lateinit var b: FragmentPengaduanUserBinding
    private lateinit var aduanUser: AduanAdapterUser
    private lateinit var bs: com.radenmas.smart.water.meter.databinding.BottomSheetAduanBinding
    private lateinit var dialog: BottomSheetDialog
    val args: UserPengaduanFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        getHistoryAll(args.idPelanggan)
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.btnHistoryAll.setOnClickListener {
            getHistoryAll(args.idPelanggan)
        }

        b.btnHistorySend.setOnClickListener {
            getHistory(args.idPelanggan, resources.getString(R.string.sent))
        }

        b.btnHistoryProses.setOnClickListener {
            getHistory(args.idPelanggan, resources.getString(R.string.processed))
        }

        b.btnHistoryDone.setOnClickListener {
            getHistory(args.idPelanggan, resources.getString(R.string.finish))
        }

        b.btnHistoryReject.setOnClickListener {
            getHistory(args.idPelanggan, resources.getString(R.string.rejected))
        }

        b.fabAddAduan.setOnClickListener {
            bs = com.radenmas.smart.water.meter.databinding.BottomSheetAduanBinding.inflate(
                layoutInflater
            )
            val v = bs.root

            dialog = BottomSheetDialog(requireActivity(), R.style.DialogStyle)
            dialog.setCancelable(false)
            dialog.setContentView(v)
            dialog.show()

            bs.imgDismiss.setOnClickListener {
                dialog.dismiss()
            }
            bs.btnSendAduan.setOnClickListener {
                val title: String = bs.etTitleAduan.text.toString().trim()
                val desc: String = bs.etDescAduan.text.toString().trim()
                if (title.isEmpty() || desc.isEmpty()) {
                    Toast.makeText(context, "Lengkapi yang masih kosong", Toast.LENGTH_SHORT).show()
                } else {
                    createAduan(args.idPelanggan, title, desc, resources.getString(R.string.sent))
                }
            }

        }
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
                if (response.body()?.message.toString() == "Success") {
                    Toast.makeText(context, "Aduan berhasil dikirim", Toast.LENGTH_SHORT).show()
                    with(bs) {
                        etTitleAduan.text.clear()
                        etDescAduan.text.clear()
                    }
                    dialog.dismiss()
                    getHistoryAll(args.idPelanggan)
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(context, "Aduan gagal dikirim", Toast.LENGTH_SHORT).show()
            }

        })
    }

}