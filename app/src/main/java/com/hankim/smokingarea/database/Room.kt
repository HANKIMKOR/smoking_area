package com.hankim.smokingarea.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SmokersDao {
    @Query("select * from smokersentity")
    fun getSmokers(): LiveData<List<SmokersEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(smokers: List<SmokersEntity>)
}

@Database(entities = [SmokersEntity::class], version = 1)
abstract class SmokersDatabase : RoomDatabase() {
    abstract val smokersDao: SmokersDao
}

private lateinit var INSTANCE: SmokersDatabase

fun getDatabase(context: Context): SmokersDatabase {
    synchronized(SmokersDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                SmokersDatabase::class.java,
                "smokers"
            ).build()
        }
    }
    return INSTANCE
}
