package com.nn.architecture.features.weathers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nn.architecture.BaseRepositoryTest
import com.nn.architecture.core.api.*
import com.nn.architecture.features.weathers.db.cache.WeatherCityCache
import com.nn.architecture.features.weathers.model.WeatherDailyDataModel
import com.nn.architecture.features.weathers.respository.WeatherRepositoryImpl
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest: BaseRepositoryTest() {

    private val api = mock<EndPointApi>()
    private val cache = mock<WeatherCityCache>()
    private val repository = WeatherRepositoryImpl(api, cache)
    private val observer = mock<Observer<Resource<List<WeatherDailyDataModel>>>>()

    val responseBody = "{\n" +
            "  \"city\": {\n" +
            "    \"id\": 1580578,\n" +
            "    \"name\": \"name1\",\n" +
            "    \"coord\": {\n" +
            "      \"lon\": 106.6667,\n" +
            "      \"lat\": 10.8333\n" +
            "    },\n" +
            "    \"country\": \"VN\",\n" +
            "    \"population\": 0,\n" +
            "    \"timezone\": 25200\n" +
            "  },\n" +
            "  \"cod\": \"200\",\n" +
            "  \"message\": 0.0481673,\n" +
            "  \"cnt\": 7,\n" +
            "  \"list\": [\n" +
            "    {\n" +
            "      \"dt\": 1596340800,\n" +
            "      \"sunrise\": 1596321717,\n" +
            "      \"sunset\": 1596367027,\n" +
            "      \"temp\": {\n" +
            "        \"day\": 299.15,\n" +
            "        \"min\": 298.83,\n" +
            "        \"max\": 300.17,\n" +
            "        \"night\": 298.83,\n" +
            "        \"eve\": 300.17,\n" +
            "        \"morn\": 299.15\n" +
            "      },\n" +
            "      \"feels_like\": {\n" +
            "        \"day\": 298.27,\n" +
            "        \"night\": 301.88,\n" +
            "        \"eve\": 300.9,\n" +
            "        \"morn\": 298.27\n" +
            "      },\n" +
            "      \"pressure\": 1,\n" +
            "      \"humidity\": 1,\n" +
            "      \"weather\": [\n" +
            "        {\n" +
            "          \"id\": 502,\n" +
            "          \"main\": \"Rain\",\n" +
            "          \"description\": \"rain\",\n" +
            "          \"icon\": \"10d\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"speed\": 7.24,\n" +
            "      \"deg\": 235,\n" +
            "      \"clouds\": 90,\n" +
            "      \"pop\": 1,\n" +
            "      \"rain\": 16.74\n" +
            "    }\n" +
            "  ]\n" +
            "}"
    val weather = WeatherDailyDataModel("name1", "1-1-2019", "20", 1, 1, "rain")

    @Before
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun `getWeathersDailyInCity should emit error incase database empty and api failed`() {
        val error = Throwable("error")
        whenever(api.getWeatherDaily(weather.city))
            .thenReturn(MutableLiveData<ApiResponse<ResponseBody>>()
                .apply { value = ApiErrorResponse(error) })

        whenever(cache.getWeathersInCity(weather.city))
            .thenReturn(MutableLiveData<List<WeatherDailyDataModel>>()
                .apply { value = listOf() })

        repository.getWeathersDailyInCity(weather.city).observeForever(observer)
        verify(observer).onChanged(Resource.error(error, listOf()))
    }

    @Test
    fun `getWeathersDailyInCity should not call api`() {
        val error = Throwable("error")
        val listData = listOf(weather)
        whenever(api.getWeatherDaily(weather.city))
            .thenReturn(MutableLiveData<ApiResponse<ResponseBody>>()
                .apply { value = ApiErrorResponse(error) })

        whenever(cache.getWeathersInCity(weather.city))
            .thenReturn(MutableLiveData<List<WeatherDailyDataModel>>()
                .apply { value = listData })

        repository.getWeathersDailyInCity(weather.city).observeForever(observer)
        verify(observer).onChanged(Resource.success(listData))
    }

    @Test
    fun `getWeathersDailyInCity should emit success and update database`() {
        testCoroutineRule.runCatching {
            whenever(api.getWeatherDaily(weather.city))
                .thenReturn(MutableLiveData<ApiResponse<ResponseBody>>()
                    .apply { value = ApiSuccessResponse(ResponseBody.create(null, responseBody)) })

            whenever(cache.getWeathersInCity(weather.city))
                .thenReturn(MutableLiveData<List<WeatherDailyDataModel>>()
                    .apply { value = listOf() })
            repository.getWeathersDailyInCity(weather.city).observeForever(observer)
            verify(observer).onChanged(Resource.loading(null))
            verify(cache).insert(weather.city, responseBody)
        }
    }
}