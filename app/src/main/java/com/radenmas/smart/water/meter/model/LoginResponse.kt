/*
 * Created by RadenMas on 24/10/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.model

data class LoginResponse(
	val id_admin: String,
	val id_pelanggan: String,
	val name: String,
	val payment: String,
	val level: String,
	val ktp: String,
	val phone: String,
	val address: String,
	val avatar: String,
	val registered: String
)
