package com.nn.architecture.features.weathers.db.cache

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.nn.architecture.features.weathers.db.dao.WeatherCityDao
import com.nn.architecture.features.weathers.model.WeatherDailyDataModel
import com.nn.architecture.features.weathers.model.toDataModel
import javax.inject.Inject

class WeatherCityCacheImpl
@Inject constructor(private val weatherCityDao: WeatherCityDao
) : WeatherCityCache {

    override fun insert(cityName: String, dataRaw: String) {
        weatherCityDao.update(cityName, dataRaw)
    }

    override fun getWeathersInCity(cityName: String): LiveData<List<WeatherDailyDataModel>> {
        return weatherCityDao.getWeathersInCity(cityName)
                .map { it?.toDataModel()?: listOf() }
    }

    override fun delete(cityName: String) {
        weatherCityDao.deleteWeathersByCityName(cityName)
    }
}
