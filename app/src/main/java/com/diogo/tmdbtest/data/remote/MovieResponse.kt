package com.diogo.tmdbtest.data.remote

data class MovieResponse(
    val page: Int,
    val totalPages: Int,
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String?
)