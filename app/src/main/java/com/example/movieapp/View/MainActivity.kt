package com.example.movieapp.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.movieapp.RoomDB.MovieDatabase
import com.example.movieapp.ViewModel.MovieViewModel
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext ,
            MovieDatabase::class.java ,
            "movies.db"
        ).build()
    }

    private val viewModel by viewModels<MovieViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MovieViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                Main(viewModel)
            }
        }
    }
}

@Composable
fun Main(viewModel : MovieViewModel){
    var navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main"
    ){
        composable("main") { MovieScreen(viewModel = viewModel , navController = navController) }
        composable("movieInfo") { MovieInfo(viewModel = viewModel , navController = navController , onEvent = viewModel::onEvent) }
    }
}

