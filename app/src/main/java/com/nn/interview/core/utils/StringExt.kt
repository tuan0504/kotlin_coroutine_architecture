package com.nn.interview.core.utils

import com.nn.interview.core.exception.ApiException

fun String.appendErrorCode(throwable: Throwable?): String {
    return if (throwable is ApiException && !throwable.errorCode.isNullOrBlank()) {
        this.plus(" [${throwable.errorCode}]")
    } else this
}