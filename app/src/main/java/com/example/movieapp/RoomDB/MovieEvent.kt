package com.example.movieapp.RoomDB

import com.example.movieapp.Model.Movie


sealed interface MovieEvent {
    data class SaveMovie(val movie : FavouriteMovie?) : MovieEvent
    data class DeleteMovie(val movie : FavouriteMovie?) : MovieEvent
}