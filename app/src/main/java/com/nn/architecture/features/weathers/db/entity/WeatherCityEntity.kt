package com.nn.architecture.features.weathers.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nn.architecture.core.data.TABLE_WEATHER_CITY

@Entity(tableName = TABLE_WEATHER_CITY)
data class WeatherCityEntity(
        @PrimaryKey
        @ColumnInfo(name = "cityName")
        val cityName: String,
        @ColumnInfo(name = "raw")
        val raw: String,
        @ColumnInfo(name = "updateTime")
        val updateTime: String
)
