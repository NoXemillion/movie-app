package com.example.movieapp.View

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.Model.Movie
import com.example.movieapp.R
import com.example.movieapp.ViewModel.MovieViewModel
import com.example.movieapp.ui.theme.BlackBackground
import com.example.movieapp.ui.theme.DarkBackground
import com.example.movieapp.ui.theme.Grey
import com.example.movieapp.ui.theme.GreyBackground
import com.example.movieapp.ui.theme.listGradient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.random.Random






@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MovieScreen(viewModel : MovieViewModel  , navController: NavController) {


    LaunchedEffect(Unit) {
        viewModel.getMovie()
    }

    var searchText by remember {
        mutableStateOf("")
    }

    var filteredText = viewModel.movieInfo.filter {
        it.original_title?.contains(searchText , ignoreCase = true) ?: false
    }

    var isVisible by remember {
        mutableStateOf(true)
    }

    var exactCard by remember {
        mutableStateOf(false)
    }

    var titleName by remember {
        mutableStateOf("")
    }

    var selectedOption by remember { mutableStateOf("Popularity") }
    var finallySelected by remember { mutableStateOf("Popularity") }
    val options = listOf("Popularity" , "ReleaseDate" , "Rating")
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var scope = rememberCoroutineScope()




    if(viewModel.isLoading == false) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Box(modifier = Modifier.fillMaxSize().background(DarkBackground)){
                        FlowRow(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                            viewModel.favList.forEach { item ->
                                Box(
                                    modifier = Modifier.width(150.dp).height(300.dp)
                                        .padding(end = 20.dp).clickable {
                                            viewModel.advancedMovieInfo.value = item
                                            navController.navigate("movieInfo")
                                        }
                                ) {
                                    Column(modifier = Modifier.wrapContentSize()) {
                                        Image(
                                            painter = rememberAsyncImagePainter("${viewModel.imageLink}${item?.poster_path}"),
                                            contentDescription = "movie_image",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.wrapContentWidth()
                                                .height(200.dp)
                                                .clip(RoundedCornerShape(3.dp))
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Text(
                                            text = item?.original_title.toString(),
                                            style = TextStyle(
                                                fontFamily = FontFamily(Font(R.font.roboto)),
                                                fontSize = 15.sp,
                                                color = Color.White
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))

                                        Text(
                                            text = item?.genres?.joinToString(
                                                ","
                                            ) { genre ->
                                                genre.name
                                            } ?: "", style =
                                            TextStyle(
                                                fontFamily = FontFamily(Font(R.font.roboto)),
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            ))

                                        Spacer(modifier = Modifier.height(5.dp))
                                        Text(
                                            text = "Release Date : ${item?.release_date.toString()}",
                                            style = TextStyle(
                                                fontFamily = FontFamily(Font(R.font.roboto)),
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )
                                        )

                                    }
                                }
                            }

                        }
                    }
                }
            } ,
            drawerState = drawerState
        ) {
            Scaffold() { paddingValues ->
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues)
                        .background(DarkBackground)
                ) {

                    Column(modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 10.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .padding(end = 10.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            GreyBackground,
                                            BlackBackground
                                        )
                                    ),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .border(
                                    width = 2.dp,
                                    color = Grey,
                                    shape = RoundedCornerShape(6.dp)
                                )
                        ) {
                            TextField(
                                value = searchText,
                                onValueChange = { searchText = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text(text = "Search here..") },
                                trailingIcon = {
                                    IconButton(onClick = {}) {
                                        Icon(
                                            painter = painterResource(R.drawable.search),
                                            contentDescription = "search",
                                            tint = Color.Gray
                                        )
                                    }
                                },
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedTextColor = Color.White,
                                    containerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent
                                )
                            )
                        }
                        if (searchText.isNotBlank()) {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth().then(
                                    if (isVisible) Modifier else Modifier.size(0.dp)
                                ).zIndex(2f)
                            )
                            {
                                items(filteredText.size) {
                                        item ->
                                    Text(
                                        text = filteredText[item].original_title.toString(),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp).clickable {
                                                searchText = filteredText[item].original_title.toString()
                                                isVisible = false
                                                exactCard = true
                                            } ,
                                        style = TextStyle(
                                            color = Color.White
                                        )
                                    )
                                    titleName = filteredText[item].original_title.toString()
                                }
                            }
                        } else {
                            isVisible = true
                            exactCard = false
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "Popular Movies", style = TextStyle(
                                    fontSize = 30.sp,
                                    fontFamily = FontFamily(Font(R.font.plusjakarta)),
                                    color = Color.White
                                )
                            )
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.left_modal),
                                    contentDescription = "modalDrawer",
                                    tint = Color.Gray
                                )
                            }


                        }
                        Log.d("TAG", "Таак ${viewModel.movieInfo}")

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth()){
                            options.forEach { item ->
                                Row(modifier = Modifier.wrapContentWidth()){
                                    Text(text = item , style = TextStyle(
                                        color = Color.White)
                                    )
                                    RadioButton(
                                        selected = (item == selectedOption),
                                        onClick = { selectedOption = item
                                        finallySelected = item}
                                    )
                                }

                            }
                        }
                        when (finallySelected) {
                            options[0] -> {
                                val newMovies = viewModel.movieInfo.sortedByDescending { it.popularity }
                                viewModel.sortedByPopularity.clear()
                                viewModel.sortedByPopularity.addAll(newMovies.distinctBy{it.id})
                            }
                            options[1] -> {
                                val newMovies = viewModel.movieInfo.sortedByDescending { it.release_date }
                                viewModel.sortedByReleaseDate.clear()
                                viewModel.sortedByReleaseDate.addAll(newMovies.distinctBy{it.id})
                            }
                            options[2] -> {
                                val newMovies = viewModel.movieInfo.sortedByDescending { it.release_date }
                                viewModel.sortedByRating.clear()
                                viewModel.sortedByRating.addAll(newMovies.distinctBy{it.id})
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyRow(modifier = Modifier.fillMaxWidth()) {


                            val listToDisplay = when (finallySelected) {
                                options[0] -> viewModel.sortedByPopularity
                                options[1] -> viewModel.sortedByReleaseDate
                                options[2] -> viewModel.sortedByRating
                                else -> viewModel.movieInfo
                            }

                            items(listToDisplay.size) { item ->
                                if(exactCard) {
                                    if(titleName.equals(listToDisplay.getOrNull(item)?.original_title.toString())) {
                                        Box(
                                            modifier = Modifier.width(150.dp).height(300.dp)
                                                .padding(end = 20.dp).clickable {
                                                    viewModel.advancedMovieInfo.value = listToDisplay.getOrNull(item)
                                                    navController.navigate("movieInfo")
                                                }
                                        ) {
                                            Column(modifier = Modifier.wrapContentSize()) {
                                                Image(
                                                    painter = rememberAsyncImagePainter("${viewModel.imageLink}${listToDisplay.getOrNull(item)?.poster_path}"),
                                                    contentDescription = "movie_image",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier.wrapContentWidth()
                                                        .height(200.dp)
                                                        .clip(RoundedCornerShape(3.dp))
                                                )
                                                Spacer(modifier = Modifier.height(5.dp))
                                                Text(
                                                    text = listToDisplay.getOrNull(item)?.original_title.toString(),
                                                    style = TextStyle(
                                                        fontFamily = FontFamily(Font(R.font.roboto)),
                                                        fontSize = 15.sp,
                                                        color = Color.White
                                                    )
                                                )
                                                Spacer(modifier = Modifier.height(5.dp))

                                                Text(
                                                    text = listToDisplay.getOrNull(item)?.genres?.joinToString(
                                                        ","
                                                    ) { genre ->
                                                        genre.name
                                                    } ?: "", style =
                                                    TextStyle(
                                                        fontFamily = FontFamily(Font(R.font.roboto)),
                                                        fontSize = 12.sp,
                                                        color = Color.Gray
                                                    ))

                                                Spacer(modifier = Modifier.height(5.dp))
                                                Text(
                                                    text = "Release Date : ${listToDisplay.getOrNull(item)?.release_date.toString()}",
                                                    style = TextStyle(
                                                        fontFamily = FontFamily(Font(R.font.roboto)),
                                                        fontSize = 12.sp,
                                                        color = Color.Gray
                                                    )
                                                )

                                            }
                                        }
                                    }
                                }
                                else{
                                    Box(
                                        modifier = Modifier.width(150.dp).height(300.dp)
                                            .padding(end = 20.dp).clickable {
                                                viewModel.advancedMovieInfo.value = listToDisplay.getOrNull(item)
                                                navController.navigate("movieInfo")
                                            }
                                    ) {
                                        Column(modifier = Modifier.wrapContentSize()) {
                                            Image(
                                                painter = rememberAsyncImagePainter("${viewModel.imageLink}${listToDisplay.getOrNull(item)?.poster_path}"),
                                                contentDescription = "movie_image",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.wrapContentWidth()
                                                    .height(200.dp)
                                                    .clip(RoundedCornerShape(3.dp))
                                            )
                                            Spacer(modifier = Modifier.height(5.dp))
                                            Text(
                                                text = listToDisplay.getOrNull(item)?.original_title.toString(),
                                                style = TextStyle(
                                                    fontFamily = FontFamily(Font(R.font.roboto)),
                                                    fontSize = 15.sp,
                                                    color = Color.White
                                                )
                                            )
                                            Spacer(modifier = Modifier.height(5.dp))

                                            Text(
                                                text = listToDisplay.getOrNull(item)?.genres?.joinToString(
                                                    ","
                                                ) { genre ->
                                                    genre.name
                                                } ?: "", style =
                                                TextStyle(
                                                    fontFamily = FontFamily(Font(R.font.roboto)),
                                                    fontSize = 12.sp,
                                                    color = Color.Gray
                                                ))

                                            Spacer(modifier = Modifier.height(5.dp))
                                            Text(
                                                text = "Release Date : ${listToDisplay.getOrNull(item)?.release_date.toString()}",
                                                style = TextStyle(
                                                    fontFamily = FontFamily(Font(R.font.roboto)),
                                                    fontSize = 12.sp,
                                                    color = Color.Gray
                                                )
                                            )

                                        }
                                    }
                                }
                            }
                        }


                        Spacer(modifier = Modifier.height(20.dp))

                        Column(modifier = Modifier.fillMaxWidth()){
                            Text(
                                text = "Genres", style = TextStyle(
                                    fontSize = 30.sp,
                                    fontFamily = FontFamily(Font(R.font.plusjakarta)),
                                    color = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            LazyRow(modifier = Modifier.fillMaxWidth()){
                                items(18){ item ->
                                    Box(modifier = Modifier.width(150.dp).height(50.dp).padding(end = 20.dp)
                                        .background(
                                            brush = Brush.horizontalGradient(colors = listOf(
                                                listGradient[Random.nextInt(0,10)] , listGradient[Random.nextInt(0,10)])),
                                            shape = RoundedCornerShape(6.dp)
                                        )){
                                        Text(text = "#${viewModel.genresList[item]}" , style = TextStyle(
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
                        }

                        Spacer(modifier = Modifier.height(30.dp))

                        Column(modifier = Modifier.fillMaxWidth()){
                            Text(
                                text = "Top Actors", style = TextStyle(
                                    fontSize = 30.sp,
                                    fontFamily = FontFamily(Font(R.font.plusjakarta)),
                                    color = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(modifier = Modifier.fillMaxWidth() ,
                                horizontalArrangement = Arrangement.SpaceBetween){

                                viewModel.actorList.forEach{ actor ->
                                    Image(painter = painterResource(actor), contentDescription = "actor",modifier = Modifier.width(60.dp).height(60.dp).clip(
                                        CircleShape)  , contentScale = ContentScale.Crop)
                                }

                            }
                        }



                    }
                }
            }
        }
    }else{
        Box(modifier = Modifier.fillMaxSize() ,
            contentAlignment = Alignment.Center,
        ){
            CircularProgressIndicator()
        }

    }



}

