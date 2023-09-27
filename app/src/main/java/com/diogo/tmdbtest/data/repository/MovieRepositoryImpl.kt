package com.diogo.tmdbtest.data.repository

import com.diogo.tmdbtest.data.remote.Movie
import com.diogo.tmdbtest.data.remote.MovieDetail
import com.diogo.tmdbtest.data.remote.TmdbApiService
import com.diogo.tmdbtest.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
) : MovieRepository {

    override suspend fun getMoviesByCategory(category: String, page: Int): Result<List<Movie>> {
        return try {
                val moviesFromApi = apiService.getMoviesFromCategory(category, page = page)
                val movieEntities = moviesFromApi.results.map {
                    Movie(
                        it.id, it.title, it.overview,
                        it.release_date, it.poster_path
                    )
                }
                Result.success(movieEntities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return try {
            val movieFromApi = apiService.getMovieDetails(movieId)
            Result.success(movieFromApi)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
