/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.network

object Config {
    // URL WEB SERVER
    const val BASE_URL_WEB_SERVER = "http://192.168.4.1/"
    const val URL_DEBIT = BASE_URL_WEB_SERVER + "debit"
    const val URL_TOTAL = BASE_URL_WEB_SERVER + "total"
    const val URL_PRICE = BASE_URL_WEB_SERVER + "harga"
    const val URL_SALDO = BASE_URL_WEB_SERVER + "token"
    const val URL_PRESS = BASE_URL_WEB_SERVER + "tekanan"
    const val URL_TEMP = BASE_URL_WEB_SERVER + "harga"
    const val URL_RELAY_ON = BASE_URL_WEB_SERVER + "led1/on"
    const val URL_RELAY_OFF = BASE_URL_WEB_SERVER + "led1/off"
    const val URL_SET_PRESS = BASE_URL_WEB_SERVER + "getTekanan?setPress="
    const val URL_SET_PRICE = BASE_URL_WEB_SERVER + "getHarga?setHarga="
    const val URL_ADD_TOKEN = BASE_URL_WEB_SERVER + "setToken?setToken="
    const val URL_RESET_EEPROM = BASE_URL_WEB_SERVER + "hapus/on"

    // DATABASE SERVER
    const val BASE_URL  = "http://smartpdam.com/api/"
    const val URL_LOGIN = "login/api_login.php"
    // KELUHAN
    const val GET_KELUHAN_USER          = "keluhan/get_keluhan_user.php"
    const val GET_KELUHAN_USER_FILTER   = "keluhan/get_keluhan_user_filter.php"
    const val CREATE_KELUHAN            = "keluhan/create_keluhan_user.php"
    const val UPDATE_STATUS_KELUHAN     = "keluhan/update_status_keluhan.php"
    // USER
    const val GET_ALL_USER = "data_pelanggan/get_all_data_user.php"
    const val UPDATE_DATA_USER = "data_pelanggan/update_data_user.php"
    const val GET_COUNT_USER = "data_pelanggan/get_count_user.php"
    const val ADD_USER = "data_pelanggan/add_user.php"

}