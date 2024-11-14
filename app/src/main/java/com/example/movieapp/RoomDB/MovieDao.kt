package com.example.movieapp.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.movieapp.Model.Movie


@Dao
interface MovieDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insert(movie : FavouriteMovie)

    @Delete
    suspend fun delete(movie : FavouriteMovie)
}