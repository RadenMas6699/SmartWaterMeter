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
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.model.AduanResponse
import com.radenmas.smart.water.meter.utils.Constant

class AduanAdapterAdmin(val context: Context) :
    RecyclerView.Adapter<AduanAdapterAdmin.HistoryViewHolder>() {

    private val history: MutableList<AduanResponse> = mutableListOf()

    inner class HistoryViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val tvTitle: TextView = item.findViewById(R.id.tvTitle)
        private val tvUserID: TextView = item.findViewById(R.id.tvUserID)
        private val tvDesc: TextView = item.findViewById(R.id.tvDesc)
        private val tvDate: TextView = item.findViewById(R.id.tvDate)
        private val tvStatues: TextView = item.findViewById(R.id.tvStatues)

        fun bindKeluhan(b: AduanResponse) {
            tvTitle.text = b.title
            tvUserID.text = b.kode_pelanggan
            tvDesc.text = b.desc
            tvDate.text = b.waktu

            tvStatues.text = b.status
            when {
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
        }
    }

    fun setKeluhan(data: List<AduanResponse>) {
        history.clear()
        history.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_aduan_admin, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bindKeluhan(history[position])
    }

    override fun getItemCount(): Int {
        return history.size
    }

}