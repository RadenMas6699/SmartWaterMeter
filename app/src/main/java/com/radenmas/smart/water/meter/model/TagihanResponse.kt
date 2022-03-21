/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.model

data class TagihanResponse(
    val id_user: String?,
    val nama: String?,
    val pemakaian: String?,
    val maintenance: String?,
    val tagihan: String?,
    val total: Int?,
    val bulan: String?,
    val tahun: String?,
    val status: String?,
    val image: String?
)

