package com.nn.interview.core.di

import com.nn.interview.BuildConfig
import com.nn.interview.core.utils.JSONParser
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
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