package com.kazimad.movieparser.remote

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @GET("/top.json")
    fun getList(@Query("after") after: String? = null,
                @Query("limit") limit: Int = 10): Observable<Response<TopResponse>>
}