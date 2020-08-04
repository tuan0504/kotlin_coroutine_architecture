package com.nn.interview.core.di

import android.content.Context
import androidx.room.Room
import com.nn.interview.core.data.AppDatabase
import com.nn.interview.core.data.DB_NAME
import com.nn.interview.core.data.db.cache.WeatherCityCache
import com.nn.interview.core.data.db.cache.WeatherCityCacheImpl
import com.nn.interview.core.data.db.dao.WeatherCityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    @Named("DB_NAME")
    fun provideDatabaseName(): String = DB_NAME

    @Provides
    @Singleton
    fun provideDatabase(context: Context, @Named("DB_NAME") dbName: String): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, dbName)
                    .build()

    @Provides
    @Singleton
    fun provideWeatherCityDao(appDatabase: AppDatabase): WeatherCityDao = appDatabase.weatherCityDao()

    @Provides
    @Singleton
    fun provideWeatherCityCache(cache: WeatherCityCacheImpl): WeatherCityCache = cache
}
