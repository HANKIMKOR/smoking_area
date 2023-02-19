package com.hankim.smokingarea.database

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.hankim.smokingarea.SmokersModel

data class SmokersEntity(
    @PrimaryKey
    @SerializedName("id") val id: Int,
    @SerializedName("place") val place: String,
    @SerializedName("local1") val local1: String,
    @SerializedName("local2") val local2: String,
    @SerializedName("address") val address: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("sector") val sector: String,
    @SerializedName("divide1") val divide1: String,
    @SerializedName("divide2") val divide2: String,
    @SerializedName("update") val update: String,
)

fun List<SmokersEntity>.asDomainModel(): List<SmokersModel> {
    return map {
        SmokersModel(
            id = it.id,
            place = it.place,
            local1 = it.local1,
            local2 = it.local2,
            address = it.address,
            lat = it.lat,
            lng = it.lng,
            sector = it.sector,
            divide1 = it.divide1,
            divide2 = it.divide2,
            update = it.update
        )
    }
}