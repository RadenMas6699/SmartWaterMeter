/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.adapter.UserAdapter
import com.radenmas.smart.water.meter.databinding.FragmentUserAdminBinding
import com.radenmas.smart.water.meter.model.UserResponse
import com.radenmas.smart.water.meter.network.Retro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminUserFragment : Fragment() {
    private lateinit var b: FragmentUserAdminBinding
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentUserAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        b.rvAllDataUser.layoutManager = LinearLayoutManager(activity)
        userAdapter = UserAdapter(requireActivity())
        b.rvAllDataUser.adapter = userAdapter

        b.swipeRefresh.setOnRefreshListener {
            getDataUser()
            Handler(Looper.getMainLooper()).postDelayed({
                b.swipeRefresh.isRefreshing = false
            }, 2000)
        }

        onClick()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataUser()
    }

    private fun getDataUser() {
        Retro.instance.getAllDataUser().enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                val dataUser = response.body()
                for (c in dataUser!!) {
                    userAdapter.setUser(dataUser)
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                Log.e("Failed", t.message.toString())
            }

        })
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.etSearchUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length != 0) {
                    Retro.instance.searchUser(p0.toString())
                        .enqueue(object : Callback<List<UserResponse>> {
                            override fun onResponse(
                                call: Call<List<UserResponse>>,
                                response: Response<List<UserResponse>>
                            ) {
                                val dataUser = response.body()
                                for (c in dataUser!!) {
                                    userAdapter.setUser(dataUser)
                                }
                                if (dataUser.isNullOrEmpty()) {
                                    getDataUser()
                                }
                            }

                            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                            }

                        })
                }
//                else {
//                    getDataUser()
//                }
            }

        })

        b.fabAddUser.setOnClickListener {
            findNavController().navigate(R.id.action_adminUserFragment_to_adminAddUserFragment)
        }
    }
}