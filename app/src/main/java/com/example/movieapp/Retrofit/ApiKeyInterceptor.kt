package com.example.movieapp.Retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiKeyInterceptor(val api_key : String) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("TAG" , "intercept выполняется")
        val request = chain.request().newBuilder()
            .addHeader("Authorization" , "Bearer $api_key")
            .build()
        return chain.proceed(request)
    }

}
object RetrofitInstance {
    val client = OkHttpClient
        .Builder()
        .addInterceptor(
            ApiKeyInterceptor("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMWNkYmNiOGZmZTMzYTNiYzIyOGI0MTEwNGZiZDE0ZiIsIm5iZiI6MTczMTQzMDY5NC4xOTQ5NTYsInN1YiI6IjY3MzM4Nzc5MjlhYThmZjI0NGMwZjk0ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.bzO4IwmiIxUfQrHHDg0zUuQloo29AfsMtW3fEwFGdh0"
            ))
        .build()

    val api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api_service = api.create(Api_Service::class.java)
}

