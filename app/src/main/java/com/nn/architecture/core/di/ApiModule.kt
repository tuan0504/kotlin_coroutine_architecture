package com.nn.architecture.core.di

import com.nn.architecture.core.api.ApiConfig
import com.nn.architecture.core.api.EndPointApi
import com.nn.architecture.core.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    internal fun providesConfigApi(
            okHttpClient: OkHttpClient,
            moshiConverterFactory: MoshiConverterFactory
    ): EndPointApi {
        return getRetrofit(
                okHttpClient,
                moshiConverterFactory
        ).create(EndPointApi::class.java)
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
