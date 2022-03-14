/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentPengaduanAdminBinding

class AdminPengaduanFragment : Fragment() {
    private lateinit var b: FragmentPengaduanAdminBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentPengaduanAdminBinding.inflate(layoutInflater, container, false)
        val v = b.root

        onClick()

        setupViewPager(b.viewPager)

        b.tabLayout.setupWithViewPager(b.viewPager)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager(b.viewPager)
        b.tabLayout.setupWithViewPager(b.viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(
            requireActivity().supportFragmentManager
        )
        adapter.addFragment(AdminAduanFragment(), resources.getString(R.string.keluhan))
        adapter.addFragment(AdminRiwayatKeluhanFragment(), resources.getString(R.string.riwayat))
        viewPager.adapter = adapter
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
            onDestroy()
        }
    }

    private class ViewPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

}