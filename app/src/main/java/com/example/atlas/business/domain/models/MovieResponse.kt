package com.example.atlas.business.domain.models

import com.example.atlas.business.datasource.cache.movie.MovieEntity

data class MovieResponse(
    val page: Int,
    val movieList: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)

data class Movie(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val popularity: Double
)

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        overview = overview,
        releaseDate = releaseDate,
        popularity = popularity
    )
}


