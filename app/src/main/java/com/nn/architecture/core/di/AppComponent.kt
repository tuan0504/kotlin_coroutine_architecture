package com.nn.architecture.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [
    ApiModule::class,
    AppModule::class,
    DatabaseModule::class,
    NetModule::class
])
@InstallIn(SingletonComponent::class)
interface AppComponent {}