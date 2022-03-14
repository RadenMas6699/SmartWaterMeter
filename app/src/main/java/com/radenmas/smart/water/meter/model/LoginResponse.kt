/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.model

data class LoginResponse(
    val username: String,
    val level_user: String,
    val id_user: String,
    val nama: String,
    val no_ktp: String,
    val no_telp: String,
    val alamat: String,
    val image: String,
    val terdaftar: String
)

