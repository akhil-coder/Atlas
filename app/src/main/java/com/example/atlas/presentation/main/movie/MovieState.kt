package com.example.atlas.presentation.main.movie

import com.example.atlas.business.domain.models.Movie
import com.example.atlas.business.domain.utils.Queue
import com.example.atlas.business.domain.utils.StateMessage

data class MovieState(
    val isLoading: Boolean = false,
    val movieList: List<Movie> = mutableListOf(),
    val page: Int = 1,
    val isQueryExhausted: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)