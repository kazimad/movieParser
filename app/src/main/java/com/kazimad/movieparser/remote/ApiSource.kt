package com.kazimad.movieparser.remote

import com.kazimad.movieparser.entities.response.TopResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiSource {

    companion object {
        const val baseUrl = "https://api.themoviedb.org/"
        const val baseImageUrl = "https://image.tmdb.org/t/p/w200/"
    }

    @GET("3/discover/movie")
    fun getList(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("primary_release_date.gte") releaseDateGte: String,
        @Query("primary_release_date.lte") releaseDateLte: String
    ): Deferred<Response<TopResponse>>
}