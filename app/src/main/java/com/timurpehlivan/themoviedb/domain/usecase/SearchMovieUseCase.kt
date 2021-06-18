package com.timurpehlivan.themoviedb.domain.usecase

import com.timurpehlivan.themoviedb.domain.DataState
import com.timurpehlivan.themoviedb.domain.model.Movie
import com.timurpehlivan.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {
  suspend fun execute(query: String): Flow<DataState<List<Movie>>> = flow {
    if (query.length > MINIMUM_CHARACTER_TO_SEARCH) {
      emit(DataState.Loading)
      val movieList = movieRepository.searchMovie(query)
      emit(DataState.Success(movieList))
    }
  }.catch {
    emit(DataState.Error(it))
  }

  companion object {
    private const val MINIMUM_CHARACTER_TO_SEARCH: Int = 2
  }
}