package com.nn.architecture.core.workbackground

import android.content.Context
import android.telephony.TelephonyManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.nn.architecture.core.workbackground.worker.SendErrorsWorker
import javax.inject.Inject
import javax.inject.Named

class WorkManager
@Inject constructor(context: Context, @Named("DEVICE_ID") val deviceId: String) {
    private val workManager: WorkManager = WorkManager.getInstance(context)
    private val telephonyManager: TelephonyManager? = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?

    private val UNKNOWN_CARRIER = "unknown"

    /**
     * Use incase logout , or clean all Workers
     */
    fun cancelAllWorker() {
        workManager.cancelAllWork()
    }

    fun sendErrorReport(throwable: Throwable) {
        val carrier = telephonyManager?.networkOperatorName ?: UNKNOWN_CARRIER
        val workerOneTime: OneTimeWorkRequest = SendErrorsWorker.scheduleInstantly(deviceId, carrier, throwable)
        workManager.enqueue(workerOneTime)
    }
}