package com.d2k.task.di

import android.content.Context
import com.d2k.task.networking.ApiClient
import com.d2k.task.networking.IApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideLoginApi(apiClient: ApiClient): IApiService {
        return apiClient.buildApi(IApiService::class.java,context)
    }
}