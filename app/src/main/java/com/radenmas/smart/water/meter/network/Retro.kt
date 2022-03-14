/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//class Retro {
//    fun getRetroClientInstance(): Retrofit {
////        val gson = GsonBuilder().setLenient().create()
//        return Retrofit.Builder()
//            .baseUrl(Config.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//}

object Retro {
    val instance: APIServices by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(APIServices::class.java)
    }
}