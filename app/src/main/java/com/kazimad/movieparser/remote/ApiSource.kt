package com.kazimad.movieparser.remote
import javax.inject.Inject


class ApiSource @Inject constructor() {

    var apiInterface: ApiInterface? = null
        @Inject set
}