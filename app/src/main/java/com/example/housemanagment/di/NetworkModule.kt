package com.example.housemanagment.di

import com.example.housemanagment.BuildConfig
import com.example.housemanagment.network.auth.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun providesBaseUrl():String{
        return BuildConfig.BASE_URL
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        var okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .readTimeout(10000, TimeUnit.MILLISECONDS)

        return okHttpClient.build()
    }


    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient,baseUrl:String):Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesAuthService(retrofit: Retrofit):AuthService = retrofit.create(AuthService::class.java)
}