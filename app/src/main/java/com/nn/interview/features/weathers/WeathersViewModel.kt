package com.nn.interview.features.weathers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.nn.interview.core.api.Resource
import com.nn.interview.core.ui.BaseViewModel
import com.nn.interview.core.ui.BasicLiveEvent
import com.nn.interview.core.utils.AbsentLiveData
import com.nn.interview.core.utils.debounce
import com.nn.interview.core.data.model.WeatherDailyDataModel
import com.nn.interview.core.data.respository.WeatherRepository
import javax.inject.Inject

class WeathersViewModel
@Inject constructor( val weatherRepository: WeatherRepository): BaseViewModel<WeathersViewModel.LiveEvents>() {
    val TAG = WeathersViewModel::class.simpleName

    private val isQueryData = MutableLiveData<Boolean>().apply { value = false}
    val queryString = MutableLiveData<String>().apply { value = "" }
    val listData : LiveData<Resource<List<WeatherDailyDataModel>>> = isQueryData.switchMap {
        if(it == true && !queryString.value.isNullOrEmpty()) {
            weatherRepository.getWeathersDailyInCity(queryString.value!!)
        } else AbsentLiveData.create()
    }

    fun queryWeathersCity() {
        debounce<Boolean>(250) {isQueryData.value = it}(true)
    }

    interface  LiveEvents: BasicLiveEvent
}
