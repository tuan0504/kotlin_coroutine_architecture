package com.nn.architecture.features.weathers.model

import android.os.Parcelable
import com.nn.architecture.core.utils.DateUtils
import com.nn.architecture.core.utils.JSONParser
import com.nn.architecture.features.weathers.db.entity.WeatherCityEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonQualifier
import kotlinx.parcelize.Parcelize
import java.util.*

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class CityNameResponse

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class WeatherDescription

/******************************************************************************************
Data Class
 *******************************************************************************************/
@Parcelize
@JsonClass(generateAdapter = true)
data class WeatherDailyDataModel(
        val city : String,
        val date: String? = "",
        val averageTemperature: String? = "",
        val pressure: Long? = 0L,
        val humidity: Int? = 0,
        val description: String? = ""
) : Parcelable

@JsonClass(generateAdapter = true)
data class WeatherResponseData(
        @Json(name = "city")
        val city : CityData? = null,
        @Json(name = "list")
        val weathersData: List<WeatherDailyData>? = listOf()
)

@JsonClass(generateAdapter = true)
data class WeatherDailyData(
        @Json(name = "dt")
        val dateTime : Long? = Calendar.getInstance().timeInMillis,
        @Json(name = "temp")
        val temperature: Map<String, Float?>? = null,
        val pressure: Long? = 0,
        val humidity: Int? = 0,
        val weather: List<WeatherDescriptionData>? = listOf()
)

@JsonClass(generateAdapter = true)
data class CityData(
        @Json(name = "name")
        val name : String? = ""
)

@JsonClass(generateAdapter = true)
data class WeatherDescriptionData(
        @Json(name = "description")
        val description : String? = ""
)

val NO_CITY_NAME = WeatherDailyDataModel("no_city")

fun WeatherCityEntity.toDataModel(): List<WeatherDailyDataModel> {
    val data = JSONParser.fromJson(this.raw, WeatherResponseData::class.java)
    return if(data?.weathersData?.isNotEmpty() == true) {
      data.weathersData.map {
          val date = DateUtils.formatDisplayDateTime(Date(it.dateTime?:System.currentTimeMillis()))
          var temp = it.temperature?.values?.sumOf { value -> (value?.toDouble()?: 0.0) - 273.15}?:0.0
          temp /= (it.temperature?.size ?: 1)

          WeatherDailyDataModel(this.cityName, date, String.format("%.1f", temp), it.pressure, it.humidity, it.weather?.get(0)?.description )
      }
    } else  listOf(NO_CITY_NAME)
}