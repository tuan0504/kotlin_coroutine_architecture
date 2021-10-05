package com.nn.architecture.core.workbackground.worker

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.nn.architecture.core.helper.ErrorReporting
import timber.log.Timber
import java.util.*

class SendErrorsWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    companion object {
        const val TAG: String = "job_tracking"
        const val EXTRA_DEVICE_ID = "tracking_device_id"
        const val EXTRA_CARRIER = "tracking_carrier"
        private val mapData: HashMap<UUID, Throwable> = hashMapOf()

        fun scheduleInstantly(deviceId: String, carrier: String, throwable: Throwable): OneTimeWorkRequest {
            val data = Data.Builder()
                .putString(EXTRA_DEVICE_ID, deviceId)
                .putString(EXTRA_CARRIER, carrier)
                .build()
            val workerRequest = OneTimeWorkRequest.Builder(SendErrorsWorker::class.java)
                .setInputData(data)
                .addTag(TAG)
                .build()
            Timber.d("Worker instant job: ${workerRequest.id}")

            mapData[workerRequest.id] = throwable
            return workerRequest
        }
    }

    override fun doWork(): Result {
        Timber.tag(TAG).d("Running job: $id")
        ErrorReporting.sendException(mapData.remove(id))
        return Result.success()
    }
}
