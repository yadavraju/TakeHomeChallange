package com.raju.takehomecodechallange.di

import com.raju.takehomecodechallange.network.RetrofitBuilder
import com.raju.takehomecodechallange.network.api.UserItemApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(retrofitBuilder: RetrofitBuilder): Retrofit = retrofitBuilder
        .build()

    @Provides
    @Singleton
    fun provideUserItemApi(retrofit: Retrofit): UserItemApi =
        retrofit.create(UserItemApi::class.java)
}
