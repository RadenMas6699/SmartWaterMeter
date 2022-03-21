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
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.model.TagihanResponse
import com.radenmas.smart.water.meter.utils.Constant

class TagihanAdapterUser(val context: Context) :
    RecyclerView.Adapter<TagihanAdapterUser.TagihanViewHolder>() {
    private val tagihan: MutableList<TagihanResponse> = mutableListOf()

    inner class TagihanViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val rlTagihan: RelativeLayout = item.findViewById(R.id.rlTagihan)
        private val tvTitle: TextView = item.findViewById(R.id.tvTitle)
        private val tvDesc: TextView = item.findViewById(R.id.tvDesc)
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
                    period = "Desember "
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

//            rlTagihan.setOnClickListener {
//                val dialog = BottomSheetDialog(context, R.style.DialogStyle)
//                dialog.setCancelable(true)
//                dialog.setContentView(R.layout.bottom_sheet_detail_aduan_user)
//                dialog.show()
//
//                val tvTitleAduan: TextView? = dialog.findViewById(R.id.tvTitleAduan)
//                val etDescAduan: TextView? = dialog.findViewById(R.id.etDescAduan)
//                val tvStatues: TextView? = dialog.findViewById(R.id.tvStatues)
//                val imgDismiss: ImageView? = dialog.findViewById(R.id.imgDismiss)
//
//                tvTitleAduan?.text = b.title
//                etDescAduan?.text = b.desc
//                tvStatues?.text = b.status
//
//                imgDismiss?.setOnClickListener {
//                    dialog.dismiss()
//                }
//            }
        }
    }

    fun setTagihan(data: List<TagihanResponse>) {
        tagihan.clear()
        tagihan.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagihanViewHolder {
        return TagihanViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_payment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TagihanViewHolder, position: Int) {
        holder.bindTagihan(tagihan[position])
    }

    override fun getItemCount(): Int {
        return tagihan.size
    }

}