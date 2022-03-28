/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.network

import com.radenmas.smart.water.meter.model.*
import retrofit2.Call
import retrofit2.http.*

interface APIWebServer {

    // WEBSERVER
    @GET(Config.URL_DEBIT)
    fun getDebitWebServer(): Call<Void>

    @GET(Config.URL_TOTAL)
    fun getTotalWebServer(): Call<Void>

    @GET(Config.URL_BILLING)
    fun getBillingWebServer(): Call<DefaultResponse>

    @GET(Config.URL_RELAY_ON)
    fun setRelayOn(): Call<Void>

    @GET(Config.URL_RELAY_OFF)
    fun setRelayOff(): Call<Void>

    @GET(Config.URL_SET_PRICE)
    fun setPriceWebServer(@Query("setHarga") setHarga: Int): Call<Void>

    @GET(Config.URL_RESET)
    fun resetDataWebServer(): Call<Void>
}