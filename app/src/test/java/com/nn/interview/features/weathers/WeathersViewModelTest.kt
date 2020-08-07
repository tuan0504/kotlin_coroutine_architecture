package com.nn.interview.features.weathers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nn.interview.BaseViewModelTest
import com.nn.interview.core.api.Resource
import com.nn.interview.core.data.model.WeatherDailyDataModel
import com.nn.interview.core.data.respository.WeatherRepository
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class WeathersViewModelTest: BaseViewModelTest() {
    private val repo = mock<WeatherRepository>()

    private lateinit var viewModel: WeathersViewModel

    val weather = WeatherDailyDataModel("name1", "1-1-2019", "20", 1, 1, "rain")

    @Before
    override fun setUp() {
        viewModel = WeathersViewModel(repo)
    }

    @Test
    fun `Initialize data for ViewModel`() {
        assertEquals(viewModel.listData.value, null)
        assertEquals(viewModel.queryString.value, "")
    }

    @Test
    fun `query data with city name`() {
        viewModel.queryString.value = weather.city
        val resource = Resource.success(listOf(weather))
        val observer = mock<Observer<Resource<List<WeatherDailyDataModel>>>>()

        testCoroutineRule.runBlockingTest {
            whenever(repo.getWeathersDailyInCity(weather.city))
                .thenReturn(MutableLiveData< Resource<List<WeatherDailyDataModel>>>()
                    .apply { value = resource })

            viewModel.listData.observeForever(observer)
            verify(observer).onChanged(null)

            viewModel.queryWeathersCity()
            delay(500)
            verify(observer).onChanged(resource)
        }
    }
}