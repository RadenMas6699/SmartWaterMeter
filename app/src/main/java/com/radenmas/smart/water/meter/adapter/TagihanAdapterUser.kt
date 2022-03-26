/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.model.TagihanResponse
import com.radenmas.smart.water.meter.utils.Constant
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TagihanAdapterUser(val context: Context) :
    RecyclerView.Adapter<TagihanAdapterUser.TagihanViewHolder>() {
    private val tagihan: MutableList<TagihanResponse> = mutableListOf()

    inner class TagihanViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val rlTagihan: RelativeLayout = item.findViewById(R.id.rlTagihan)
        private val tvTitle: TextView = item.findViewById(R.id.tvTitle)
        private val tvStatues: TextView = item.findViewById(R.id.tvStatues)
        private val tvPrice: TextView = item.findViewById(R.id.tvPrice)

        fun bindTagihan(b: TagihanResponse) {

            val year = b.tahun
            val period: String
            when {
                b.bulan.equals("1") -> {
                    period = "Januari $year"
                    tvTitle.text = period
                }
                b.bulan.equals("2") -> {
                    period = "Februari $year"
                    tvTitle.text = period
                }
                b.bulan.equals("3") -> {
                    period = "Maret $year"
                    tvTitle.text = period
                }
                b.bulan.equals("4") -> {
                    period = "April $year"
                    tvTitle.text = period
                }
                b.bulan.equals("5") -> {
                    period = "Mei $year"
                    tvTitle.text = period
                }
                b.bulan.equals("6") -> {
                    period = "Juni $year"
                    tvTitle.text = period
                }
                b.bulan.equals("7") -> {
                    period = "Juli $year"
                    tvTitle.text = period
                }
                b.bulan.equals("8") -> {
                    period = "Agustus $year"
                    tvTitle.text = period
                }
                b.bulan.equals("9") -> {
                    period = "September $year"
                    tvTitle.text = period
                }
                b.bulan.equals("10") -> {
                    period = "Oktober $year"
                    tvTitle.text = period
                }
                b.bulan.equals("11") -> {
                    period = "November $year"
                    tvTitle.text = period
                }
                b.bulan.equals("12") -> {
                    period = "Desember $year"
                    tvTitle.text = period
                }
            }

            val tagihan: String =
                String.format("%,d", b.total)
            val total = "Rp. $tagihan"
            tvPrice.text = total

            tvStatues.text = b.status
            when {
                b.status.equals(Constant.paid_off) -> {
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_green)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_green))
                }
                b.status.equals(Constant.not_yet_paid_off) -> {
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_red)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_red))
                }
            }

            rlTagihan.setOnClickListener {
                val dialog = BottomSheetDialog(context, R.style.DialogStyle)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.bottom_sheet_detail_billing_user)
                dialog.show()

                val tvPeriod: TextView? = dialog.findViewById(R.id.tvPeriod)
                val tvPemakaian: TextView? = dialog.findViewById(R.id.tvPemakaian)
                val tvTagihan: TextView? = dialog.findViewById(R.id.tvTagihan)
                val tvUsageCost: TextView? = dialog.findViewById(R.id.tvUsageCost)
                val tvMaintenanceCost: TextView? = dialog.findViewById(R.id.tvMaintenanceCost)
                val tvPayDate: TextView? = dialog.findViewById(R.id.tvPayDate)
                val imgDismiss: ImageView? = dialog.findViewById(R.id.imgDismiss)

                val detPeriod: String
                when {
                    b.bulan.equals("1") -> {
                        detPeriod = "Januari $year"
                        tvPeriod?.text = detPeriod
                    }
                    b.bulan.equals("2") -> {
                        detPeriod = "Februari $year"
                        tvPeriod?.text = detPeriod
                    }
                    b.bulan.equals("3") -> {
                        detPeriod = "Maret $year"
                        tvPeriod?.text = detPeriod
                    }
                    b.bulan.equals("4") -> {
                        detPeriod = "April $year"
                        tvPeriod?.text = detPeriod
                    }
                    b.bulan.equals("5") -> {
                        detPeriod = "Mei $year"
                        tvPeriod?.text = detPeriod
                    }
                    b.bulan.equals("6") -> {
                        detPeriod = "Juni $year"
                        tvPeriod?.text = detPeriod
                    }
                    b.bulan.equals("7") -> {
                        detPeriod = "Juli $year"
                        tvPeriod?.text = detPeriod
                    }
                    b.bulan.equals("8") -> {
                        detPeriod = "Agustus $year"
                        tvPeriod?.text = detPeriod
                    }
                    b.bulan.equals("9") -> {
                        detPeriod = "September $year"
                        tvPeriod?.text = detPeriod
                    }
                    b.bulan.equals("10") -> {
                        detPeriod = "Oktober $year"
                        tvPeriod?.text = detPeriod
                    }
                    b.bulan.equals("11") -> {
                        detPeriod = "November $year"
                        tvPeriod?.text = detPeriod
                    }
                    b.bulan.equals("12") -> {
                        detPeriod = "Desember $year"
                        tvPeriod?.text = detPeriod
                    }
                }

                tvPemakaian?.text = b.pemakaian
                tvTagihan?.text = total

                val usageCost: String =
                    String.format("%,d", b.tagihan.toString().toInt())
                val usage = "Rp. $usageCost"
                tvUsageCost?.text = usage

                val maintenanceCost: String =
                    String.format("%,d", b.maintenance.toString().toInt())
                val maintenance = "Rp. $maintenanceCost"
                tvMaintenanceCost?.text = maintenance

                when {
                    b.status.equals(Constant.paid_off) -> {
                        val inputFormat: DateFormat = SimpleDateFormat(
                            Constant.pattern_input_date,
                            Locale("ID")
                        )
                        val outputFormat: DateFormat =
                            SimpleDateFormat(Constant.pattern_output_date, Locale("ID"))
                        val inputDateStr = b.tgl_bayar.toString()
                        val date: Date? = inputFormat.parse(inputDateStr)
                        val outputDateStr: String = outputFormat.format(date)
                        tvPayDate?.text = outputDateStr

                    }
                    b.status.equals(Constant.not_yet_paid_off) -> {
                        tvPayDate?.text = "-"
                    }
                }

                imgDismiss?.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
    }

    fun setTagihan(data: List<TagihanResponse>) {
        tagihan.clear()
        tagihan.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagihanViewHolder {
        return TagihanViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_billing, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TagihanViewHolder, position: Int) {
        holder.bindTagihan(tagihan[position])
    }

    override fun getItemCount(): Int {
        return tagihan.size
    }

}