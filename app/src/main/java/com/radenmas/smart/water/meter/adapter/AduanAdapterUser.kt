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
import com.radenmas.smart.water.meter.model.AduanResponse
import com.radenmas.smart.water.meter.utils.Constant

class AduanAdapterUser(val context: Context) :
    RecyclerView.Adapter<AduanAdapterUser.HistoryViewHolder>() {

    private val history: MutableList<AduanResponse> = mutableListOf()

    inner class HistoryViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val rlAduan: RelativeLayout = item.findViewById(R.id.rlAduan)
        private val tvTitle: TextView = item.findViewById(R.id.tvTitle)
        private val tvDesc: TextView = item.findViewById(R.id.tvDesc)
        private val tvDate: TextView = item.findViewById(R.id.tvDate)
        private val tvStatues: TextView = item.findViewById(R.id.tvStatues)

        fun bindKeluhan(b: AduanResponse) {
            tvTitle.text = b.title
            tvDesc.text = b.desc

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//                val date = LocalDate.parse(b.waktu.toString(), firstApiFormat)
////                tvDate.text = date.toString()
//            } else {
////                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
////                val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
////                val date: String = formatter.format(parser.parse(b.waktu))
//
//            }

            tvDate.text = b.waktu

            tvStatues.text = b.status
            when {
                b.status.equals(Constant.sent) -> {
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_green)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_green))
                }
                b.status.equals(Constant.processed) -> {
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_orange)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_orange))
                }
                b.status.equals(Constant.finish) -> {
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_blue)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_primary))
                }
                b.status.equals(Constant.rejected) -> {
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_red)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_red))
                }
            }

            rlAduan.setOnClickListener {
                val dialog = BottomSheetDialog(context, R.style.DialogStyle)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.bottom_sheet_detail_aduan_user)
                dialog.show()

                val tvTitleAduan: TextView? = dialog.findViewById(R.id.tvTitleAduan)
                val etDescAduan: TextView? = dialog.findViewById(R.id.etDescAduan)
                val tvStatues: TextView? = dialog.findViewById(R.id.tvStatues)
                val imgDismiss: ImageView? = dialog.findViewById(R.id.imgDismiss)

                tvTitleAduan?.text = b.title
                etDescAduan?.text = b.desc
                tvStatues?.text = b.status

                when {
                    b.status.equals(Constant.sent) -> {
                        tvStatues?.setBackgroundResource(R.drawable.bg_statues_green)
                        tvStatues?.setTextColor(Color.parseColor(Constant.color_green))
                    }
                    b.status.equals(Constant.processed) -> {
                        tvStatues?.setBackgroundResource(R.drawable.bg_statues_orange)
                        tvStatues?.setTextColor(Color.parseColor(Constant.color_orange))
                    }
                    b.status.equals(Constant.finish) -> {
                        tvStatues?.setBackgroundResource(R.drawable.bg_statues_blue)
                        tvStatues?.setTextColor(Color.parseColor(Constant.color_primary))
                    }
                    b.status.equals(Constant.rejected) -> {
                        tvStatues?.setBackgroundResource(R.drawable.bg_statues_red)
                        tvStatues?.setTextColor(Color.parseColor(Constant.color_red))
                    }
                }

                imgDismiss?.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
    }

    fun setKeluhan(data: List<AduanResponse>) {
        history.clear()
        history.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_aduan_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bindKeluhan(history[position])
    }

    override fun getItemCount(): Int {
        return history.size
    }

}