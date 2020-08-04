package com.nn.interview.core.api

import androidx.lifecycle.LiveData
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface InterviewApi {
    @GET(ApiConfig.EndPoint.WEATHER_DAILY)
    fun getWeatherDaily(@Query("q") cityName: String,
                        @Query("cnt") cnt: Int = ApiConfig.CNT,
                        @Query("appId") appId: String = ApiConfig.APPID): LiveData<ApiResponse<ResponseBody>>
}