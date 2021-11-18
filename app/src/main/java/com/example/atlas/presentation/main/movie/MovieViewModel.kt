package com.example.atlas.presentation.main.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atlas.business.interactors.movie.DiscoverMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject
constructor(
    private val discoverMovies: DiscoverMovie,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val TAG = "MovieViewModel"

    val state: MutableLiveData<MovieState> = MutableLiveData(MovieState())

    init {
        onTriggerEvent(event = MovieEvents.MovieDiscover)
        Log.d(TAG, "Inside init: ")
    }

    fun onTriggerEvent(event: MovieEvents) {
        when (event) {
            is MovieEvents.MovieDiscover -> {
                discover()
            }
            is MovieEvents.NextPage -> {
                nextPage()
            }
        }
    }

    private fun nextPage() {
        incrementPageNumber()
        state.value?.let { state ->
            discoverMovies.execute(state.page).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { list ->
                    this.state.value = state.copy(movieList = list.resultsEntity)
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun incrementPageNumber() {
        state.value?.let { state ->
            this.state.value = state.copy(page = state.page + 1)
        }
    }

    private fun discover() {
        state.value?.let { state ->
            discoverMovies.execute(page = state.page).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { list ->
                    this.state.value = state.copy(movieList = list.resultsEntity)
                }
            }.launchIn(viewModelScope)
        }
    }
}