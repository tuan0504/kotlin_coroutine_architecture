package com.nn.architecture.features.weathers.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.nn.architecture.core.api.*
import com.nn.architecture.features.weathers.db.cache.WeatherCityCache
import com.nn.architecture.features.weathers.model.WeatherDailyDataModel
import okhttp3.ResponseBody
import javax.inject.Inject

class WeatherRepositoryImpl
@Inject constructor(
    private val endPointApi: EndPointApi,
    private val weatherCache: WeatherCityCache) : WeatherRepository {
    private val TAG = "WeatherRepository"

    override fun getWeathersDailyInCity(cityName: String): LiveData<Resource<List<WeatherDailyDataModel>>> {
        return object : NetworkBoundResource<List<WeatherDailyDataModel>, ResponseBody>() {
            override fun saveCallResult(data: ResponseBody) {
                 weatherCache.insert(cityName, data.string())
            }

            override fun shouldFetch(data: List<WeatherDailyDataModel>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun loadFromDb() = weatherCache.getWeathersInCity(cityName)

            override fun createCall() = loadWeathersApi(cityName)
        }.asLiveData()
    }

    private fun loadWeathersApi(cityName: String): LiveData<ApiResponse<ResponseBody>> {
        return endPointApi.getWeatherDaily(cityName)
                .map {
                    if(it is ApiErrorResponse){
                        if(it.error.message?.contains("city not found") == true) {
                            ApiSuccessResponse(ResponseBody.create(null, it.error.message?:""))
                        } else it
                    } else it
                }
    }
}
