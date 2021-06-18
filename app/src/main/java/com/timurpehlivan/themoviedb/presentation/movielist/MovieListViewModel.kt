package com.timurpehlivan.themoviedb.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timurpehlivan.themoviedb.domain.DataState
import com.timurpehlivan.themoviedb.domain.model.Movie
import com.timurpehlivan.themoviedb.domain.usecase.GetPopularMoviesUseCase
import com.timurpehlivan.themoviedb.domain.usecase.GetTopRatedMoviesUseCase
import com.timurpehlivan.themoviedb.domain.usecase.GetUpcomingMoviesUseCase
import com.timurpehlivan.themoviedb.domain.usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
  private val upcomingMoviesUseCase: GetUpcomingMoviesUseCase,
  private val topRatedMoviesUseCase: GetTopRatedMoviesUseCase,
  private val popularMoviesUseCase: GetPopularMoviesUseCase,
  private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {
  private val _initialMovieListStateFlow = MutableStateFlow<DataState<Triple<List<Movie>, List<Movie>, List<Movie>>>>(DataState.Loading)
  val initialMovieListStateFlow: StateFlow<DataState<Triple<List<Movie>, List<Movie>, List<Movie>>>>
    get() = _initialMovieListStateFlow

  val searchQuery = MutableStateFlow("")
  val movieListFlow = searchQuery.flatMapLatest {
    searchMovieUseCase.execute(it).stateIn(viewModelScope, SharingStarted.Lazily, DataState.Success(emptyList()))
  }

  init {
    viewModelScope.launch {
      val upcomingMovies = upcomingMoviesUseCase.execute().onEach { _initialMovieListStateFlow.value = DataState.Loading }
      val latestMovies = topRatedMoviesUseCase.execute().onEach { _initialMovieListStateFlow.value = DataState.Loading }
      val popularMovies = popularMoviesUseCase.execute().onEach { _initialMovieListStateFlow.value = DataState.Loading }
      combine(upcomingMovies, latestMovies, popularMovies) { _upcomingMovies, _latestMovies, _popularMovies ->
        Triple(_upcomingMovies, _latestMovies, _popularMovies)
      }.catch {
        _initialMovieListStateFlow.value = DataState.Error(it)
      }.collect {
        _initialMovieListStateFlow.value = DataState.Success(it)
      }
    }
  }
}