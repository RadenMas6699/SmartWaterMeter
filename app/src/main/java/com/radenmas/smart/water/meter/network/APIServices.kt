/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.network

import com.radenmas.smart.water.meter.model.AduanResponse
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.model.LoginResponse
import com.radenmas.smart.water.meter.model.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface APIServices {

    @FormUrlEncoded
    @POST(Config.URL_LOGIN)
    fun postLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST(Config.GET_KELUHAN_USER)
    fun getKeluhanUser(
        @Field("kode_pelanggan") username: String
    ): Call<List<AduanResponse>>

    @FormUrlEncoded
    @POST(Config.GET_KELUHAN_USER_FILTER)
    fun getKeluhanUserFilter(
        @Field("kode_pelanggan") username: String,
        @Field("filter") filter: String
    ): Call<List<AduanResponse>>

    @FormUrlEncoded
    @POST(Config.CREATE_KELUHAN)
    fun createKeluhan(
        @Field("kode_pelanggan") kode_pelanggan: String,
        @Field("title") subjek: String,
        @Field("desc") keterangan: String,
        @Field("status") status: String,
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST(Config.UPDATE_DATA_USER)
    fun updateDataUser(
        @Field("id_pelanggan") id_pelanggan: String,
        @Field("nama_pelanggan") nama_pelanggan: String,
        @Field("no_telp") no_telp: String,
        @Field("alamat") alamat: String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST(Config.ADD_USER)
    fun addUser(
        @Field("kode_meteran") kode_meteran: String,
        @Field("tahun") tahun: String,
        @Field("jenis") jenis: String,
        @Field("status") status: String,
        @Field("password") password: String,
        @Field("fullname") fullname: String,
        @Field("no_ktp") no_ktp: String,
        @Field("no_telp") no_telp: String,
        @Field("alamat") alamat: String,
        @Field("image") image: String,
        @Field("terdaftar") terdaftar: String
    ): Call<DefaultResponse>

    @GET(Config.GET_COUNT_USER)
    fun getCountUser(): Call<DefaultResponse>

    @GET("data_pelanggan/get_all_data_user.php")
    fun getAllDataUser(): Call<List<UserResponse>>

    @POST("keluhan/get_history_keluhan_user.php")
    fun getHistoryKeluhanUser(): Call<List<AduanResponse>>

//    @POST("keluhan/get_filter_keluhan_user.php")
//    fun getHistoryKeluhanUser(): Call<List<Keluhan>>
}