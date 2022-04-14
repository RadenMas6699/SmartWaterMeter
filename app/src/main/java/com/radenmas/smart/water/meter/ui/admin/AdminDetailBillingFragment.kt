/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.DialogPaidOffBinding
import com.radenmas.smart.water.meter.databinding.FragmentDetailBillingAdminBinding
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.Utils
import com.radenmas.smart.water.meter.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AdminDetailBillingFragment : Fragment() {

    private lateinit var b: FragmentDetailBillingAdminBinding

    private lateinit var paidOff: Dialog
    private lateinit var dp: DialogPaidOffBinding

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
        b.tvPeriod.text = Utils.formatPeriod(month, year)
        b.tvUsage.text = Utils.formatUsage(usage)
        b.tvUsageCost.text = Utils.formatRupiah(bill.toInt())
        b.tvMaintenanceCost.text = Utils.formatRupiah(maintenance.toInt())
        b.tvTagihan.text = Utils.formatRupiah(totalBill)
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.btnPaidOff.setOnClickListener {

            val idTagihan: String = args.idTagihan
            val name: String = args.name
            val month: String = args.month
            val year: String = args.year
            val period = Utils.formatPeriod(month, year)

            dp = DialogPaidOffBinding.inflate(
                layoutInflater
            )
            val v = dp.root

            paidOff = Dialog(requireActivity())
            paidOff.setContentView(v)
            paidOff.setCancelable(true)
            paidOff.window!!.setBackgroundDrawableResource(R.drawable.bg_progress)
            paidOff.show()

            val desc = "Apakah $name telah membayar tagihan pada periode $period?"
            dp.tvDescConfirm.text = desc

            dp.btnNo.setOnClickListener {
                paidOff.dismiss()
            }

            dp.btnYes.setOnClickListener {
                var payDate: String? = null
                payDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern(Constant.pattern_input_date)
                    current.format(formatter)
                } else {
                    val date = Date()
                    val formatter = SimpleDateFormat(Constant.pattern_input_date)
                    formatter.format(date)
                }

                paidOff.dismiss()
                Utils.showLoading(requireContext())
                Retro.instance.updateStatusTagihan(
                    idTagihan,
                    Constant.paid_off,
                    payDate
                ).enqueue(object :
                    Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Utils.dismissLoading()
                        Utils.toast(requireContext(), "Tagihan berhasil dilunasi")
                        activity?.onBackPressed()
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    }

                })
            }
        }
    }

}