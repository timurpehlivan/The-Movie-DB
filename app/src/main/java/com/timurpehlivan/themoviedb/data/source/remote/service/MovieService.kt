package com.timurpehlivan.themoviedb.data.source.remote.service

import com.timurpehlivan.themoviedb.data.model.ApiResponseDTO
import com.timurpehlivan.themoviedb.data.model.MovieDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
  @GET("movie/upcoming")
  suspend fun getUpcomingMovies(@Query("api_key") apiKey: String): ApiResponseDTO

  @GET("movie/top_rated")
  suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): ApiResponseDTO

  @GET("movie/popular")
  suspend fun getPopularMovies(@Query("api_key") apiKey: String): ApiResponseDTO

  @GET("search/movie")
  suspend fun searchMovie(@Query("query") query: String, @Query("api_key") apiKey: String): ApiResponseDTO

  @GET("movie/{movie_id}")
  suspend fun getMovieDetails(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): MovieDTO

  @GET("movie/{movie_id}/similar")
  suspend fun getRelatedMovies(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): ApiResponseDTO

}