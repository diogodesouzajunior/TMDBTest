package com.diogo.tmdbtest.di

import com.diogo.tmdbtest.data.remote.TmdbApiService
import com.diogo.tmdbtest.data.repository.MovieRepositoryImpl
import com.diogo.tmdbtest.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        apiService: TmdbApiService
    ): MovieRepository {
        return MovieRepositoryImpl(apiService)
    }
}
