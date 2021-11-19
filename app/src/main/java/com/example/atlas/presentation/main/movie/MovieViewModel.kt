package com.example.atlas.presentation.main.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atlas.business.domain.utils.ErrorHandling
import com.example.atlas.business.domain.utils.StateMessage
import com.example.atlas.business.domain.utils.UIComponentType
import com.example.atlas.business.domain.utils.doesMessageAlreadyExistInQueue
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
                    this.state.value = state.copy(movieList = list)
                }

                dataState.stateMessage?.let { stateMessage ->
                    if(stateMessage.response.message?.contains(ErrorHandling.INVALID_PAGE) == true){
                        onUpdateQueryExhausted(true)
                    }else{
                        appendToMessageQueue(stateMessage)
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun appendToMessageQueue(stateMessage: StateMessage){
        state.value?.let { state ->
            val queue = state.queue
            if(!stateMessage.doesMessageAlreadyExistInQueue(queue = queue)){
                if(!(stateMessage.response.uiComponentType is UIComponentType.None)){
                    queue.add(stateMessage)
                    this.state.value = state.copy(queue = queue)
                }
            }
        }
    }

    private fun onUpdateQueryExhausted(isExhausted: Boolean) {
        state.value?.let { state ->
            this.state.value = state.copy(isQueryExhausted = isExhausted)
        }
    }

    private fun incrementPageNumber() {
        state.value?.let { state ->
            this.state.value = state.copy(page = state.page + 1)
        }
    }

    private fun discover() {
        resetPage()
        clearList()
        state.value?.let { state ->
            discoverMovies.execute(page = state.page).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { response ->
                    this.state.value = state.copy(movieList = response)
                }

                dataState.stateMessage?.let { stateMessage ->
                    if(stateMessage.response.message?.contains(ErrorHandling.INVALID_PAGE) == true){
                        onUpdateQueryExhausted(true)
                    }else{
                        appendToMessageQueue(stateMessage)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun clearList() {
        state.value?.let { state ->
            this.state.value = state.copy(movieList = listOf())
        }
    }

    private fun resetPage() {
        state.value = state.value?.copy(page = 1)
        onUpdateQueryExhausted(false)
    }
}