package com.example.movieapp.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.Model.Genre
import com.example.movieapp.Model.ProductionCompany
import com.example.movieapp.Model.ProductionCountry
import com.example.movieapp.Model.SpokenLanguage
import com.example.movieapp.Model.Videos


@Entity(tableName = "favouriteMovies")
data class FavouriteMovie(
    @PrimaryKey(autoGenerate = true)
    val primaryId : Int = 0,
    val title: String?,
    val vote_average: Double?,
)