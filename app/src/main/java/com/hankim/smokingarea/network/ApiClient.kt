package com.hankim.smokingarea.network

import com.hankim.smokingarea.SearchData
import retrofit2.Call
import retrofit2.http.GET

interface ApiClient {
    @GET("https://run.mocky.io/v3/d9aee6e9-fe1f-401f-ac74-f6bdb8b8ad27")
    fun getSmokingList(): Call<SearchData>
}
