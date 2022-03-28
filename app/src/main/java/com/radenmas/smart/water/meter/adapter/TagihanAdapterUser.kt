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
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.model.TagihanResponse
import com.radenmas.smart.water.meter.utils.AppUtils
import com.radenmas.smart.water.meter.utils.Constant

class TagihanAdapterUser(val context: Context) :
    RecyclerView.Adapter<TagihanAdapterUser.TagihanViewHolder>() {
    private val tagihan: MutableList<TagihanResponse> = mutableListOf()

    inner class TagihanViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val imgBill: ImageView = item.findViewById(R.id.imgBill)
        private val tvTitle: TextView = item.findViewById(R.id.tvTitle)
        private val tvStatues: TextView = item.findViewById(R.id.tvStatues)
        private val tvPrice: TextView = item.findViewById(R.id.tvPrice)

        fun bindTagihan(b: TagihanResponse) {

            val year = b.year
            val period: String
            when (b.month) {
                "1" -> {
                    period = "Januari $year"
                    tvTitle.text = period
                }
                "2" -> {
                    period = "Februari $year"
                    tvTitle.text = period
                }
                "3" -> {
                    period = "Maret $year"
                    tvTitle.text = period
                }
                "4" -> {
                    period = "April $year"
                    tvTitle.text = period
                }
                "5" -> {
                    period = "Mei $year"
                    tvTitle.text = period
                }
                "6" -> {
                    period = "Juni $year"
                    tvTitle.text = period
                }
                "7" -> {
                    period = "Juli $year"
                    tvTitle.text = period
                }
                "8" -> {
                    period = "Agustus $year"
                    tvTitle.text = period
                }
                "9" -> {
                    period = "September $year"
                    tvTitle.text = period
                }
                "10" -> {
                    period = "Oktober $year"
                    tvTitle.text = period
                }
                "11" -> {
                    period = "November $year"
                    tvTitle.text = period
                }
                "12" -> {
                    period = "Desember $year"
                    tvTitle.text = period
                }
            }
            tvPrice.text = AppUtils.formatRupiah(b.total_bill)

            tvStatues.text = b.status
            when (b.status) {
                Constant.paid_off -> {
                    Glide.with(context)
                        .load(R.drawable.ic_bill_paid_off)
                        .into(imgBill)
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_green)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_green))
                }
                Constant.not_yet_paid_off -> {
                    Glide.with(context)
                        .load(R.drawable.ic_bill_not_yet_paid_off)
                        .into(imgBill)
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_red)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_red))
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
        holder.itemView.setOnClickListener {
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

            val year = tagihan[position].year
            val detPeriod: String
            when (tagihan[position].month) {
                "1" -> {
                    detPeriod = "Januari $year"
                    tvPeriod?.text = detPeriod
                }
                "2" -> {
                    detPeriod = "Februari $year"
                    tvPeriod?.text = detPeriod
                }
                "3" -> {
                    detPeriod = "Maret $year"
                    tvPeriod?.text = detPeriod
                }
                "4" -> {
                    detPeriod = "April $year"
                    tvPeriod?.text = detPeriod
                }
                "5" -> {
                    detPeriod = "Mei $year"
                    tvPeriod?.text = detPeriod
                }
                "6" -> {
                    detPeriod = "Juni $year"
                    tvPeriod?.text = detPeriod
                }
                "7" -> {
                    detPeriod = "Juli $year"
                    tvPeriod?.text = detPeriod
                }
                "8" -> {
                    detPeriod = "Agustus $year"
                    tvPeriod?.text = detPeriod
                }
                "9" -> {
                    detPeriod = "September $year"
                    tvPeriod?.text = detPeriod
                }
                "10" -> {
                    detPeriod = "Oktober $year"
                    tvPeriod?.text = detPeriod
                }
                "11" -> {
                    detPeriod = "November $year"
                    tvPeriod?.text = detPeriod
                }
                "12" -> {
                    detPeriod = "Desember $year"
                    tvPeriod?.text = detPeriod
                }
            }

            val usage = tagihan[position].usage
            val usage_air = "$usage M3"
            tvPemakaian?.text = usage_air

            tvTagihan?.text = AppUtils.formatRupiah(tagihan[position].total_bill)
            tvUsageCost?.text = AppUtils.formatRupiah(tagihan[position].bill.toInt())
            tvMaintenanceCost?.text = AppUtils.formatRupiah(tagihan[position].maintenance.toInt())

            when (tagihan[position].status) {
                Constant.paid_off -> {
                    tvPayDate?.text = AppUtils.formatDate(tagihan[position].pay_date)
                }
                Constant.not_yet_paid_off -> {
                    tvPayDate?.text = Constant.dash
                }
            }

            imgDismiss?.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    override fun getItemCount(): Int {
        return tagihan.size
    }

}