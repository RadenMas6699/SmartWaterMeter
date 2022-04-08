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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.model.TagihanResponse
import com.radenmas.smart.water.meter.ui.admin.AdminBillingFragmentDirections
import com.radenmas.smart.water.meter.utils.AppUtils
import com.radenmas.smart.water.meter.utils.Constant
import de.hdodenhof.circleimageview.CircleImageView

class TagihanAdapterAdmin(val context: Context) :
    RecyclerView.Adapter<TagihanAdapterAdmin.TagihanViewHolder>() {
    private val tagihan: MutableList<TagihanResponse> = mutableListOf()

    inner class TagihanViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val imgBill: CircleImageView = item.findViewById(R.id.imgBill)
        private val tvTitle: TextView = item.findViewById(R.id.tvTitle)
        private val tvDesc: TextView = item.findViewById(R.id.tvDesc)
        private val tvStatues: TextView = item.findViewById(R.id.tvStatues)
        private val tvPrice: TextView = item.findViewById(R.id.tvPrice)

        fun bindTagihan(b: TagihanResponse) {

            if (b.avatar == Constant.default) {
                Glide.with(context)
                    .load(R.drawable.ic_profile_default)
                    .into(imgBill)
            } else {
                Glide.with(context)
                    .load(b.avatar)
                    .into(imgBill)
            }

            tvTitle.text = b.id_pelanggan
            tvDesc.text = AppUtils.formatPeriod(b.month,b.year)
            tvPrice.text = AppUtils.formatRupiah(b.total_bill)
            tvStatues.text = b.status
            tvStatues.setBackgroundResource(R.drawable.bg_statues_red)
            tvStatues.setTextColor(Color.parseColor(Constant.color_red))
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
            val detailBilling =
                AdminBillingFragmentDirections.actionAdminBillingFragmentToAdminDetailBillingFragment(
                    tagihan[position].id_tagihan,
                    tagihan[position].id_pelanggan,
                    tagihan[position].name,
                    tagihan[position].month,
                    tagihan[position].year,
                    tagihan[position].usage,
                    tagihan[position].bill,
                    tagihan[position].maintenance,
                    tagihan[position].total_bill
                )
            Navigation.createNavigateOnClickListener(detailBilling).onClick(holder.itemView)
        }
    }

    override fun getItemCount(): Int {
        return tagihan.size
    }

}