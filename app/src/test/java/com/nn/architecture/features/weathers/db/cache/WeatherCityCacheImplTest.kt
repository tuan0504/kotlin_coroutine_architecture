package com.nn.architecture.features.weathers.db.cache

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nn.architecture.features.weathers.db.dao.WeatherCityDao
import com.nn.architecture.features.weathers.db.entity.WeatherCityEntity
import com.nn.architecture.getCurrentDate
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class WeatherCityCacheImplTest {

    private var dao = mock<WeatherCityDao>()
    val weather = WeatherCityEntity("name1", "raw1", getCurrentDate())
    private lateinit var weatherCityCacheImpl : WeatherCityCacheImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        weatherCityCacheImpl =
            WeatherCityCacheImpl(dao)
    }

    @Test
    fun `test insert list not empty`() {
        weatherCityCacheImpl.insert(weather.cityName, weather.raw)
        verify(dao).update(weather.cityName, weather.raw)
    }

    @Test
    fun `test list`() {
        weatherCityCacheImpl.getWeathersInCity(weather.cityName)
        verify(dao).getWeathersInCity(weather.cityName)
    }

    @Test
    fun `test delete`() {
        weatherCityCacheImpl.delete(weather.cityName)
        verify(dao).deleteWeathersByCityName(weather.cityName)
    }
}