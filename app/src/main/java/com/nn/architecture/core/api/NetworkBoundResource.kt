package com.nn.architecture.core.api

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.nn.architecture.core.utils.runWithDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor() {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            if (response !is ApiLoading) {
                result.removeSource(apiResponse)
                result.removeSource(dbSource)
                when (response) {
                    is ApiSuccessResponse -> {
                        runWithDispatcher(Dispatchers.IO) {
                            saveCallResult(processResponse(response))
                            runWithDispatcher {
                                result.addSource(loadFromDb()) { newData ->
                                    setValue(Resource.success(newData))
                                }
                            }
                        }
                    }
                    is ApiEmptyResponse -> {
                        runWithDispatcher {
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                    is ApiErrorResponse -> {
                        onFetchFailed()
                        result.addSource(dbSource) { newData ->
                            setValue(Resource.error(response.error, newData))
                        }
                    }
                    else -> {
                        onFetchFailed()
                        result.addSource(dbSource) { newData ->
                            setValue(Resource.error(Throwable("unknown"), newData))
                        }
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(data: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}

/**
 * This method is used for call api without database (call directly)
 */
fun <RequestType> LiveData<ApiResponse<RequestType>>.toResource(): LiveData<Resource<RequestType>> {
    return this@toResource.map { apiResponse ->
        when (apiResponse) {
            is ApiLoading -> {
                Resource.loading(null)
            }
            is ApiSuccessResponse -> {
                Resource.success(apiResponse.body)
            }
            is ApiEmptyResponse -> {
                Resource.success(null)
            }
            is ApiErrorResponse -> {
                Resource.error(apiResponse.error, null)
            }
        }
    }
}