package com.diogo.tmdbtest.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.diogo.tmdbtest.data.remote.Genre
import com.diogo.tmdbtest.data.remote.MovieDetail
import com.diogo.tmdbtest.data.remote.ProductionCompany
import com.diogo.tmdbtest.data.remote.ProductionCountry
import com.diogo.tmdbtest.data.remote.SpokenLanguage
import com.diogo.tmdbtest.domain.repository.MovieRepository
import com.diogo.tmdbtest.domain.utils.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    // Mock the MovieRepository
    private val mockRepository: MovieRepository = mock()

    // Instantiate the ViewModel with the mock repository
    private lateinit var viewModel: MovieDetailViewModel
    // Rule to make LiveData call values immediately
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieDetailViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `fetchMovieDetails returns success`() = runTest {
        // Given
        val movieId = 1
        val mockMovieDetail = Result.success(MovieDetail(
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
        )) // Provide mock data here

        whenever(mockRepository.getMovieDetail(movieId)).thenReturn(mockMovieDetail)

        // When
        viewModel.fetchMovieDetails(movieId)

        // Then
        assert(viewModel.movieDetails.value == mockMovieDetail)
    }

}
