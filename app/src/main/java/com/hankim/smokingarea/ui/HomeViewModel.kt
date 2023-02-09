package com.hankim.smokingarea.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hankim.smokingarea.SmokingList

class HomeViewModel: ViewModel() {

    private val _smokingList = MutableLiveData<SmokingList>()
    val smokingList: LiveData<SmokingList> = _smokingList

    fun loadHomeData() {
        // TODO Data Layer - Repository에 요청
    }

}