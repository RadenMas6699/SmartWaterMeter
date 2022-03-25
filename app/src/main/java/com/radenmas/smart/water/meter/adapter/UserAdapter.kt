/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.model.UserResponse
import com.radenmas.smart.water.meter.utils.Constant
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(val context: Context) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val userRespons: MutableList<UserResponse> = mutableListOf()

    inner class UserViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val imgUser: CircleImageView = item.findViewById(R.id.imgUser)
        private val namaPelanggan: TextView = item.findViewById(R.id.tvFullName)
        private val idPelanggan: TextView = item.findViewById(R.id.tvUserID)

        fun bindUser(b: UserResponse) {
            if (b.image.equals(Constant.default)) {
                Glide.with(context)
                    .load(R.drawable.img_user_default)
                    .into(imgUser)
            } else {
                Glide.with(context)
                    .load(b.image)
                    .into(imgUser)
            }

            namaPelanggan.text = b.nama
            idPelanggan.text = b.id_user
        }
    }

    fun setUser(data: List<UserResponse>) {
        userRespons.clear()
        userRespons.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindUser(userRespons[position])
    }

    override fun getItemCount(): Int {
        return userRespons.size
    }
}