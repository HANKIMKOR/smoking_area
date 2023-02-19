package com.hankim.smokingarea.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.hankim.smokingarea.SmokersModel
import com.hankim.smokingarea.database.getDatabase
import com.hankim.smokingarea.repository.SmokersRepository
import kotlinx.coroutines.launch
import java.io.IOException

class SmokersViewModel(application: Application): AndroidViewModel(application) {

    private val smokersRepository = SmokersRepository(getDatabase(application))

    val smokersList = smokersRepository.smokers

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                smokersRepository.refreshSmokers()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(smokersList.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SmokersViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SmokersViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}