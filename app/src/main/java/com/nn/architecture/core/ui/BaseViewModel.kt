package com.nn.architecture.core.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel<T : BasicLiveEvent> : ViewModel() {
    val composite = CompositeDisposable()
    val uiEvent = BaseLiveEvent<T>()

    fun destroy() {
        onCleared()
        composite.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }
}

interface BasicLiveEvent {
    fun showErrorMessage(error: Throwable?)
    fun showErrorMessageLocalized(error: Throwable?)
}