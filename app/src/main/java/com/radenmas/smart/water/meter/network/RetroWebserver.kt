/*
 * Created by RadenMas on 23/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by RadenMas on 23/03/2022.
 */
object RetroWebserver {
    val instance: APIServices by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(Config.BASE_URL_WEB_SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(APIServices::class.java)
    }
}