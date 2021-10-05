package com.nn.architecture.core.utils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun <T> debounce(delayMs: Long = 500L,
                 coroutineContext: CoroutineContext = Dispatchers.Main,
                 f: (T) -> Unit): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (debounceJob?.isCompleted != false) {
            debounceJob = CoroutineScope(coroutineContext).launch {
                delay(delayMs)
                f(param)
            }
        }
    }
}