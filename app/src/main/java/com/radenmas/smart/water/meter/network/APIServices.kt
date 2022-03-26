/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.network

import com.radenmas.smart.water.meter.model.*
import retrofit2.Call
import retrofit2.http.*

interface APIServices {

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
        @Field("kode_meteran") kode_meteran: Int,
        @Field("fullname") fullname: String,
        @Field("no_ktp") no_ktp: String,
        @Field("no_telp") no_telp: String,
        @Field("alamat") alamat: String,
        @Field("terdaftar") terdaftar: String,
        @Field("tahun") tahun: String,
        @Field("jenis") jenis: String,
        @Field("status") status: Int
    ): Call<DefaultResponse>

    @GET(Config.GET_ALL_USER)
    fun getAllDataUser(): Call<List<UserResponse>>

    @GET(Config.GET_COUNT_USER)
    fun getCountUser(): Call<DefaultResponse>

    @FormUrlEncoded
    @POST(Config.SEARCH_USER)
    fun searchUser(
        @Field("nama_pelanggan") nama_pelanggan: String
    ): Call<List<UserResponse>>

    @FormUrlEncoded
    @POST(Config.UPDATE_DATA_USER)
    fun updateDataUser(
        @Field("id_pelanggan") id_pelanggan: String,
        @Field("nama_pelanggan") nama_pelanggan: String,
        @Field("no_telp") no_telp: String,
        @Field("alamat") alamat: String
    ): Call<DefaultResponse>


    // KELUHAN
    @FormUrlEncoded
    @POST(Config.CREATE_KELUHAN)
    fun createKeluhan(
        @Field("kode_pelanggan") kode_pelanggan: String,
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
        @Field("kode_pelanggan") username: String,
        @Field("filter") filter: String
    ): Call<List<AduanResponse>>

    @FormUrlEncoded
    @POST(Config.GET_KELUHAN_USER)
    fun getKeluhanUser(
        @Field("kode_pelanggan") username: String
    ): Call<List<AduanResponse>>

    @FormUrlEncoded
    @POST(Config.UPDATE_STATUS_KELUHAN)
    fun updateStatusKeluhan(
        @Field("kode_keluhan") kode_keluhan: String,
        @Field("status") status: String
    ): Call<DefaultResponse>


    // TAGIHAN
    @FormUrlEncoded
    @POST(Config.GET_TAGIHAN_USER_LIMIT)
    fun getTagihanUserLimit(
        @Field("kode_pelanggan") kode_pelanggan: String
    ): Call<List<TagihanResponse>>

    @FormUrlEncoded
    @POST(Config.GET_TAGIHAN_USER)
    fun getTagihanUser(
        @Field("kode_pelanggan") username: String
    ): Call<List<TagihanResponse>>


    @FormUrlEncoded
    @POST(Config.GET_TOTAL_TAGIHAN_USER)
    fun getTotalTagihanUser(
        @Field("kode_pelanggan") kode_pelanggan: String
    ): Call<TagihanResponse>

    @FormUrlEncoded
    @POST(Config.UPDATE_STATUS_TAGIHAN)
    fun updateStatusTagihan(
        @Field("id_tagihan") id_tagihan: String,
        @Field("status") status: String
    ): Call<DefaultResponse>


    // WEBSERVER
    @GET(Config.URL_DEBIT)
    fun getDebit(): Call<DefaultResponse>

    @GET(Config.URL_TOTAL)
    fun getTotal(): Call<Void>

    @GET(Config.URL_BILLING)
    fun getBilling(): Call<DefaultResponse>

    @GET(Config.URL_RELAY_ON)
    fun setRelayOn(): Call<Void>

    @GET(Config.URL_RELAY_OFF)
    fun setRelayOff(): Call<Void>

    @GET(Config.URL_SET_PRICE)
    fun setPrice(@Query("setHarga") setHarga: Int): Call<Void>

    @GET(Config.URL_RESET)
    fun resetData(): Call<Void>
}