package com.timurpehlivan.themoviedb.data.source.remote.datasource

import com.timurpehlivan.themoviedb.data.model.MovieDTO

interface MovieRemoteDataSource {
  suspend fun getUpcomingMovies(): List<MovieDTO>
  suspend fun getTopRatedMovies(): List<MovieDTO>
  suspend fun getPopularMovies(): List<MovieDTO>
  suspend fun searchMovie(query: String): List<MovieDTO>
  suspend fun getMovieDetails(movieId: Int): MovieDTO
  suspend fun getRelatedMovies(movieId: Int): List<MovieDTO>
}