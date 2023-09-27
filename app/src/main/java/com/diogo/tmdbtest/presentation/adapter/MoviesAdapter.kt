package com.diogo.tmdbtest.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diogo.tmdbtest.R
import com.diogo.tmdbtest.data.remote.Movie
import com.diogo.tmdbtest.databinding.ItemMovieBinding

class MoviesAdapter(var movies: MutableList<Movie>, private val onMovieClicked: (Movie) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {

    fun updateList (updatedList: List<Movie>) {
        movies.addAll(updatedList)
        notifyDataSetChanged()
    }

    fun clear () {
        movies.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener { onMovieClicked(movies[position]) }
    }
}

class MovieViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(movie: Movie) {
        binding.movieTitle.text = movie.title
        binding.movieOverview.text = movie.overview
        Glide.with(binding.moviePoster.context)
            .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
            .placeholder(binding.moviePoster.context.getDrawable(R.drawable.ic_placeholder))
            .into(binding.moviePoster)
    }
}
