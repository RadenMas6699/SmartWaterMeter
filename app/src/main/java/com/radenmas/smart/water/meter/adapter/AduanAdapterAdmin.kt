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
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.model.AduanResponse
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.Constant
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AduanAdapterAdmin(val context: Context) :
    RecyclerView.Adapter<AduanAdapterAdmin.HistoryViewHolder>() {

    private val history: MutableList<AduanResponse> = mutableListOf()

    inner class HistoryViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val imgUser: CircleImageView = item.findViewById(R.id.imgUser)
        private val tvTitle: TextView = item.findViewById(R.id.tvTitle)
        private val tvUserID: TextView = item.findViewById(R.id.tvUserID)
        private val tvDesc: TextView = item.findViewById(R.id.tvDesc)
        private val tvDate: TextView = item.findViewById(R.id.tvDate)
        private val tvStatues: TextView = item.findViewById(R.id.tvStatues)
        private val rlAduanAdmin: RelativeLayout = item.findViewById(R.id.rlAduanAdmin)

        fun bindKeluhan(b: AduanResponse) {
            if (b.image.equals(Constant.default)) {
                Glide.with(context)
                    .load(R.drawable.img_user_default)
                    .into(imgUser)
            } else {
                Glide.with(context)
                    .load(b.image)
                    .into(imgUser)
            }

            tvTitle.text = b.title
            tvUserID.text = b.kode_pelanggan
            tvDesc.text = b.desc

            val inputFormat: DateFormat = SimpleDateFormat(
                Constant.pattern_input_date, Locale("ID")
            )
            val outputFormat: DateFormat =
                SimpleDateFormat(Constant.pattern_output_date, Locale("ID"))
            val inputDateStr = b.waktu.toString()
            val date: Date? = inputFormat.parse(inputDateStr)
            val outputDateStr: String = outputFormat.format(date)
            tvDate.text = outputDateStr

            tvStatues.text = b.status
            when {
                b.status.equals(Constant.sent) -> {
                    tvStatues.visibility = View.GONE
                }
                b.status.equals(Constant.processed) -> {
                    tvStatues.visibility = View.VISIBLE
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_orange)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_orange))
                }
                b.status.equals(Constant.finish) -> {
                    tvStatues.visibility = View.VISIBLE
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_blue)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_primary))
                }
                b.status.equals(Constant.rejected) -> {
                    tvStatues.visibility = View.VISIBLE
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_red)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_red))
                }
            }

            rlAduanAdmin.setOnClickListener {
                val dialog = BottomSheetDialog(context, R.style.DialogStyle)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.bottom_sheet_detail_aduan_admin)
                dialog.show()

                val imgProfile: CircleImageView = dialog.findViewById(R.id.imgProfile)!!
                val tvFullName: TextView? = dialog.findViewById(R.id.tvFullName)
                val tvUserID: TextView? = dialog.findViewById(R.id.tvUserID)
                val tvTitleAduan: TextView? = dialog.findViewById(R.id.tvTitleAduan)
                val etDescAduan: TextView? = dialog.findViewById(R.id.etDescAduan)
                val llConfirm: LinearLayout? = dialog.findViewById(R.id.llConfirm)
                val btnReject: MaterialButton? = dialog.findViewById(R.id.btnReject)
                val btnAccept: MaterialButton? = dialog.findViewById(R.id.btnAccept)

                if (b.image.equals(Constant.default)) {
                    Glide.with(context)
                        .load(R.drawable.img_user_default)
                        .into(imgProfile)
                } else {
                    Glide.with(context)
                        .load(b.image)
                        .into(imgProfile)
                }

                tvFullName?.text = b.nama_pelanggan
                tvUserID?.text = b.kode_pelanggan
                tvTitleAduan?.text = b.title
                etDescAduan?.text = b.desc

                if (b.status.equals(Constant.sent)) {
                    llConfirm?.visibility = View.VISIBLE
                } else {
                    llConfirm?.visibility = View.GONE
                }

                btnAccept?.setOnClickListener {
                    Retro.instance.updateStatusKeluhan(
                        b.kode_keluhan.toString(), Constant.processed
                    ).enqueue(object : Callback<DefaultResponse> {
                        override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                        ) {

                        }

                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        }

                    })
                }

                btnReject?.setOnClickListener {
                    Retro.instance.updateStatusKeluhan(
                        b.kode_keluhan.toString(), Constant.rejected
                    ).enqueue(object : Callback<DefaultResponse> {
                        override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                        ) {

                        }

                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        }

                    })
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