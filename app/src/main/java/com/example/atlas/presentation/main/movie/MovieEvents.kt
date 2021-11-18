package com.example.atlas.presentation.main.movie

import com.example.atlas.business.domain.utils.StateMessage

sealed class MovieEvents {
    data class MovieSearch(val postId: Int) : MovieEvents()

    object MovieDiscover : MovieEvents()

    object NextPage : MovieEvents()

    object OnRemoveHeadFromQueue : MovieEvents()

    data class Error(val stateMessage: StateMessage) : MovieEvents()
}