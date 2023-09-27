package com.diogo.tmdbtest.domain.repository

import com.diogo.tmdbtest.data.remote.Genre
import com.diogo.tmdbtest.data.remote.Movie
import com.diogo.tmdbtest.data.remote.MovieDetail
import com.diogo.tmdbtest.data.remote.MovieResponse
import com.diogo.tmdbtest.data.remote.ProductionCompany
import com.diogo.tmdbtest.data.remote.ProductionCountry
import com.diogo.tmdbtest.data.remote.SpokenLanguage
import com.diogo.tmdbtest.data.remote.TmdbApiService
import com.diogo.tmdbtest.data.repository.MovieRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    private lateinit var repository: MovieRepositoryImpl
    private val apiService: TmdbApiService = mock(TmdbApiService::class.java)

    @Before
    fun setUp() {
        repository = MovieRepositoryImpl(apiService)
    }

    @Test
    fun `getMoviesByCategory success`() = runTest {
        val category = "popular"
        val page = 1
        val mockMovie = Movie(1, "title", "overview", "release_date", "poster_path")
        val mockResponse = MovieResponse(1, 1 , listOf(mockMovie))

        `when`(apiService.getMoviesFromCategory(category, page = page)).thenReturn(mockResponse)

        val result = repository.getMoviesByCategory(category, page)

        assertEquals(Result.success(listOf(mockMovie)), result)
    }

    @Test
    fun `getMoviesByCategory failure`() = runTest {
        val category = "popular"
        val page = 1
        val exception = Exception("API Error")

        `when`(apiService.getMoviesFromCategory(category)).thenThrow(RuntimeException("API Error"))

        val result = repository.getMoviesByCategory(category, page)

        assertTrue(result.isFailure)
        assertEquals("API Error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getMovieDetail success`() = runTest {
        val movieId = 1
        val mockMovieDetail = MovieDetail(
            adult = false,
            backdrop_path = "/path_to_backdrop",
            budget = 1000000,
            genres = listOf(Genre(1, "Action")),
            homepage = "https://example.com",
            id = movieId,
            imdb_id = "tt1234567",
            original_language = "en",
            original_title = "Original Title",
            overview = "This is an overview.",
            popularity = 8.5,
            poster_path = "/path_to_poster",
            production_companies = listOf(ProductionCompany(1, "/logo_path", "Company Name", "US")),
            production_countries = listOf(ProductionCountry("US", "United States")),
            release_date = "2023-09-26",
            revenue = 5000000,
            runtime = 120,
            spoken_languages = listOf(SpokenLanguage("English", "en", "English")),
            status = "Released",
            tagline = "This is a tagline.",
            title = "Test Movie",
            video = false,
            vote_average = 8.5,
            vote_count = 1000
        )

        `when`(apiService.getMovieDetails(movieId)).thenReturn(mockMovieDetail)

        val result = repository.getMovieDetail(movieId)

        assertEquals(Result.success(mockMovieDetail), result)
    }


    @Test
    fun `getMovieDetail failure`() = runTest {
        val movieId = 1

        `when`(apiService.getMovieDetails(movieId)).thenThrow(RuntimeException("API Error"))

        val result = repository.getMovieDetail(movieId)

        assertTrue(result.isFailure)
        assertEquals("API Error", result.exceptionOrNull()?.message)
    }
}
