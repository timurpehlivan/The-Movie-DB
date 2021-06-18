package com.timurpehlivan.themoviedb.presentation.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timurpehlivan.themoviedb.domain.DataState
import com.timurpehlivan.themoviedb.domain.model.Movie
import com.timurpehlivan.themoviedb.domain.usecase.GetMovieDetailsUseCase
import com.timurpehlivan.themoviedb.domain.usecase.GetRelatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
  private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
  private val getRelatedMoviesUseCase: GetRelatedMoviesUseCase
) : ViewModel() {
  private val _movieDetailsStateFlow = MutableStateFlow<DataState<Movie>?>(DataState.Loading)
  val movieDetailsStateFlow: StateFlow<DataState<Movie>?>
    get() = _movieDetailsStateFlow

  private val _relatedMovieListStateFlow = MutableStateFlow<DataState<List<Movie>>?>(DataState.Loading)
  val relatedMovieListStateFlow: StateFlow<DataState<List<Movie>>?>
    get() = _relatedMovieListStateFlow

  fun getMovieDetails(movieId: Int) {
    viewModelScope.launch {
      val movieDetails = getMovieDetailsUseCase.execute(movieId)
      val relatedMovieList = getRelatedMoviesUseCase.execute(movieId)
      combine(movieDetails, relatedMovieList) { _movieDetails, _relatedMovieList ->
        Pair(_movieDetails, _relatedMovieList)
      }.collect {
        _movieDetailsStateFlow.value = it.first
        _relatedMovieListStateFlow.value = it.second
      }
    }
  }
}