package com.timurpehlivan.themoviedb.data.source.remote.datasource

import com.timurpehlivan.themoviedb.data.model.MovieDTO
import com.timurpehlivan.themoviedb.data.source.remote.service.MovieService
import javax.inject.Inject

class MovieRemoteDataSourceImp @Inject constructor(
  private val movieService: MovieService
) : MovieRemoteDataSource {
  override suspend fun getUpcomingMovies(): List<MovieDTO> {
    return movieService.getUpcomingMovies(API_KEY).results
  }

  override suspend fun getTopRatedMovies(): List<MovieDTO> {
    return movieService.getTopRatedMovies(API_KEY).results
  }

  override suspend fun getPopularMovies(): List<MovieDTO> {
    return movieService.getPopularMovies(API_KEY).results
  }

  override suspend fun searchMovie(query: String): List<MovieDTO> {
    return movieService.searchMovie(query, API_KEY).results
  }

  override suspend fun getMovieDetails(movieId: Int): MovieDTO {
    return movieService.getMovieDetails(movieId, API_KEY)
  }

  override suspend fun getRelatedMovies(movieId: Int): List<MovieDTO> {
    return movieService.getRelatedMovies(movieId, API_KEY).results
  }

  companion object {
    private const val API_KEY = "35ef0461fc4557cf1d256d3335ed7545"
  }
}