package com.hankim.smokingarea.network

import com.hankim.smokingarea.SmokersModel
import com.hankim.smokingarea.database.SmokersEntity
import retrofit2.Callback

data class NetworkSmokersContainer(val smokers: List<NetworkSmokers>) {
    fun enqueue(callback: Callback<SmokersDto>) {
    }
}

data class NetworkSmokers(
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
    val update: String
)

fun NetworkSmokersContainer.asDomainModel(): List<SmokersModel> {
    return smokers.map {
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

fun NetworkSmokersContainer.asDatabaseModel(): List<SmokersEntity> {
    return smokers.map {
        SmokersEntity(
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