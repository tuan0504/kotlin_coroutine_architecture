package com.nn.architecture.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nn.architecture.features.weathers.db.dao.WeatherCityDao
import com.nn.architecture.features.weathers.db.entity.WeatherCityEntity

@Database(
        entities = [WeatherCityEntity::class],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherCityDao(): WeatherCityDao
}
