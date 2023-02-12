package com.hankim.smokingarea

data class SmokingList(
    val id: Int,
    val place: String,
    val local1: String,
    val local2: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val sector: String,
    val divide1: String,
    val divide2: String,
    val update: String,
): java.io.Serializable