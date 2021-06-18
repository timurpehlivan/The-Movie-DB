package com.timurpehlivan.themoviedb.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.timurpehlivan.themoviedb.BuildConfig
import com.timurpehlivan.themoviedb.data.source.remote.datasource.MovieRemoteDataSource
import com.timurpehlivan.themoviedb.data.source.remote.datasource.MovieRemoteDataSourceImp
import com.timurpehlivan.themoviedb.data.repository.MovieRepositoryImp
import com.timurpehlivan.themoviedb.data.source.remote.service.MovieService
import com.timurpehlivan.themoviedb.domain.mapper.MovieMapper
import com.timurpehlivan.themoviedb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Provides
  fun provideGson(): Gson = GsonBuilder().create()

  @Provides
  fun provideRetrofit(gson: Gson): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .client(OkHttpClient())
      .build()
  }

  @Provides
  fun provideMovieAPI(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

  @Provides
  fun provideMovieDataSource(movieService: MovieService): MovieRemoteDataSource = MovieRemoteDataSourceImp(movieService)

  @Provides
  fun provideMovieRepository(
    movieRemoteDataSource: MovieRemoteDataSource,
    movieMapper: MovieMapper,
  ): MovieRepository =
    MovieRepositoryImp(movieRemoteDataSource, movieMapper)
}