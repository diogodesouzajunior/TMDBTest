package com.diogo.tmdbtest.presentation.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.diogo.tmdbtest.R
import com.diogo.tmdbtest.databinding.FragmentMovieDetailBinding
import com.diogo.tmdbtest.presentation.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private var binding: FragmentMovieDetailBinding? = null
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        (activity as AppCompatActivity).setSupportActionBar(binding?.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        val movieId = arguments?.getInt("movieId")
        val movieTitle = arguments?.getString("movieTitle")
        (activity as AppCompatActivity).supportActionBar?.title = movieTitle

        viewModel.movieDetails.observe(viewLifecycleOwner) { result ->
            result.onSuccess {details ->



                // Use Glide to load the poster image
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500${details.poster_path}")
                    .placeholder(R.drawable.ic_placeholder)
                    .into(binding?.moviePosterImageView!!)

                // Bind the details to the views
                binding?.movieTitleTextView?.text = details.title
                binding?.genresTextView?.text = details.genres.joinToString(", ") { it.name }
                binding?.overviewTextView?.text = details.overview
                binding?.releaseDateTextView?.text = getString(R.string.release_date, details.release_date)
                binding?.runtimeTextView?.text = getString(R.string.runtime, details.runtime)

                // Populate other fields similarly
            }
        }

        if (movieId != null) {
            viewModel.fetchMovieDetails(movieId)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle the back/up icon's click event
                activity?.onBackPressed() // This will take you back to the previous fragment
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
