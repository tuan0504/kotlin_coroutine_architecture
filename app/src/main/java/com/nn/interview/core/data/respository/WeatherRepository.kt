package com.nn.interview.core.data.respository

import androidx.lifecycle.LiveData
import com.nn.interview.core.api.Resource
import com.nn.interview.core.data.model.WeatherDailyDataModel

interface WeatherRepository {
    fun getWeathersDailyInCity(cityName: String): LiveData<Resource<List<WeatherDailyDataModel>>>
}