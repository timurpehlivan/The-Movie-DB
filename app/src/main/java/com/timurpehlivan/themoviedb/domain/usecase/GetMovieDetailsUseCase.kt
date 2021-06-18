package com.timurpehlivan.themoviedb.domain.usecase

import com.timurpehlivan.themoviedb.domain.DataState
import com.timurpehlivan.themoviedb.domain.model.Movie
import com.timurpehlivan.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {
  suspend fun execute(movieId: Int): Flow<DataState<Movie>> = flow {
    emit(DataState.Loading)
    val movieDetails = movieRepository.getMovieDetails(movieId)
    emit(DataState.Success(movieDetails))
  }.catch {
    emit(DataState.Error(it))
  }
}