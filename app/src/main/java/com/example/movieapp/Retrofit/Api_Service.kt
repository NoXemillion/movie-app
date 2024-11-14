package com.example.movieapp.Retrofit

import com.example.movieapp.Model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api_Service {

    @GET("/3/movie/{id}?api_key=e1cdbcb8ffe33a3bc228b41104fbd14f")
    suspend fun getMovies(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = "e1cdbcb8ffe33a3bc228b41104fbd14f"
    ): Response<Movie>
}