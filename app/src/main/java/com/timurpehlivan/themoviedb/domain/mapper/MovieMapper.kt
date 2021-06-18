package com.timurpehlivan.themoviedb.domain.mapper

import com.timurpehlivan.themoviedb.data.model.MovieDTO
import com.timurpehlivan.themoviedb.domain.model.Movie
import com.timurpehlivan.themoviedb.util.Utils.formatImagePathToDisplay
import javax.inject.Inject

class MovieMapper @Inject constructor() : Function1<MovieDTO, Movie> {
  override fun invoke(movieDTO: MovieDTO): Movie {
    return with(movieDTO) {
      val posterPath =
        formatImagePathToDisplay(INITIAL_PATH_FOR_IMAGE, movieDTO.poster_path)
      val backdropPath =
        formatImagePathToDisplay(INITIAL_PATH_FOR_IMAGE, movieDTO.backdrop_path)

      Movie(
        posterPath,
        adult,
        overview,
        release_date,
        id,
        original_title,
        original_language,
        title,
        backdropPath,
        popularity,
        vote_count,
        video,
        vote_average
      )
    }
  }

  fun mapDataModelToDomainModel(movieDTOList: List<MovieDTO>): List<Movie> {
    return movieDTOList.map { invoke(it) }
  }

  companion object {
    private const val INITIAL_PATH_FOR_IMAGE = "http://image.tmdb.org/t/p/w500"
  }
}