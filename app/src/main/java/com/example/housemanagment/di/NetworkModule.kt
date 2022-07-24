package com.example.housemanagment.di

import com.example.housemanagment.BuildConfig
import com.example.housemanagment.network.auth.ApiService
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
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        return okHttpClient.build()
    }


    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient,baseUrl:String):Retrofit{
//        Timber.e("Thread - %d",Thread.currentThread().name)
//        Log.e("Thread", Thread.currentThread().name.toString())
//        require(Looper.myLooper()==Looper.getMainLooper()){
//            "Wr cannot create this deps on main thread"
//        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesAuthService(retrofit: Retrofit):ApiService = retrofit.create(ApiService::class.java)
}