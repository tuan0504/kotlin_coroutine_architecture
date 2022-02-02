package com.nn.architecture.core.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.nn.architecture.core.api.Resource
import com.nn.architecture.core.api.Status
import kotlinx.coroutines.*

fun CoroutineScope.debounce(delayMs: Long = 500L, job: Job?, f:() -> Unit): Job {
    job?.cancel()
    return launch {
        delay(delayMs)
        f()
    }
}

fun runWithDispatcher(dispatcher: CoroutineDispatcher = Dispatchers.Main, run: () -> Unit) {
    CoroutineScope(dispatcher).launch { run() }
}

fun <T> LiveData<T>.nonNull(): LiveData<T> {
    val mediator = MediatorLiveData<T>()
    mediator.addSource(this) { it?.let { mediator.value = it } }
    return mediator
}

fun <T> LiveData<Resource<T>>.showDialog(requestDialog: (Boolean) -> Unit): LiveData<Resource<T>> {
    requestDialog(true)
    return this.map { if( it.status != Status.LOADING )  { requestDialog(false) }
        it
    }
}