package com.nn.architecture.features.weathers.respository

import androidx.lifecycle.LiveData
import com.nn.architecture.core.api.Resource
import com.nn.architecture.features.weathers.model.WeatherDailyDataModel

interface WeatherRepository {
    fun getWeathersDailyInCity(cityName: String): LiveData<Resource<List<WeatherDailyDataModel>>>
}