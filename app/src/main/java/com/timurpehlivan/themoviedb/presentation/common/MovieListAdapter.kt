package com.timurpehlivan.themoviedb.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.timurpehlivan.themoviedb.R
import com.timurpehlivan.themoviedb.databinding.ItemMovieListBinding
import com.timurpehlivan.themoviedb.domain.model.Movie
import com.timurpehlivan.themoviedb.util.Utils.displayImage

class MovieListAdapter(
  private val listener: (Movie) -> Unit
) : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(DiffCallback()) {
  inner class MovieViewHolder(private val binding: ItemMovieListBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) {
      binding.apply {
        movie.apply {
          displayImage(poster_path, moviePoster)
          Picasso
            .with(binding.getRoot().context)
            .load(poster_path)
            .error(R.drawable.ic_placeholder)
            .placeholder(R.drawable.ic_placeholder)
            .into(moviePoster)
          movieTitle.text = title
          movieAverageVote.text = "IMBb $vote_average"
          root.setOnClickListener {
            listener(movie)
          }
        }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val binding = ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MovieViewHolder(binding)
  }

  override fun onBindViewHolder(holderMovie: MovieViewHolder, position: Int) {
    val currentItem = getItem(position)
    holderMovie.bind(currentItem)
  }

  class DiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
  }
}