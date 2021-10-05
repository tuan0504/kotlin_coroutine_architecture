package com.nn.architecture.core.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        private const val EEEddMMMyyyy = "EEE, dd MMM yyyy"
        private val basicDateFormat = SimpleDateFormat(EEEddMMMyyyy, Locale.getDefault())

        fun formatDisplayDateTime(date: Date): String {
            return basicDateFormat.format(date)
        }
    }
}