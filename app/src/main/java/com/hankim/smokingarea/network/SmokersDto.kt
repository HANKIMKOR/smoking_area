package com.hankim.smokingarea.network

import com.hankim.smokingarea.database.SmokersEntity

data class SmokersDto(
    val smokingList: List<SmokersEntity>

)