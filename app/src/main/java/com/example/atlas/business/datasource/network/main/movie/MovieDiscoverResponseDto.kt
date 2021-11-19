package com.example.atlas.business.datasource.network.main.movie

import com.example.atlas.business.domain.models.MovieResponse
import com.example.atlas.business.domain.models.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieDiscoverResponseDto(
    @Expose
    @SerializedName("page")
    val page: Int,
    @Expose
    @SerializedName("results")
    val results: List<MovieDto>,
    @Expose
    @SerializedName("total_pages")
    val totalPages: Int,
    @Expose
    @SerializedName("total_results")
    val totalResults: Int
)

data class MovieDto(
    @Expose
    @SerializedName("adult")
    val adult: Boolean,
    @Expose
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @Expose
    @SerializedName("genre_ids")
    val genreIds: List<Integer>,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("original_language")
    val originalLanguage: String,
    @Expose
    @SerializedName("original_title")
    val originalTitle: String,
    @Expose
    @SerializedName("overview")
    val overview: String,
    @Expose
    @SerializedName("popularity")
    val popularity: Double,
    @Expose
    @SerializedName("poster_path")
    val posterPath: String,
    @Expose
    @SerializedName("release_date")
    val releaseDate: String,
    @Expose
    @SerializedName("title")
    val title: String,
    @Expose
    @SerializedName("video")
    val video: Boolean,
    @Expose
    @SerializedName("vote_average")
    val voteAverage: Double,
    @Expose
    @SerializedName("vote_count")
    val voteCount: Int
)

fun MovieDiscoverResponseDto.toMovieResponse(): MovieResponse {
    return MovieResponse(
        page = page,
        movieList = results.map {
            Movie(
                id = it.id,
                title = it.title,
                posterPath = it.posterPath,
                voteAverage = it.voteAverage,
                overview = it.overview,
                releaseDate = it.releaseDate,
                popularity = it.popularity
            )
        },
        totalPages = totalPages,
        totalResults = totalResults
    )
}
