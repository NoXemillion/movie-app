package com.example.movieapp.ViewModel

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.Model.Movie
import com.example.movieapp.R
import com.example.movieapp.Retrofit.RetrofitInstance
import com.example.movieapp.RoomDB.FavouriteMovie
import com.example.movieapp.RoomDB.MovieDao
import com.example.movieapp.RoomDB.MovieEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Response


class MovieViewModel(dao : MovieDao) : ViewModel() {
    var title = ""
    var vote_average = ""
    var dao = dao
    fun onEvent(event : MovieEvent){
        when(event){
            is MovieEvent.SaveMovie -> {
                val item = event.movie
                title = item?.title.toString()
                vote_average = item?.vote_average.toString()

                var movie = FavouriteMovie(
                    title = title ,
                    vote_average = vote_average.toDouble()
                )
                viewModelScope.launch {
                    dao.insert(movie)
                }
            }
            is MovieEvent.DeleteMovie -> {
                viewModelScope.launch {
                    event.movie?.let { dao.delete(it) }
                }
            }

        }
    }


    var imageLink = "https://image.tmdb.org/t/p/w500/"

    var movieInfo = mutableStateListOf<Movie>()
    var isLoading by mutableStateOf(true)
    val genresList = listOf(
        "action",
        "adventure",
        "animation",
        "biography",
        "comedy",
        "crime",
        "documentary",
        "drama",
        "family",
        "fantasy",
        "history",
        "horror",
        "music",
        "mystery",
        "romance",
        "science fiction",
        "thriller",
        "war",
        "western"
    )
    val actorList = listOf(
        R.drawable.actor1 ,
        R.drawable.actor2 ,
        R.drawable.actor3 ,
        R.drawable.actor4 ,
        R.drawable.actor5
    )
    var advancedMovieInfo = mutableStateOf<Movie?>(null)

    val sortedByPopularity = mutableStateListOf<Movie?>()
    val sortedByReleaseDate = mutableStateListOf<Movie?>()
    val sortedByRating = mutableStateListOf<Movie?>()

    val favList = mutableStateListOf<Movie?>(null)

    val checking = mutableStateOf(false)

    fun getMovie(){
        Log.d("TAG" , "getMovie выполняется")
        viewModelScope.launch {
            isLoading = true
            var result = (300..400).map{ id ->
                async { fetchData(id) }
            }


            result.awaitAll().forEach{ movie ->
                movie?.let { item ->
                    movieInfo.add(
                        Movie(
                            adult = item.adult,
                            backdrop_path = item.budget.toString(),
                            belongs_to_collection = "",
                            budget = item.budget,
                            genres = item.genres,
                            homepage = item.homepage,
                            id = item.id,
                            imdb_id = item.imdb_id,
                            origin_country = item.origin_country,
                            original_language = item.original_language,
                            original_title = item.original_title,
                            overview = item.overview,
                            popularity = item.popularity,
                            poster_path = item.poster_path,
                            production_companies = item.production_companies,
                            production_countries = item.production_countries,
                            release_date = item.release_date,
                            revenue = item.revenue,
                            runtime = item.runtime,
                            spoken_languages = item.spoken_languages,
                            status = item.status,
                            tagline = item.tagline,
                            title = item.title,
                            video = item.video,
                            videos = item.videos,
                            vote_average = item.vote_average,
                            vote_count = item.vote_count
                        )
                    )
                }
            }
            isLoading = false

        }
    }

    suspend fun fetchData(id : Int) : Movie?{
        Log.d("TAG" , "fetchData выполняется")
        return try {
            var response: Response<Movie> = RetrofitInstance.api_service.getMovies(id)
            if (response.isSuccessful) {
                Log.d("TAG", "Проверка 2 : ${response.body()}")
                response.body()
            } else {
                Log.d(
                    "TAG",
                    "Ошибка : ${response.code()} , сообщение = ${response.errorBody()?.string()}"
                )
                null
            }
        }catch(e : Exception){
            Log.d("TAG" , "${e.message}")
            null
        }
    }
}