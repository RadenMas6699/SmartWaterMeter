/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.network

import com.radenmas.smart.water.meter.model.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface APIServices {

    // NOTIFICATION
    @FormUrlEncoded
    @POST(Config.POST_NOTIFICATION)
    fun postNotification(
        @Field("title") title: String,
        @Field("message") message: String
    ): Call<DefaultResponse>

    // LOGIN
    @FormUrlEncoded
    @POST(Config.POST_LOGIN)
    fun postLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>


    // DATA PELANGGAN
    @FormUrlEncoded
    @POST(Config.ADD_USER)
    fun addUser(
        @Field("id_meteran") id_meteran: Int,
        @Field("name") name: String,
        @Field("ktp") ktp: String,
        @Field("phone") phone: String,
        @Field("address") address: String,
        @Field("registered") registered: String,
        @Field("year") year: String,
        @Field("type") type: String,
        @Field("status") status: Int
    ): Call<DefaultResponse>

    @GET(Config.GET_ALL_USER)
    fun getAllDataUser(): Call<List<UserResponse>>

    @GET(Config.GET_COUNT_USER)
    fun getCountUser(): Call<DefaultResponse>

    @FormUrlEncoded
    @POST(Config.SEARCH_USER)
    fun searchUser(
        @Field("name") name: String
    ): Call<List<UserResponse>>

    @FormUrlEncoded
    @POST(Config.UPDATE_DATA_USER)
    fun updateDataUser(
        @Field("id_pelanggan") id_pelanggan: String,
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("address") address: String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST(Config.UPDATE_AVATAR_USER)
    fun updateAvatarUser(
        @Field("id_pelanggan") id_pelanggan: String,
        @Field("avatar") avatar: String
    ): Call<DefaultResponse>


    // KELUHAN
    @FormUrlEncoded
    @POST(Config.CREATE_KELUHAN)
    fun createKeluhan(
        @Field("id_pelanggan") id_pelanggan: String,
        @Field("title") subjek: String,
        @Field("desc") keterangan: String,
        @Field("status") status: String,
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST(Config.GET_KELUHAN_ADMIN_FILTER)
    fun getKeluhanAdminFilter(
        @Field("filter") filter: String
    ): Call<List<AduanResponse>>

    @GET(Config.GET_KELUHAN_ADMIN)
    fun getKeluhanAdmin(): Call<List<AduanResponse>>

    @FormUrlEncoded
    @POST(Config.GET_KELUHAN_USER_FILTER)
    fun getKeluhanUserFilter(
        @Field("id_pelanggan") id_pelanggan: String,
        @Field("filter") filter: String
    ): Call<List<AduanResponse>>

    @FormUrlEncoded
    @POST(Config.GET_KELUHAN_USER)
    fun getKeluhanUser(
        @Field("id_pelanggan") id_pelanggan: String
    ): Call<List<AduanResponse>>

    @FormUrlEncoded
    @POST(Config.UPDATE_STATUS_KELUHAN)
    fun updateStatusKeluhan(
        @Field("id_keluhan") id_keluhan: String,
        @Field("status") status: String
    ): Call<DefaultResponse>


    // TAGIHAN
    @FormUrlEncoded
    @POST(Config.GET_TAGIHAN_USER_LIMIT)
    fun getTagihanUserLimit(
        @Field("id_pelanggan") id_pelanggan: String
    ): Call<List<TagihanResponse>>

    @FormUrlEncoded
    @POST(Config.GET_TAGIHAN_USER)
    fun getTagihanUser(
        @Field("id_pelanggan") id_pelanggan: String
    ): Call<List<TagihanResponse>>

    @FormUrlEncoded
    @POST(Config.GET_CHART_MONTH)
    fun getChartMonth(
        @Field("id_pelanggan") id_pelanggan: String
    ): Call<List<TagihanResponse>>


    @FormUrlEncoded
    @POST(Config.GET_TOTAL_TAGIHAN_USER)
    fun getTotalTagihanUser(
        @Field("id_pelanggan") id_pelanggan: String
    ): Call<TagihanResponse>

    @GET(Config.GET_TAGIHAN_ADMIN)
    fun getTagihanAdmin(): Call<List<TagihanResponse>>

    @FormUrlEncoded
    @POST(Config.UPDATE_STATUS_TAGIHAN)
    fun updateStatusTagihan(
        @Field("id_tagihan") id_tagihan: String,
        @Field("status") status: String,
        @Field("tgl_bayar") tgl_bayar: String
    ): Call<DefaultResponse>

}