/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.model

data class TagihanResponse(
    val index : Float,
    val id_pelanggan: String,
    val name: String,
    val usage: String,
    val maintenance: String,
    val bill: String,
    val total_bill: Int,
    val month: String,
    val year: String,
    val status: String,
    val avatar: String,
    val pay_date: String
)

