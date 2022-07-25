/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.model

import java.sql.Timestamp

data class ChartResponse(
    val timestamp: Long,
    val usage: String
)

