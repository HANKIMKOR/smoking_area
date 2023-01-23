package com.hankim.smokingarea

data class SearchData(
    val list: List<Banner>
)
data class Banner(
    val local1: String,
    val local2: String,
    val place: String,
    val address: String,
    val sector: String,
    val divide1: String,
    val divide2: String,
    val update: String,
)

