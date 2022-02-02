package com.nn.architecture.features.weathers.db.dao

import com.nn.architecture.blockingObserve
import com.nn.architecture.core.data.DbTest
import com.nn.architecture.features.weathers.db.entity.WeatherCityEntity
import com.nn.architecture.getCurrentDate
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class WeatherCityDaoTest : DbTest() {

    val weather = WeatherCityEntity("name1", "raw1", getCurrentDate())
    private lateinit var dao: WeatherCityDao

    @Before
    fun setup() {
        super.setUp()
        dao = db.weatherCityDao()
    }

    @Test
    fun `test get WeatherCityDao When No User Inserted`() {
        db.runInTransaction {
            val data = dao.getWeathersInCity(weather.cityName).blockingObserve()
            assertEquals(data, null)
        }
    }

    @Test
    fun `test insert WeatherCityDao`() {
        db.runInTransaction {
            dao.insert(weather)
            val data = dao.getWeathersInCity(weather.cityName).blockingObserve()
            assertEquals(data?.cityName, weather.cityName)
            assertEquals(data?.raw, weather.raw)
        }
    }

    @Test
    fun `test delete WeatherCityDao`() {
        db.runInTransaction {
            dao.insert(weather)
            dao.deleteWeathersByCityName(weather.cityName)
            val data = dao.getWeathersInCity(weather.cityName).blockingObserve()
            assertEquals(data, null)
        }
    }

    @Test
    fun `test update WeatherCityDao`() {
        db.runInTransaction {
            dao.insert(weather)
            val data1 = dao.getWeathersInCity(weather.cityName).blockingObserve()
            assertEquals(data1, weather)

            dao.update(weather.cityName, "b2Updated")
            val data2 = dao.getWeathersInCity(weather.cityName).blockingObserve()
            assertEquals(data2?.cityName, weather.cityName)
            assertEquals(data2?.raw, "b2Updated")
        }
    }
}