package com.nn.architecture.core.di

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.nn.architecture.core.data.respository.WeatherRepository
import com.nn.architecture.core.data.respository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class AppModule {

    @Provides
    @Singleton
    fun providesWeatherRepository(repo: WeatherRepositoryImpl): WeatherRepository {
        return repo
    }

    @SuppressLint("HardwareIds")
    @Provides
    @Singleton
    @Named("DEVICE_ID")
    fun providesDeviceId(@ApplicationContext context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}
