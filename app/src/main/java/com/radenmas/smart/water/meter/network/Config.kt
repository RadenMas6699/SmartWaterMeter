/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.network

object Config {
    // URL WEB SERVER
    private const val BASE_URL_WEB_SERVER = "http://192.168.4.1/"
    const val URL_DEBIT = BASE_URL_WEB_SERVER + "debit"
    const val URL_USAGE = BASE_URL_WEB_SERVER + "usage"
    const val URL_CALIBRATION = BASE_URL_WEB_SERVER + "calibration"
    const val URL_BILLING = BASE_URL_WEB_SERVER + "harga"
    const val URL_RELAY_ON = BASE_URL_WEB_SERVER + "selenoid/on"
    const val URL_RELAY_OFF = BASE_URL_WEB_SERVER + "selenoid/off"
    const val URL_SET_PRICE = BASE_URL_WEB_SERVER + "getHarga"
    const val URL_SET_PRESS = BASE_URL_WEB_SERVER + "getTekanan?setPress="
    const val URL_SET_CALIBRATION = BASE_URL_WEB_SERVER + "setCalibration?setCalibration="
    const val URL_RESET = BASE_URL_WEB_SERVER + "reset/on"

    // DATABASE SERVER
    const val BASE_URL = "http://smartpdam.com/api/"
    const val POST_LOGIN = "login/api_login"

    // NOTIF
    const val POST_NOTIFICATION = "test/notification_user"

    // KELUHAN
    const val GET_KELUHAN_USER = "keluhan/get_keluhan_user"
    const val GET_KELUHAN_USER_FILTER = "keluhan/get_keluhan_user_filter"
    const val GET_KELUHAN_ADMIN_FILTER = "keluhan/get_keluhan_admin_filter"
    const val GET_KELUHAN_ADMIN = "keluhan/get_keluhan_admin"
    const val CREATE_KELUHAN = "keluhan/create_keluhan_user"
    const val UPDATE_STATUS_KELUHAN = "keluhan/update_status_keluhan"

    // USER
    const val GET_ALL_USER = "data_pelanggan/get_all_data_user"
    const val UPDATE_DATA_USER = "data_pelanggan/update_data_user"
    const val UPDATE_AVATAR_USER = "data_pelanggan/update_avatar_user"
    const val GET_COUNT_USER = "data_pelanggan/get_count_user"
    const val ADD_USER = "data_pelanggan/add_user"
    const val SEARCH_USER = "data_pelanggan/search_data_user"

    // TAGIHAN
    const val GET_TAGIHAN_USER = "tagihan/get_tagihan_user"
    const val GET_TAGIHAN_USER_LIMIT = "tagihan/get_tagihan_user_limit"
    const val GET_TOTAL_TAGIHAN_USER = "tagihan/get_total_tagihan_user"
    const val GET_TAGIHAN_ADMIN = "tagihan/get_tagihan_admin"
    const val UPDATE_STATUS_TAGIHAN = "tagihan/update_status_tagihan"
    const val GET_CHART_MONTH = "tagihan/get_chart_month"
}