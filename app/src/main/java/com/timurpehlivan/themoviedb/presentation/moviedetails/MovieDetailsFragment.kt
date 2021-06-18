package com.timurpehlivan.themoviedb.presentation.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.timurpehlivan.themoviedb.databinding.FragmentMovieDetailsBinding
import com.timurpehlivan.themoviedb.domain.DataState
import com.timurpehlivan.themoviedb.domain.model.Movie
import com.timurpehlivan.themoviedb.presentation.common.MovieListAdapter
import com.timurpehlivan.themoviedb.util.Utils.displayImage
import com.timurpehlivan.themoviedb.util.displayErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlin.math.roundToInt

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
  private var _binding: FragmentMovieDetailsBinding? = null
  private val binding get() = _binding!!

  private lateinit var relatedMovieListAdapter: MovieListAdapter

  private val viewModel: MovieDetailsViewModel by viewModels()
  private val args: MovieDetailsFragmentArgs by navArgs()

  private val movie: Movie by lazy { args.movie }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentMovieDetailsBinding.inflate(inflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setAdapter()
    collectFlow()
    viewModel.getMovieDetails(movie.id)
  }

  private fun setAdapter() {
    relatedMovieListAdapter = MovieListAdapter {
      val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(it, it.title)
      findNavController().navigate(action)
    }
    binding.relatedMovieListRecyclerView.apply {
      adapter = relatedMovieListAdapter
    }
  }

  private fun collectFlow() {
    viewModel.apply {
      lifecycleScope.launchWhenStarted {
        movieDetailsStateFlow.collect { dataState ->
          when (dataState) {
            is DataState.Success<Movie> -> setupView(dataState.data)
            is DataState.Error -> showErrorState(dataState.throwable.message)
            is DataState.Loading -> showProgressBar(true)
          }
        }
      }
      lifecycleScope.launchWhenStarted {
        relatedMovieListStateFlow.collect { dataState ->
          when (dataState) {
            is DataState.Success<List<Movie>> -> relatedMovieListAdapter.submitList(dataState.data)
            is DataState.Error -> showErrorState(dataState.throwable.message)
            is DataState.Loading -> showProgressBar(true)
          }
        }
      }
    }
  }

  private fun setupView(movie: Movie) {
    binding.apply {
      displayImage(movie.poster_path, moviePoster)
      movieOverView.text = movie.overview
      movieAverageVote.text = movie.vote_average.toString()
      moviePopularity.text = movie.popularity.roundToInt().toString()
      movieReleaseDate.text = movie.release_date?.slice(IntRange(0, 3))
      binding.progressBar.root.isVisible = false
      showProgressBar(false)
      moviePoster.setOnClickListener {
        requireActivity().onBackPressed()
      }
    }
  }

  private fun showProgressBar(isVisible: Boolean) {
    binding.progressBar.root.isVisible = isVisible
  }

  private fun showErrorState(errorMessage: String?) {
    displayErrorMessage(errorMessage)
    showProgressBar(false)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}