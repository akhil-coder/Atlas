package com.example.atlas.business.interactors.movie

import android.util.Log
import com.example.atlas.business.datasource.cache.movie.MovieDao
import com.example.atlas.business.datasource.cache.movie.toMovie
import com.example.atlas.business.datasource.network.main.TMDBService
import com.example.atlas.business.datasource.network.main.movie.toMovieResponse
import com.example.atlas.business.domain.models.Movie
import com.example.atlas.business.domain.models.toMovieEntity
import com.example.atlas.business.domain.utils.DataState
import com.example.atlas.business.domain.utils.MessageType
import com.example.atlas.business.domain.utils.Response
import com.example.atlas.business.domain.utils.UIComponentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DiscoverMovie(
    private val service: TMDBService,
    private val cache: MovieDao
    ) {
    private val TAG = "DiscoverMovie"
    fun execute(page: Int): Flow<DataState<List<Movie>>> = flow {
        emit(DataState.loading<List<Movie>>())
        if(page == 1) cache.deleteAllMovies()

        try {
            val movieResponse = service.discoverMovies("e9e690206c5709a32678c99230ce1a1e", "en-US", page).toMovieResponse()

            for (movie in movieResponse.movieList){
                try {
                cache.insertMovie(movie.toMovieEntity())
                }catch (e: java.lang.Exception){
                    e.printStackTrace()
                }
            }
        } catch (e: Exception){
            Log.d(TAG, "execute: ${e.message}")
            emit(
                DataState.error<List<Movie>>(
                    response = Response(
                        message = "Unable to update the cache.",
                        uiComponentType = UIComponentType.None(),
                        messageType = MessageType.Error()
                    )
                )
            )
        }

        val cachedMovies = cache.getAllMovies()?.map { it.toMovie() }
        emit(DataState.data(null, cachedMovies))

    }
}