package com.nn.architecture.core.utils

import androidx.lifecycle.LiveData

/**
 * A LiveData class that has `null` value.
 */
class AbsentLiveData<T : Any?> private constructor(data: T?) : LiveData<T>() {
    init {
        // use post instead of set since this can be created on any thread
        postValue(data)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData(null)
        }

        fun <T> create(data: T): LiveData<T> {
            return AbsentLiveData(data)
        }
    }
}
