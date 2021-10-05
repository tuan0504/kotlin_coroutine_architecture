package com.nn.architecture.core.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class BaseLiveEvent<T> : SingleLiveEvent<(T.() -> Unit)?>() {
    fun setEventReceiver(owner: LifecycleOwner, receiver: T) {
        observe(owner, Observer { event ->
            if ( event != null ) {
                receiver.event()
            }
        })
    }

    fun sendEvent(event: (T.() -> Unit)?) {
        value = event
    }

    fun postEvent(event: (T.() -> Unit)?) {
        postValue(event)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setEventReceiverForTesting(receiver: T) {
        observeForever { event ->
            if (event != null) {
                receiver.event()
            }
        }
    }

}