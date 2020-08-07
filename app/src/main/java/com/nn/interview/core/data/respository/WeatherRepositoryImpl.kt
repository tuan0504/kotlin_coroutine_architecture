package com.nn.interview.core.data.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.nn.interview.core.api.*
import com.nn.interview.core.data.db.cache.WeatherCityCache
import com.nn.interview.core.data.model.WeatherDailyDataModel
import okhttp3.ResponseBody
import javax.inject.Inject

class WeatherRepositoryImpl
@Inject constructor(
        private val interviewApi: InterviewApi,
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
        return interviewApi.getWeatherDaily(cityName)
                .map {
                    if(it is ApiErrorResponse){
                        if(it.error.message?.contains("city not found") == true) {
                            ApiSuccessResponse(ResponseBody.create(null, it.error.message?:""))
                        } else it
                    } else it
                }
    }
}
