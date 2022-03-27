/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.model

data class UserResponse(
    val id_pelanggan: String,
    val name: String,
    val ktp: String,
    val phone: String,
    val address: String,
    val avatar: String,
    val registered: String
)