/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.radenmas.smart.water.meter.databinding.ActivityOnBoardingBinding
import com.radenmas.smart.water.meter.ui.auth.AuthActivity
import com.radenmas.smart.water.meter.utils.PrefManager

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var b: ActivityOnBoardingBinding

    private var prefManager: PrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityOnBoardingBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        prefManager = PrefManager(this)
        if (!prefManager!!.isFirstTimeLaunch()) {
            launchHomeScreen()
        }


        onClick()

        val adapter = ViewPagerAdapter()
        b.vpIntro.adapter = adapter
        b.dotsIndicator.setViewPager(b.vpIntro)
    }

    private fun launchHomeScreen() {
        prefManager?.setFirstTimeLaunch(false)
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    private fun onClick() {
        b.tvSkip.setOnClickListener {
            launchHomeScreen()
        }

        b.imgNext.setOnClickListener {

        }
    }
}

class ViewPagerAdapter : PagerAdapter() {
    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        TODO("Not yet implemented")
    }

}
