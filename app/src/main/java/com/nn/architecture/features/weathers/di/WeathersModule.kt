package com.nn.architecture.features.weathers.di

import com.nn.architecture.core.data.AppDatabase
import com.nn.architecture.features.weathers.db.cache.WeatherCityCache
import com.nn.architecture.features.weathers.db.cache.WeatherCityCacheImpl
import com.nn.architecture.features.weathers.db.dao.WeatherCityDao
import com.nn.architecture.features.weathers.respository.WeatherRepository
import com.nn.architecture.features.weathers.respository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
class WeathersModule {
    @Provides
    @ViewModelScoped
    fun providesWeatherRepository(repo: WeatherRepositoryImpl): WeatherRepository {
        return repo
    }

    @Provides
    @ViewModelScoped
    fun provideWeatherCityDao(appDatabase: AppDatabase): WeatherCityDao = appDatabase.weatherCityDao()

    @Provides
    @ViewModelScoped
    fun provideWeatherCityCache(cache: WeatherCityCacheImpl): WeatherCityCache = cache
}