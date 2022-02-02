package com.nn.architecture.features.weathers

import androidx.lifecycle.*
import com.nn.architecture.core.api.Status
import com.nn.architecture.core.ui.BaseViewModel
import com.nn.architecture.core.ui.BasicLiveEvent
import com.nn.architecture.core.utils.AbsentLiveData
import com.nn.architecture.core.utils.debounce
import com.nn.architecture.core.utils.showDialog
import com.nn.architecture.features.weathers.model.WeatherDailyDataModel
import com.nn.architecture.features.weathers.respository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class WeathersViewModel
@Inject constructor(private val weatherRepository: WeatherRepository): BaseViewModel<WeathersViewModel.LiveEvents>() {
    val TAG = WeathersViewModel::class.simpleName

    private val isQueryData = MutableLiveData(false)
    val queryString = MutableLiveData("")
    val showProcessStatus = MutableLiveData(false)
    val listData : LiveData<List<WeatherDailyDataModel>> = isQueryData.switchMap {
        queryString.value?.let { query -> if(it && query.length > 2) {
            weatherRepository.getWeathersDailyInCity(query).showDialog(this::showProcessBar).map {
                when(it.status) {
                    Status.SUCCESS -> it.data?: listOf()
                    else -> listOf()
                }
            }} else {  AbsentLiveData.create() }
        }?: kotlin.run { AbsentLiveData.create() }
    }.distinctUntilChanged()

    var job: Job? = null
    fun queryWeathersCity() {
        viewModelScope.apply {
            job = debounce(250, job) {
                isQueryData.value = true
            }
        }
    }

    private fun showProcessBar(isShow: Boolean) {
        showProcessStatus.postValue(isShow)
    }

    interface  LiveEvents: BasicLiveEvent
}
