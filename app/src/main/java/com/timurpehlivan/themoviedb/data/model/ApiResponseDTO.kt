package com.timurpehlivan.themoviedb.data.model

data class ApiResponseDTO(
  val page: Int,
  val total_results: Int,
  val total_pages: Int,
  val results: List<MovieDTO>
)