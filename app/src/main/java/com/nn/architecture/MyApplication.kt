package com.nn.architecture

import android.app.Application
import androidx.work.Configuration
import com.nn.architecture.core.utils.CrashlyticsTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    companion object {
        private val TAG = MyApplication::class.java.name
    }

    override fun onCreate() {
        super.onCreate()
        initMainProcess()
    }

    private fun initMainProcess() {
        // Init timber as early as possible
        if (!BuildConfig.DEBUG) {
            Timber.plant(CrashlyticsTree(this))
        } else {
            Timber.plant(Timber.DebugTree())
        }
    }
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().build()
    }
}