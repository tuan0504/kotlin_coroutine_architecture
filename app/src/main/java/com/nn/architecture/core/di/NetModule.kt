package com.nn.architecture.core.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.nn.architecture.BuildConfig
import com.nn.architecture.core.utils.JSONParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    private val HTTP_CONNECT_TIMEOUT = 30L
    private val HTTP_WRITE_TIMEOUT = 30L
    private val HTTP_READ_TIMEOUT = 60L

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun providesMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory
            .create(JSONParser.moshi)
            .asLenient()
}