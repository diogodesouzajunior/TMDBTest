package com.diogo.tmdbtest.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.diogo.tmdbtest.data.remote.Movie
import com.diogo.tmdbtest.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: MovieViewModel
    private val movieRepository: MovieRepository = mock(MovieRepository::class.java)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieViewModel(movieRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `fetchMoviesByCategory returns list of movies`() = runBlockingTest {
        // Arrange
        val category = "popular"
        val mockMovies = listOf(
            Movie(id = 1, title = "Movie 1", overview = "Overview 1", release_date = "2022-01-01", poster_path = null),
            Movie(id = 2, title = "Movie 2", overview = "Overview 2", release_date = "2022-02-01", poster_path = "/path/to/poster.jpg")
        )
        `when`(movieRepository.getMoviesByCategory(category, 1)).thenReturn(Result.success(mockMovies))

        // Act
        viewModel.fetchMoviesByCategory(category)

        // Assert
        val result = viewModel.movies.value
        assertEquals(Result.success(mockMovies), result)
    }


    @Test
    fun `fetchMoviesByCategory returns error`() = runBlockingTest {
        // Arrange
        val category = "unknown"
        val error = RuntimeException("Unknown category")
        `when`(movieRepository.getMoviesByCategory(category, 1)).thenThrow(error)

        // Act
        viewModel.fetchMoviesByCategory(category)

        // Assert
        val result = viewModel.movies.value
        assertEquals(Result.failure<List<Movie>>(error), result)
    }
}
