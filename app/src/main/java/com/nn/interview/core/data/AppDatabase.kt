package com.nn.interview.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nn.interview.core.data.db.dao.WeatherCityDao
import com.nn.interview.core.data.db.entity.WeatherCityEntity

@Database(
        entities = [WeatherCityEntity::class],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherCityDao(): WeatherCityDao
}
