package com.timurpehlivan.themoviedb.domain.repository

import com.timurpehlivan.themoviedb.domain.model.Movie

interface MovieRepository {
  suspend fun getUpcomingMovies(): List<Movie>
  suspend fun getTopRatedMovies(): List<Movie>
  suspend fun getPopularMovies(): List<Movie>
  suspend fun searchMovie(query: String): List<Movie>
  suspend fun getMovieDetails(movieId: Int): Movie
  suspend fun getRelatedMovies(movieId: Int): List<Movie>
}