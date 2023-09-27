package com.diogo.tmdbtest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diogo.tmdbtest.data.remote.Movie
import com.diogo.tmdbtest.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movies = MutableLiveData<Result<List<Movie>>>()
    val movies: LiveData<Result<List<Movie>>> get() = _movies

    fun fetchMoviesByCategory(category: String, page: Int = 1) {
        viewModelScope.launch {
            try {
                _movies.value = movieRepository.getMoviesByCategory(category, page)
            } catch (e: Exception) {
                _movies.value = Result.failure(e)
            }
        }
    }

}
