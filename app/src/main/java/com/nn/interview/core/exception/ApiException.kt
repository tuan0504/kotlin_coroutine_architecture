package com.nn.interview.core.exception

class ApiException ( val errorName: String? = "",
                     val errorCode: String,
                     detailMessage: String? = "",
                     throwable: Throwable?): Exception(detailMessage, throwable)