/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("id_user")
    @Expose
    var id_user: String? = null

    @SerializedName("nama")
    @Expose
    var nama: String? = null

    @SerializedName("no_telp")
    @Expose
    var no_telp: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("terdaftar")
    @Expose
    var terdaftar: String? = null
}