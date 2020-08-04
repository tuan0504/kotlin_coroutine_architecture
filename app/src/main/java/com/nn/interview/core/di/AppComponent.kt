package com.nn.interview.core.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface AppComponent {

    fun okHttpClient():OkHttpClient
    fun moshiConverterFactory():MoshiConverterFactory
}