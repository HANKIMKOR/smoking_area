package com.hankim.smokingarea.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface SmokersService {
    @GET("https://smokingarea-c5d0b-default-rtdb.asia-southeast1.firebasedatabase.app")
    fun getSmokersPlace(): NetworkSmokersContainer

}

object SmokersNetwork {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://smokingarea-c5d0b-default-rtdb.asia-southeast1.firebasedatabase.app")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val smokers = retrofit.create(SmokersService::class.java)

}