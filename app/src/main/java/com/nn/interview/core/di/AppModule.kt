package com.nn.interview.core.di

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.provider.Settings
import com.nn.interview.MyApplication
import com.nn.interview.core.data.respository.WeatherRepository
import com.nn.interview.core.data.respository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
internal class AppModule {

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext app: Context): Context {
        return app
    }

    @Provides
    @Singleton
    fun providesApplication(app: MyApplication): Application {
        return app
    }

    @Provides
    @Singleton
    fun providesWeatherRepository(repo: WeatherRepositoryImpl): WeatherRepository {
        return repo
    }

    @SuppressLint("HardwareIds")
    @Provides
    @Singleton
    @Named("DEVICE_ID")
    fun providesDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}
