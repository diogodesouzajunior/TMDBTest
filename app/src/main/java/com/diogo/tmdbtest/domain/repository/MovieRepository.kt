package com.diogo.tmdbtest.domain.repository

import com.diogo.tmdbtest.data.remote.Movie
import com.diogo.tmdbtest.data.remote.MovieDetail

interface MovieRepository {
    suspend fun getMoviesByCategory(category: String, page: Int): Result<List<Movie>>
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>
    // You can add other methods as required.
}