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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.model.AduanResponse
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.AppUtils
import com.radenmas.smart.water.meter.utils.Constant
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        fun bindKeluhan(b: AduanResponse) {
            if (b.avatar == Constant.default) {
                Glide.with(context)
                    .load(R.drawable.ic_profile_default)
                    .into(imgUser)
            } else {
                Glide.with(context)
                    .load(b.avatar)
                    .into(imgUser)
            }

            tvTitle.text = b.title
            tvUserID.text = b.id_pelanggan
            tvDesc.text = b.desc
            tvDate.text = AppUtils.formatDate(b.date)

            tvStatues.text = b.status
            when (b.status) {
                Constant.sent -> {
                    tvStatues.visibility = View.GONE
                }
                Constant.processed -> {
                    tvStatues.visibility = View.VISIBLE
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_orange)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_orange))
                }
                Constant.finish -> {
                    tvStatues.visibility = View.VISIBLE
                    tvStatues.setBackgroundResource(R.drawable.bg_statues_blue)
                    tvStatues.setTextColor(Color.parseColor(Constant.color_primary))
                }
                Constant.rejected -> {
                    tvStatues.visibility = View.VISIBLE
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
        holder.itemView.setOnClickListener {
            val dialog = BottomSheetDialog(context, R.style.DialogStyle)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.bottom_sheet_detail_aduan_admin)
            dialog.show()

            val imgProfile: CircleImageView = dialog.findViewById(R.id.imgProfile)!!
            val tvFullName: TextView? = dialog.findViewById(R.id.tvFullName)
            val tvUserID: TextView? = dialog.findViewById(R.id.tvUserID)
            val tvTitleAduan: TextView? = dialog.findViewById(R.id.tvTitleAduan)
            val tvDescAduan: TextView? = dialog.findViewById(R.id.tvDescAduan)
            val etNoteRejected: TextView? = dialog.findViewById(R.id.etNoteRejected)
            val llConfirm: LinearLayout? = dialog.findViewById(R.id.llConfirm)
            val btnReject: MaterialButton? = dialog.findViewById(R.id.btnReject)
            val btnAccept: MaterialButton? = dialog.findViewById(R.id.btnAccept)
            val imgDismiss: ImageView? = dialog.findViewById(R.id.imgDismiss)

            if (history[position].avatar == Constant.default) {
                Glide.with(context)
                    .load(R.drawable.ic_profile_default)
                    .into(imgProfile)
            } else {
                Glide.with(context)
                    .load(history[position].avatar)
                    .into(imgProfile)
            }

            tvFullName?.text = history[position].name
            tvUserID?.text = history[position].id_pelanggan
            tvTitleAduan?.text = history[position].title
            tvDescAduan?.text = history[position].desc

            if (history[position].status == Constant.sent) {
                llConfirm?.visibility = View.VISIBLE
            } else {
                llConfirm?.visibility = View.GONE
            }

            btnAccept?.setOnClickListener {
                if (etNoteRejected!!.text.isBlank()) {
                    AppUtils.toast(context, "Berikan Alasan Atas Penolakan")
                } else {
                    Retro.instance.updateStatusKeluhan(
                        history[position].id_keluhan, Constant.processed
                    ).enqueue(object : Callback<DefaultResponse> {
                        override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                        ) {
                            AppUtils.toast(context, "Aduan Diterima")
                            dialog.dismiss()
                        }

                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            dialog.dismiss()
                        }

                    })
                }
            }

            btnReject?.setOnClickListener {
                Retro.instance.updateStatusKeluhan(
                    history[position].id_keluhan, Constant.rejected
                ).enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        AppUtils.toast(context, "Aduan Ditolak")
                        dialog.dismiss()
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        dialog.dismiss()
                    }

                })
            }

            imgDismiss?.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    override fun getItemCount(): Int {
        return history.size
    }

}