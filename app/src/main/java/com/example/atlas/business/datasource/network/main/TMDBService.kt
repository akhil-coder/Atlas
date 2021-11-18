package com.example.atlas.business.datasource.network.main

import com.example.atlas.business.datasource.network.main.movie.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apikey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieListDto
}