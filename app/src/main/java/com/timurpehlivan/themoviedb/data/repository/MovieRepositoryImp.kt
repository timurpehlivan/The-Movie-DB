package com.timurpehlivan.themoviedb.data.repository

import com.timurpehlivan.themoviedb.data.source.remote.datasource.MovieRemoteDataSource
import com.timurpehlivan.themoviedb.domain.mapper.MovieMapper
import com.timurpehlivan.themoviedb.domain.model.Movie
import com.timurpehlivan.themoviedb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
  private val movieRemoteDataSource: MovieRemoteDataSource,
  private val movieMapper: MovieMapper
) : MovieRepository {
  override suspend fun getUpcomingMovies(): List<Movie> {
    return movieMapper.mapDataModelToDomainModel(movieRemoteDataSource.getUpcomingMovies())
  }

  override suspend fun getTopRatedMovies(): List<Movie> {
    return movieMapper.mapDataModelToDomainModel(movieRemoteDataSource.getTopRatedMovies())
  }

  override suspend fun getPopularMovies(): List<Movie> {
    return movieMapper.mapDataModelToDomainModel(movieRemoteDataSource.getPopularMovies())
  }

  override suspend fun searchMovie(query: String): List<Movie> {
    return movieMapper.mapDataModelToDomainModel(movieRemoteDataSource.searchMovie(query))
  }

  override suspend fun getMovieDetails(movieId: Int): Movie {
    return movieMapper.invoke(movieRemoteDataSource.getMovieDetails(movieId))
  }

  override suspend fun getRelatedMovies(movieId: Int): List<Movie> {
    return movieMapper.mapDataModelToDomainModel(movieRemoteDataSource.getRelatedMovies(movieId))
  }
}