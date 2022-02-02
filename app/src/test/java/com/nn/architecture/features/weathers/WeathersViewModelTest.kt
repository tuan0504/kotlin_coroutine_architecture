package com.nn.architecture.features.weathers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nn.architecture.BaseViewModelTest
import com.nn.architecture.core.api.Resource
import com.nn.architecture.features.weathers.model.WeatherDailyDataModel
import com.nn.architecture.features.weathers.respository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class WeathersViewModelTest: BaseViewModelTest() {
    private val repo = mock<WeatherRepository>()

    private lateinit var viewModel: WeathersViewModel

    private val weather = WeatherDailyDataModel("name1", "1-1-2019", "20", 1, 1, "rain")

    @Before
    fun setUp() {
        viewModel = spy(WeathersViewModel(repo))
    }

    @Test
    fun `Initialize data for ViewModel`() {
        assertEquals(viewModel.listData.value, null)
        assertEquals(viewModel.queryString.value, "")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `query data with city name`() {
        viewModel.queryString.value = weather.city
        val resource = Resource.success(listOf(weather))
        val observer = mock<Observer<List<WeatherDailyDataModel>>>()

        whenever(repo.getWeathersDailyInCity(weather.city)).thenReturn(MutableLiveData(resource))
        viewModel.listData.observeForever(observer)
        verify(observer).onChanged(null)
        testCoroutineRule.runWithoutDelay { viewModel.queryWeathersCity() }
        verify(observer).onChanged(resource.data)
    }
}