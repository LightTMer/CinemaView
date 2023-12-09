package com.example.cinemaview

import retrofit2.Call
import retrofit2.http.GET

interface MovieService {
    @GET("movies")
    fun getMovies(): Call<List<MovieEntity>>
}