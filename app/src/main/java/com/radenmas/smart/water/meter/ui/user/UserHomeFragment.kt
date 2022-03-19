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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.Utils
import com.google.android.material.button.MaterialButton
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentHomeUserBinding
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Constant
import com.radenmas.smart.water.meter.network.Retro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserHomeFragment : Fragment() {
    private lateinit var b: FragmentHomeUserBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentHomeUserBinding.inflate(layoutInflater, container, false)
        val v = b.root

        sharedPref = activity?.getSharedPreferences(
            getString(R.string.app_pref), Context.MODE_PRIVATE
        )!!

        initView()
        onClick()

        lineChartDay()

        return v
    }

    private fun lineChart(data: ArrayList<Entry>) {
        val kasusLineDataSet = LineDataSet(data, "Data")
        kasusLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

        kasusLineDataSet.setDrawFilled(true)
        if (Utils.getSDKInt() >= 18) {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.bg_chart)
            kasusLineDataSet.fillDrawable = drawable
        } else {
            kasusLineDataSet.fillAlpha = 5
        }

        kasusLineDataSet.lineWidth = 1.5f
        kasusLineDataSet.setDrawCircles(false)
        kasusLineDataSet.setDrawValues(false)

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
        b.chart.data = LineData(kasusLineDataSet)
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
        kasus.add(Entry(24F, 181F))
        kasus.add(Entry(25F, 218F))
        kasus.add(Entry(26F, 247F))
        kasus.add(Entry(27F, 218F))
        kasus.add(Entry(28F, 337F))
        kasus.add(Entry(29F, 219F))

        lineChart(kasus)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTagihan()
    }

    private fun getTagihan() {
        Retro.instance.getTotalTagihanUser(
            sharedPref.getString("idPelanggan", null).toString(),
            "Belum Lunas"
        ).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val tagihan: String =
                    String.format("%,d", response.body()?.message.toString().toInt())
                b.tvTagihan.text = tagihan
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

            }

        })
    }

    private fun initView() {
        Glide.with(this).load(sharedPref.getString("image", null)).into(b.imgProfile)
        b.tvFullName.text = sharedPref.getString("nama", null)
    }

    private fun onClick() {
        b.imgProfile.setOnClickListener {
            findNavController().navigate(R.id.action_userHomeFragment_to_userProfileFragment)
        }
        b.imgWebServer.setOnClickListener {
            findNavController().navigate(R.id.action_userHomeFragment_to_userWebserverFragment)
        }
        b.imgNotif.setOnClickListener {
            findNavController().navigate(R.id.action_userHomeFragment_to_userNotificationFragment)
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
            findNavController().navigate(R.id.action_userHomeFragment_to_userPaymentFragment)
        }
    }

    private fun setButtonActive(
        btnActive: MaterialButton,
        btnNotActive1: MaterialButton,
        btnNotActive2: MaterialButton
    ) {
        btnActive.setTextColor(resources.getColor(R.color.primary_text))
        btnActive.strokeColor = ColorStateList.valueOf(Color.parseColor(Constant.color_primary))

        btnNotActive1.setTextColor(resources.getColor(R.color.hint))
        btnNotActive1.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))

        btnNotActive2.setTextColor(resources.getColor(R.color.hint))
        btnNotActive2.strokeColor =
            ColorStateList.valueOf(Color.parseColor(Constant.color_white_text))
    }

}