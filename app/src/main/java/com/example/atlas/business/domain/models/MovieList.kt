package com.example.atlas.business.domain.models

data class MovieList(
    val page: Int,
    val resultsEntity: List<ResultsEntity>,
    val totalPages: Int,
    val totalResults: Int
)

data class ResultsEntity(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
)
