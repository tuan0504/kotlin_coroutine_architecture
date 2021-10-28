package com.nn.architecture.features.weathers.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nn.architecture.core.data.TABLE_WEATHER_CITY
import com.nn.architecture.features.weathers.db.entity.WeatherCityEntity
import java.util.*

@Dao
interface WeatherCityDao {

    @Query("SELECT * FROM $TABLE_WEATHER_CITY WHERE cityName= :cityName AND updateTime = :currentDateTime")
    fun getWeathersInCity(cityName: String, currentDateTime: String = getCurrentDate()): LiveData<WeatherCityEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: WeatherCityEntity)

    @Query("DELETE FROM $TABLE_WEATHER_CITY WHERE cityName = :cityName")
    fun deleteWeathersByCityName(cityName: String)

    @Transaction
    fun update(cityName: String, data: String) {
        deleteWeathersByCityName(cityName)
        insert(WeatherCityEntity(cityName, data, getCurrentDate()))
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        return "${calendar[Calendar.YEAR]}/${calendar[Calendar.DAY_OF_YEAR]}"

    }
}
