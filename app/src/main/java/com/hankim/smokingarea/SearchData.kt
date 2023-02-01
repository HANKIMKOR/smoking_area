package com.hankim.smokingarea

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SearchData(
    val smokingList: List<SmokingList>
) : Parcelable


