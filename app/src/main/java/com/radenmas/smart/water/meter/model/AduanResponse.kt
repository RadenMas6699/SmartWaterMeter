/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.model

data class AduanResponse(
    val id_keluhan: String,
    val id_pelanggan: String,
    val name: String,
    val avatar: String,
    val title: String,
    val desc: String,
    val status: String,
    val date: String
)