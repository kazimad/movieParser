package com.kazimad.movieparser.remote
import javax.inject.Inject


class ApiSource @Inject constructor() {

    companion object {
        const val baseUrl = "https://api.themoviedb.org/"
        const val baseImageUrl = "https://image.tmdb.org/t/p/w200/"
    }

    var apiInterface: ApiInterface? = null
        @Inject set
}