package com.nn.architecture.core.di

import android.content.Context
import androidx.room.Room
import com.nn.architecture.core.data.AppDatabase
import com.nn.architecture.core.data.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    @Named("DB_NAME")
    fun provideDatabaseName(): String = DB_NAME

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context, @Named("DB_NAME") dbName: String): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
}
