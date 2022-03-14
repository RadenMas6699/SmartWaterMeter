/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AduanResponse {
    @SerializedName("kode_pelanggan")
    @Expose
    var kode_pelanggan: String? = null

    @SerializedName("nama_pelanggan")
    @Expose
    var nama_pelanggan: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("desc")
    @Expose
    var desc: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("waktu")
    @Expose
    var waktu: String? = null
}