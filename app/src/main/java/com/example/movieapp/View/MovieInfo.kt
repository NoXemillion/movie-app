package com.example.movieapp.View

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.R
import com.example.movieapp.RoomDB.FavouriteMovie
import com.example.movieapp.RoomDB.MovieEvent
import com.example.movieapp.ViewModel.MovieViewModel
import com.example.movieapp.ui.theme.DarkBackground
import com.example.movieapp.ui.theme.listGradient
import kotlin.random.Random


@Composable
fun MovieInfo(viewModel : MovieViewModel , navController: NavController ,
              onEvent : (MovieEvent) -> Unit) {

    Box(modifier = Modifier.fillMaxSize().background(DarkBackground)){
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp)) {
                Column(modifier = Modifier.wrapContentWidth().padding(start = 10.dp)) {

                    Box(modifier = Modifier.wrapContentSize()) {
                        Image(
                            painter = rememberAsyncImagePainter("${viewModel.imageLink}${viewModel.advancedMovieInfo.value?.poster_path}"),
                            contentDescription = "movieDisc",
                            modifier = Modifier.width(150.dp).height(250.dp)
                                .clip(RoundedCornerShape(7.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Column(modifier = Modifier.wrapContentSize().padding(start = 10.dp)) {
                    Log.d(
                        "TAG",
                        "Проверка текста ${viewModel.advancedMovieInfo.value?.original_title.toString()}"
                    )
                    Text(
                        text = viewModel.advancedMovieInfo.value?.original_title.toString(),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Rating",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 15.sp,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "${viewModel.advancedMovieInfo.value?.vote_average.toString()}/10",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Runtime",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 15.sp,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "${viewModel.advancedMovieInfo.value?.runtime.toString()}min",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Budget",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 15.sp,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "${viewModel.advancedMovieInfo.value?.budget.toString()}$",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "\"${viewModel.advancedMovieInfo.value?.tagline.toString()}\"",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    )

                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            IconButton(onClick = {
                if(viewModel.checking.value == false){
                    viewModel.favList.add(viewModel.advancedMovieInfo.value)
                    viewModel.advancedMovieInfo.value.let { item ->
                        var movie = FavouriteMovie(
                            title = item?.title ,
                            vote_average = item?.vote_average
                        )
                        onEvent(MovieEvent.SaveMovie(movie))
                    }

                    viewModel.checking.value = true
                }
                else{
                    viewModel.favList.remove(viewModel.advancedMovieInfo.value)
                    viewModel.advancedMovieInfo.value.let { item ->
                        var movie = FavouriteMovie(
                            title = item?.title ,
                            vote_average = item?.vote_average
                        )
                        onEvent(MovieEvent.DeleteMovie(movie))
                    }
                    viewModel.checking.value = false
                }
            }) {
                Icon(painter = painterResource(R.drawable.favourite) , contentDescription = "favourite" ,
                    tint = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 10.dp, top = 20.dp, end = 20.dp)
            ) {
                Text(
                    text = "Synopsis",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontSize = 25.sp,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "${viewModel.advancedMovieInfo.value?.overview.toString()}",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontSize = 20.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.alpha(0.7f)
                )
                Spacer(modifier = Modifier.height(40.dp))
                Row(modifier = Modifier.fillMaxWidth()){
                    viewModel.advancedMovieInfo.value?.genres?.forEach { genre ->
                        Box(modifier = Modifier.width(120.dp).height(30.dp).padding(end = 20.dp)
                            .background(
                                brush = Brush.horizontalGradient(colors = listOf(
                                    listGradient[Random.nextInt(0,10)] , listGradient[Random.nextInt(0,10)])),
                                shape = RoundedCornerShape(12.dp)
                            )){
                            Text(text = "#${genre.name}" , style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.roboto)),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                                modifier = Modifier.align(Alignment.Center) ,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Row(modifier = Modifier.fillMaxWidth() ,
                    horizontalArrangement = Arrangement.SpaceBetween){
                    viewModel.advancedMovieInfo.value?.production_companies?.forEach { company ->
                        Image(painter = rememberAsyncImagePainter("${viewModel.imageLink}${company.logo_path}"),
                            contentDescription = "company" ,
                            modifier = Modifier.width(80.dp).height(80.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop)
                    }
                }
            }
        }
    }
}