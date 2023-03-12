package com.hankim.smokingarea.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.hankim.smokingarea.SmokersModel
import com.hankim.smokingarea.database.SmokersDatabase
import com.hankim.smokingarea.database.SmokersEntity
import com.hankim.smokingarea.database.asDomainModel
import com.hankim.smokingarea.network.SmokersNetwork
import com.hankim.smokingarea.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SmokersRepository(private val database: SmokersDatabase) {

    val smokers: LiveData<List<SmokersModel>> =
        Transformations.map(database.smokersDao.getSmokers()) {
            it.asDomainModel()
        }
    suspend fun refreshSmokers() {
        withContext(Dispatchers.IO) {
            val smokersList = SmokersNetwork.smokers.getSmokersPlace()
            database.smokersDao.insertAll(smokersList.asDatabaseModel())

        }
    }
}