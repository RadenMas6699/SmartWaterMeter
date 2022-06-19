/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.user

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.button.MaterialButton
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.adapter.TagihanAdapterUser
import com.radenmas.smart.water.meter.databinding.FragmentHomeUserBinding
import com.radenmas.smart.water.meter.model.TagihanResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.Constant
import com.radenmas.smart.water.meter.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserHomeFragment : Fragment() {
    private lateinit var b: FragmentHomeUserBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var paymentUser: TagihanAdapterUser

    private lateinit var idPelanggan: String
    private lateinit var name: String
    private lateinit var payment: String
    private lateinit var avatar: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentHomeUserBinding.inflate(layoutInflater, container, false)
        val v = b.root

        sharedPref = activity?.getSharedPreferences(
            Constant.app_pref, Context.MODE_PRIVATE
        )!!

        initView()
        onClick()

        lineChartDay()

        return v
    }

    private fun lineChart(data: ArrayList<Entry>) {
        val lineDataSet = LineDataSet(data, "Data")
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

        lineDataSet.setDrawFilled(true)

        lineDataSet.lineWidth = 1.5f

        lineDataSet.color = ResourcesCompat.getColor(resources, R.color.primary, null)

        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)

        val xAxis: XAxis = b.chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f

        val rightAxis: YAxis = b.chart.axisRight
        rightAxis.setDrawLabels(false)
        rightAxis.setDrawGridLines(false)

        val leftAxis: YAxis = b.chart.axisLeft
        leftAxis.setDrawGridLines(false)

        b.chart.legend.isEnabled = false
        b.chart.description.isEnabled = false
        b.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        b.chart.data = LineData(lineDataSet)
        b.chart.animateXY(100, 500)
    }

    private fun lineChartDay() {
        val kasus = ArrayList<Entry>()
        kasus.add(Entry(0F, 149F))
        kasus.add(Entry(1F, 113F))
        kasus.add(Entry(2F, 196F))
        kasus.add(Entry(3F, 106F))
        kasus.add(Entry(4F, 181F))
        kasus.add(Entry(5F, 218F))
        kasus.add(Entry(6F, 247F))
        kasus.add(Entry(7F, 218F))
        kasus.add(Entry(8F, 337F))
        kasus.add(Entry(9F, 219F))
        kasus.add(Entry(10F, 247F))
        kasus.add(Entry(11F, 218F))
        kasus.add(Entry(12F, 181F))
        kasus.add(Entry(13F, 218F))
        kasus.add(Entry(14F, 247F))
        kasus.add(Entry(15F, 218F))
        kasus.add(Entry(16F, 337F))
        kasus.add(Entry(17F, 219F))
        kasus.add(Entry(18F, 247F))
        kasus.add(Entry(19F, 218F))
        kasus.add(Entry(20F, 149F))
        kasus.add(Entry(21F, 113F))
        kasus.add(Entry(22F, 196F))
        kasus.add(Entry(23F, 106F))

        lineChart(kasus)
    }

    private fun lineChartWeek() {
        val kasus = ArrayList<Entry>()
        kasus.add(Entry(0F, 149F))
        kasus.add(Entry(1F, 113F))
        kasus.add(Entry(2F, 196F))
        kasus.add(Entry(3F, 106F))
        kasus.add(Entry(4F, 181F))
        kasus.add(Entry(5F, 218F))
        kasus.add(Entry(6F, 247F))

        lineChart(kasus)
    }

    private fun lineChartMonth() {
        val chart = ArrayList<Entry>()

        Retro.instance.getChartMonth(idPelanggan)
            .enqueue(object : Callback<List<TagihanResponse>> {
                override fun onResponse(
                    call: Call<List<TagihanResponse>>,
                    response: Response<List<TagihanResponse>>
                ) {
                    val dataTagihan = response.body()
                    for (c in dataTagihan!!) {
                        chart.add(Entry(c.index, c.usage.toFloat()))
                        lineChart(chart)
                    }
                }

                override fun onFailure(call: Call<List<TagihanResponse>>, t: Throwable) {
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTotalTagihan()
        getTagihanPayment()
    }

    private fun getTagihanPayment() {
        Retro.instance.getTagihanUserLimit(
            idPelanggan
        ).enqueue(object : Callback<List<TagihanResponse>> {
            override fun onResponse(
                call: Call<List<TagihanResponse>>,
                response: Response<List<TagihanResponse>>
            ) {
                val dataTagihan = response.body()
                for (c in dataTagihan!!) {
                    b.llPayment.visibility = View.VISIBLE
                    paymentUser.setTagihan(dataTagihan)
                }
                if (dataTagihan.isEmpty()) {
                    b.llPayment.visibility = View.INVISIBLE
                }
            }

            override fun onFailure(call: Call<List<TagihanResponse>>, t: Throwable) {
            }
        })
    }

    private fun getTotalTagihan() {
        Retro.instance.getTotalTagihanUser(
            sharedPref.getString(Constant.data_id_pelanggan, null).toString()
        ).enqueue(object : Callback<TagihanResponse> {
            override fun onResponse(
                call: Call<TagihanResponse>,
                response: Response<TagihanResponse>
            ) {
                b.tvTagihan.text = Utils.formatNumber(response.body()?.total_bill!!)
                b.tvPemakaian.text = response.body()?.usage.toString()
            }

            override fun onFailure(call: Call<TagihanResponse>, t: Throwable) {
            }
        })
    }

    private fun initView() {
        idPelanggan = sharedPref.getString(Constant.data_id_pelanggan, null).toString()
        name = sharedPref.getString(Constant.data_name, null).toString()
        payment = sharedPref.getString(Constant.data_payment, null).toString()
        avatar = sharedPref.getString(Constant.data_avatar, null).toString()

        if (avatar == Constant.default) {
            Glide.with(this)
                .load(R.drawable.ic_profile_default)
                .into(b.imgProfile)
        } else {
            Glide.with(this)
                .load(avatar)
                .into(b.imgProfile)
        }

        b.tvName.text = name

        b.rvPaymentLast.layoutManager = LinearLayoutManager(activity)
        paymentUser = TagihanAdapterUser(requireActivity())
        b.rvPaymentLast.adapter = paymentUser
    }

    private fun onClick() {
        b.imgProfile.setOnClickListener {
            findNavController().navigate(R.id.action_userHomeFragment_to_userProfileFragment)
        }
        b.imgWebServer.setOnClickListener {
            findNavController().navigate(R.id.action_userHomeFragment_to_userWebserverFragment)
        }
        b.imgComplaint.setOnClickListener {
            val complaint =
                UserHomeFragmentDirections.actionUserHomeFragmentToUserPengaduanFragment(
                    idPelanggan
                )
            findNavController().navigate(complaint)
        }
        b.btnDay.setOnClickListener {
            setButtonActive(
                b.btnDay,
                b.btnWeek,
                b.btnMonth
            )
            lineChartDay()
        }
        b.btnWeek.setOnClickListener {
            setButtonActive(
                b.btnWeek,
                b.btnDay,
                b.btnMonth
            )
            lineChartWeek()
        }
        b.btnMonth.setOnClickListener {
            setButtonActive(
                b.btnMonth,
                b.btnDay,
                b.btnWeek
            )
            lineChartMonth()
        }

        b.tvViewAll.setOnClickListener {
            val billing =
                UserHomeFragmentDirections.actionUserHomeFragmentToUserBillingFragment(
                    idPelanggan
                )
            findNavController().navigate(billing)
        }
    }

    private fun setButtonActive(
        btnActive: MaterialButton,
        btnNotActive1: MaterialButton,
        btnNotActive2: MaterialButton
    ) {
        btnActive.setTextColor(ResourcesCompat.getColor(resources, R.color.primary_text, null))
        btnActive.strokeColor = ColorStateList.valueOf(Color.parseColor(Constant.color_primary))

        btnNotActive1.setTextColor(ResourcesCompat.getColor(resources, R.color.hint, null))
        btnNotActive1.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))

        btnNotActive2.setTextColor(ResourcesCompat.getColor(resources, R.color.hint, null))
        btnNotActive2.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))
    }
}