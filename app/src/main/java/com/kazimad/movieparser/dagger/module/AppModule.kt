package com.kazimad.movieparser.dagger.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kazimad.movieparser.BuildConfig
import com.kazimad.movieparser.remote.ApiSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {
    val interceptor: HttpLoggingInterceptor
        @Singleton
        @Provides
        get() = HttpLoggingInterceptor()


    val logLevel: HttpLoggingInterceptor.Level
        @Singleton
        @Provides
        get() = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE


    @Provides
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor.setLevel(logLevel))
            .build()
    }

    @Provides
    fun provideApiInterface(): ApiSource {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiSource.baseUrl)
            .client(getOkHttpClient())
            .build()

        return retrofit.create(ApiSource::class.java)
    }
}