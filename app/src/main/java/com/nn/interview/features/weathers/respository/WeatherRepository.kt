package com.nn.interview.features.weathers.respository

import androidx.lifecycle.LiveData
import com.nn.interview.core.api.Resource
import com.nn.interview.features.weathers.model.WeatherDailyDataModel

interface WeatherRepository {
    fun getWeathersDailyInCity(cityName: String): LiveData<Resource<List<WeatherDailyDataModel>>>
}