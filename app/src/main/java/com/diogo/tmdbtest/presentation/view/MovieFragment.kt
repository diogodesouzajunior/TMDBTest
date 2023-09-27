package com.diogo.tmdbtest.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.diogo.tmdbtest.R
import com.diogo.tmdbtest.databinding.FragmentMovieBinding
import com.diogo.tmdbtest.presentation.adapter.EndlessRecyclerViewScrollListener
import com.diogo.tmdbtest.presentation.adapter.MoviesAdapter
import com.diogo.tmdbtest.presentation.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {

    private var binding: FragmentMovieBinding? = null
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    private val scrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(binding?.recyclerView?.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int) {
                viewModel.fetchMoviesByCategory(binding?.spinnerCategories?.selectedItem.toString().toLowerCase().replace(" ", "_"), page)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view) // This line was missing in your code; it's important for binding to work.

        moviesAdapter = MoviesAdapter(mutableListOf()) { movie ->
            // Create a bundle to hold the movie's ID
            val bundle = Bundle()
            bundle.putInt("movieId", movie.id)
            bundle.putString("movieTitle", movie.title)

            // Create a new instance of MovieDetailFragment
            val detailFragment = MovieDetailFragment()
            detailFragment.arguments = bundle

            // Get the FragmentManager and start a transaction
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, detailFragment)  // 'container' is your FrameLayout or any container in your Activity XML
                .addToBackStack(null)  // This allows you to navigate back to the MovieFragment
                .commit()
        }

        binding?.recyclerView?.apply {
            adapter = moviesAdapter
            addOnScrollListener(scrollListener)  // Attach the endless scroll listener here.
        }

        viewModel.movies.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                moviesAdapter.updateList(it)
            }
        }

        binding?.spinnerCategories?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                moviesAdapter.clear()
                scrollListener.resetState() // Reset scroll listener state
                val category = parentView.getItemAtPosition(position).toString().toLowerCase().replace(" ", "_")
                viewModel.fetchMoviesByCategory(category)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing here
            }
        }
    }
}

