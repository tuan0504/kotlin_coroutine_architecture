package com.nn.architecture.core.api

import com.nn.architecture.BuildConfig

object ApiConfig {
    const val HOST = "https://api.openweathermap.org"
    const val CNT= 7
    const val APPID= BuildConfig.APPID_KEY

    object EndPoint {
        const val WEATHER_DAILY = "/data/2.5/forecast/daily"
    }
}