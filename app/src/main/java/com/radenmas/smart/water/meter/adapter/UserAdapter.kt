/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.model.UserResponse
import com.radenmas.smart.water.meter.ui.admin.AdminUserFragmentDirections
import com.radenmas.smart.water.meter.utils.AppUtils
import com.radenmas.smart.water.meter.utils.Constant
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(val context: Context) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val userRespons: MutableList<UserResponse> = mutableListOf()

    inner class UserViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val imgUser: CircleImageView = item.findViewById(R.id.imgUser)
        private val namaPelanggan: TextView = item.findViewById(R.id.tvFullName)
        private val idPelanggan: TextView = item.findViewById(R.id.tvUserID)

        fun bindUser(b: UserResponse) {
            if (b.avatar == Constant.default) {
                Glide.with(context)
                    .load(R.drawable.ic_profile_default)
                    .into(imgUser)
            } else {
                Glide.with(context)
                    .load(b.avatar)
                    .into(imgUser)
            }
            namaPelanggan.text = b.name
            idPelanggan.text = b.id_pelanggan
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
        holder.itemView.setOnClickListener {
            val dataUser = AdminUserFragmentDirections.actionAdminUserFragmentToAdminDetailUserFragment(
                userRespons[position].avatar,
                userRespons[position].name,
                userRespons[position].id_pelanggan,
                userRespons[position].ktp,
                userRespons[position].phone,
                userRespons[position].address,
                userRespons[position].registered
            )
            Navigation.createNavigateOnClickListener(dataUser).onClick(holder.itemView)
        }
    }

    override fun getItemCount(): Int {
        return userRespons.size
    }
}