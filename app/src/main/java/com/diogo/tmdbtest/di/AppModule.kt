package com.diogo.tmdbtest.di

import com.diogo.tmdbtest.data.remote.ApiClient
import com.diogo.tmdbtest.data.remote.TmdbApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiService(): TmdbApiService {
        return ApiClient.apiService
    }

    // You'll add more providers here as you progress in the project.
}