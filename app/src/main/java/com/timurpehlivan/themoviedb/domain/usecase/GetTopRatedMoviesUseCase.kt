package com.timurpehlivan.themoviedb.domain.usecase

import com.timurpehlivan.themoviedb.domain.model.Movie
import com.timurpehlivan.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {
  suspend fun execute(): Flow<List<Movie>> = flow {
    val movieList = movieRepository.getTopRatedMovies()
    emit(movieList)
  }
}