package com.nn.architecture.core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FlowUtil(private  val scope: CoroutineScope) {
    private var jobD: Job? = null
    private var jobThrottleFirst: Job? = null

    fun debounce(delayMs : Long, f: () -> Unit) {
        jobD = debounce(delayMs, jobD, f)
    }

    fun throttleFirst( delayMs : Long, f: () -> Unit) {
        jobThrottleFirst = throttleFirst(delayMs, jobThrottleFirst, f)
    }


    private fun debounce(delayMs: Long = 500L, job: Job?, f:() -> Unit): Job {
        job?.cancel()
        return scope.launch {
            delay(delayMs)
            f()
        }
    }

    private fun throttleFirst(delayMs: Long = 500L, job: Job?, f:() -> Unit): Job {
        return if(job == null || job.isCompleted) {
            scope.launch {
                f()
                delay(delayMs)
            }
        } else { job }
    }
}