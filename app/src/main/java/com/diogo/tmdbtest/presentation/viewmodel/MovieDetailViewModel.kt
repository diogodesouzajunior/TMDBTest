package com.diogo.tmdbtest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diogo.tmdbtest.data.remote.Movie
import com.diogo.tmdbtest.data.remote.MovieDetail
import com.diogo.tmdbtest.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieDetails = MutableLiveData<Result<MovieDetail>>()
    val movieDetails: LiveData<Result<MovieDetail>> get() = _movieDetails

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val result = movieRepository.getMovieDetail(movieId)
                _movieDetails.value = result
            } catch (e: Exception) {
                // Handle error here
            }
        }
    }
}