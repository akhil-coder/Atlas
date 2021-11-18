package com.example.atlas.business.interactors.movie

import android.util.Log
import com.example.atlas.business.datasource.network.main.TMDBService
import com.example.atlas.business.datasource.network.main.movie.toMovieList
import com.example.atlas.business.domain.models.MovieList
import com.example.atlas.business.domain.utils.DataState
import com.example.atlas.business.domain.utils.MessageType
import com.example.atlas.business.domain.utils.Response
import com.example.atlas.business.domain.utils.UIComponentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DiscoverMovie(private val service: TMDBService) {
    private val TAG = "DiscoverMovie"
    fun execute(page: Int): Flow<DataState<MovieList>> = flow {
        emit(DataState.loading<MovieList>())

        try {
            val movieList = service.discoverMovies("e9e690206c5709a32678c99230ce1a1e", "en-US", page).toMovieList()
            Log.d(TAG, "execute: ${movieList.totalPages}")
            emit(DataState.data(response = null, data = movieList))
        } catch (e: Exception){
            Log.d(TAG, "execute: ${e.message}")
            emit(
                DataState.error<MovieList>(
                    response = Response(
                        message = "No network data available",
                        uiComponentType = UIComponentType.None(),
                        messageType = MessageType.Error()
                    )
                )
            )
        }
    }
}