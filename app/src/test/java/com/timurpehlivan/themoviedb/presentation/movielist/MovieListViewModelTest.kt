package com.timurpehlivan.themoviedb.presentation.movielist

import com.timurpehlivan.themoviedb.domain.usecase.GetPopularMoviesUseCase
import com.timurpehlivan.themoviedb.domain.usecase.GetTopRatedMoviesUseCase
import com.timurpehlivan.themoviedb.domain.usecase.GetUpcomingMoviesUseCase
import com.timurpehlivan.themoviedb.domain.usecase.SearchMovieUseCase
import com.timurpehlivan.themoviedb.util.MainCoroutineRule
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class MovieListViewModelTest {
  @get:Rule
  val testCoroutineRule = MainCoroutineRule()

  private lateinit var viewModel: MovieListViewModel
  private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase = mock()
  private val getTopRelatedMoviesUseCase: GetTopRatedMoviesUseCase = mock()
  private val getPopularMoviesUseCase: GetPopularMoviesUseCase = mock()
  private val searchMovieUseCase: SearchMovieUseCase = mock()

  @Before
  fun setUp() {
    viewModel = MovieListViewModel(
      getUpcomingMoviesUseCase,
      getTopRelatedMoviesUseCase,
      getPopularMoviesUseCase,
      searchMovieUseCase
    )
  }

  @Test
  fun getMovieDetails() = runBlockingTest {
    verify(getUpcomingMoviesUseCase, times(1)).execute()
    verify(getTopRelatedMoviesUseCase, times(1)).execute()
    verify(getPopularMoviesUseCase, times(1)).execute()
  }
}