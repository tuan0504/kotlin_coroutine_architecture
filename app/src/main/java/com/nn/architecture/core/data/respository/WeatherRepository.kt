package com.nn.architecture.core.data.respository

import androidx.lifecycle.LiveData
import com.nn.architecture.core.api.Resource
import com.nn.architecture.core.data.model.WeatherDailyDataModel

interface WeatherRepository {
    fun getWeathersDailyInCity(cityName: String): LiveData<Resource<List<WeatherDailyDataModel>>>
}