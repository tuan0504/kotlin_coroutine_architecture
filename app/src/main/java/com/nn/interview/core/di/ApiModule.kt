package com.nn.interview.core.di

import com.nn.interview.core.api.ApiConfig
import com.nn.interview.core.api.InterviewApi
import com.nn.interview.core.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    @Provides
    internal fun providesInterviewApi(
            okHttpClient: OkHttpClient,
            moshiConverterFactory: MoshiConverterFactory
    ): InterviewApi {
        return getRetrofit(
                okHttpClient,
                moshiConverterFactory
        ).create(InterviewApi::class.java)
    }

    private fun getRetrofit( okHttpClient: OkHttpClient, moshiConverterFactory: MoshiConverterFactory ): Retrofit {
        //add authenticator & interceptor
        val domainOkHttpClient = okHttpClient.newBuilder()

        //create instance of retrofit
        return Retrofit.Builder()
                .baseUrl(ApiConfig.HOST)
                .addConverterFactory(moshiConverterFactory)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(domainOkHttpClient.build())
                .build()
    }
}
