package com.nn.interview.core.data.db.cache

import androidx.lifecycle.LiveData
import com.nn.interview.features.weathers.model.WeatherDailyDataModel

interface WeatherCityCache {

    fun insert(cityName: String, dataRaw: String)

    fun getWeathersInCity(cityName: String): LiveData<List<WeatherDailyDataModel>>

    fun delete(cityName: String)
}
