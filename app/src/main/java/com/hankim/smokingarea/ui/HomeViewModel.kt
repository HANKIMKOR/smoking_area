package com.hankim.smokingarea.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hankim.smokingarea.network.SmokersEntity

class HomeViewModel: ViewModel() {

    private val _smokingList = MutableLiveData<SmokersEntity>()
    val smokingList: LiveData<SmokersEntity> = _smokingList

    fun loadHomeData() {
        // TODO Data Layer - Repository에 요청
    }

}