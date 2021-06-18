package com.timurpehlivan.themoviedb.presentation.movielist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.timurpehlivan.themoviedb.R
import com.timurpehlivan.themoviedb.databinding.FragmentMovieListBinding
import com.timurpehlivan.themoviedb.domain.DataState
import com.timurpehlivan.themoviedb.domain.model.Movie
import com.timurpehlivan.themoviedb.presentation.common.MovieListAdapter
import com.timurpehlivan.themoviedb.util.displayErrorMessage
import com.timurpehlivan.themoviedb.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MovieListFragment : Fragment() {
  private var _binding: FragmentMovieListBinding? = null
  private val binding get() = _binding!!

  private lateinit var upcomingMovieListAdapter: MovieListAdapter
  private lateinit var latestMovieListAdapter: MovieListAdapter
  private lateinit var popularMovieListAdapter: MovieListAdapter
  private lateinit var searchMovieListAdapter: MovieListAdapter

  private lateinit var searchView: SearchView

  private val viewModel: MovieListViewModel by viewModels()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    setHasOptionsMenu(true)
    _binding = FragmentMovieListBinding.inflate(inflater)
    return binding.root
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_toolbar, menu)
    val searchItem = menu.findItem(R.id.action_search)
    searchView = searchItem.actionView as SearchView
    searchView.onQueryTextChanged {
      viewModel.searchQuery.value = it
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setAdapters()
    collectFlow()
  }

  private fun setAdapters() {
    setUpcomingMovieList()
    setLatestMovieList()
    setPopularMovieList()
    setSearchMovieList()
  }

  private fun setUpcomingMovieList() {
    upcomingMovieListAdapter = MovieListAdapter {
      navigateToMovieDetails(it)
    }
    binding.initialMovieListLayout.upcomingMovieListRecyclerView.apply {
      adapter = upcomingMovieListAdapter
    }
  }

  private fun setLatestMovieList() {
    latestMovieListAdapter = MovieListAdapter {
      navigateToMovieDetails(it)
    }
    binding.initialMovieListLayout.latestMovieListRecyclerView.apply {
      adapter = latestMovieListAdapter
    }
  }

  private fun setPopularMovieList() {
    popularMovieListAdapter = MovieListAdapter {
      navigateToMovieDetails(it)
    }
    binding.initialMovieListLayout.popularMovieListRecyclerView.apply {
      adapter = popularMovieListAdapter
    }
  }

  private fun setSearchMovieList() {
    searchMovieListAdapter = MovieListAdapter {
      navigateToMovieDetails(it)
    }
    binding.searchMovieListRecyclerView.apply {
      adapter = searchMovieListAdapter
    }
  }

  private fun navigateToMovieDetails(movie: Movie) {
    val action = MovieListFragmentDirections.actionListFragmentToMovieDetailsFragment(movie, movie.title)
    findNavController().navigate(action)
  }

  private fun collectFlow() {
    viewModel.apply {
      lifecycleScope.launchWhenStarted {
        initialMovieListStateFlow.collect { dataState ->
          when (dataState) {
            is DataState.Success<Triple<List<Movie>, List<Movie>, List<Movie>>> -> setInitialMovieList(dataState.data)
            is DataState.Error -> showErrorState(dataState.throwable.message)
            is DataState.Loading -> showProgressBar(true)
          }
        }
      }
      lifecycleScope.launchWhenStarted {
        movieListFlow.collect { dataState ->
          when (dataState) {
            is DataState.Success<List<Movie>> -> updateSearchMovieList(dataState.data)
            is DataState.Error -> showErrorState(dataState.throwable.message)
            is DataState.Loading -> showProgressBar(true)
          }
        }
      }
    }
  }

  private fun setInitialMovieList(it: Triple<List<Movie>, List<Movie>, List<Movie>>) {
    upcomingMovieListAdapter.submitList(it.first)
    latestMovieListAdapter.submitList(it.second)
    popularMovieListAdapter.submitList(it.third)
    binding.initialMovieListLayout.root.isVisible = true
    showProgressBar(false)
  }

  private fun updateSearchMovieList(movieList: List<Movie>) {
    if (movieList.isNotEmpty()) {
      binding.searchMovieListRecyclerView.isVisible = true
      binding.initialMovieListLayout.root.isVisible = false
      searchMovieListAdapter.submitList(movieList)
      showProgressBar(false)
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