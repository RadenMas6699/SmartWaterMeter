/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.radenmas.smart.water.meter.databinding.FragmentDetailBillingAdminBinding
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.AppUtils
import com.radenmas.smart.water.meter.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminDetailBillingFragment : Fragment() {

    private lateinit var b: FragmentDetailBillingAdminBinding

    var idTagihan: String? = null

    private val args: AdminDetailBillingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentDetailBillingAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        initView()
        onClick()

        return v
    }

    private fun initView() {
        idTagihan = args.idTagihan
        val idPelanggan: String = args.idPelanggan
        val name: String = args.name
        val month: String = args.month
        val year: String = args.year
        val usage: String = args.usage
        val bill: String = args.bill
        val maintenance: String = args.maintenance
        val totalBill: Int = args.totalBill

        b.tvUserID.text = idPelanggan
        b.tvName.text = name
        b.tvPeriod.text = AppUtils.formatPeriod(month, year)
        b.tvUsage.text = AppUtils.formatUsage(usage)
        b.tvUsageCost.text = AppUtils.formatRupiah(bill.toInt())
        b.tvMaintenanceCost.text = AppUtils.formatRupiah(maintenance.toInt())
        b.tvTagihan.text = AppUtils.formatRupiah(totalBill)
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.btnPaidOff.setOnClickListener {


            AppUtils.showLoading(requireContext())
            Retro.instance.updateStatusTagihan(idTagihan!!, Constant.paid_off).enqueue(object :
                Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    AppUtils.dismissLoading()
                    AppUtils.toast(requireContext(), "Tagihan berhasil dilunasi")
                    activity?.onBackPressed()
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                }

            })
        }
    }

}