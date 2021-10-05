package com.nn.architecture.core.helper

import com.google.firebase.crashlytics.FirebaseCrashlytics
import okhttp3.Request
import retrofit2.HttpException

class ErrorReporting {
    companion object {
        private val CRASHLYTICS_KEY_PRIORITY = "priority"
        private val CRASHLYTICS_KEY_TAG = "tag"
        private val CRASHLYTICS_MESSAGE = "message"
        val crashlytics
            get() = FirebaseCrashlytics.getInstance()

        fun sendHttpException(httpException: HttpException?) {
            sendException(-1, null, null, httpException)
        }

        fun sendException(throwable: Throwable?) {
            sendException(-1, null, null, throwable)
        }

        fun sendException(priority: Int, tag: String?, message: String?) {
            val throwable = Throwable(message)
            val cleanStackTrace = cleanUpStackTrace(throwable)
            throwable.stackTrace = cleanStackTrace
            sendException(priority, tag, message, throwable)
        }

        /**
         * Send Full exception details to [Crashlytics]
         *
         * @param priority  the [android.util.Log] priority
         * @param tag       the tag used for the log
         * @param message   the log message
         * @param throwable the [Throwable] to send
         */
        fun sendException(priority: Int, tag: String?, message: String?, throwable: Throwable?) {
            throwable?.let {
                if (priority > -1) {
                    crashlytics.setCustomKey(CRASHLYTICS_KEY_PRIORITY, priority)
                }
                tag?.let { crashlytics.setCustomKey(CRASHLYTICS_KEY_TAG, tag) }
                message?.let { crashlytics.setCustomKey(CRASHLYTICS_MESSAGE, message) }
                crashlytics.recordException(it)
            }
        }

        private fun cleanUpStackTrace(e: Throwable): Array<StackTraceElement?> {
            val result = arrayOfNulls<StackTraceElement>(e.stackTrace.size - 1)
            System.arraycopy(e.stackTrace, 1, result, 0, result.size)
            return result
        }

        private fun getHttpSummary(httpException: HttpException): String? {
            val response = httpException.response().raw()
            val request: Request = response.request()
            return request.method() +
                    ' ' + request.url() +
                    '=' + response.code() +
                    ' ' + response.message()
        }
    }
}