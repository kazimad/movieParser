package com.kazimad.movieparser.remote

import com.kazimad.movieparser.models.response.TopResponse
import com.kazimad.movieparser.utils.Constants
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("3/discover/movie")
    fun getList(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("primary_release_date.gte") releaseDate: String): Observable<Response<TopResponse>>
}