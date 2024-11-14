package com.example.movieapp.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavouriteMovie::class] , version = 1)
abstract class MovieDatabase : RoomDatabase(){
    abstract val dao : MovieDao
}