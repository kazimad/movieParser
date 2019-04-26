package com.kazimad.movieparser.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Constants {

    companion object {
        const val API_KEY = "e9cc9ebba25fe2aa6258bd675d3474b0"
//        const val API_SORT_BY = "popularity.asc"
        const val API_SORT_BY = "release_date.asc"
        const val API_READ_ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlOWNjOWViYmEyNWZlMmFhNjI1OGJkNjc1ZDM0NzRiMCIsInN1YiI6IjVjNzU4ODAyMGUwYTI2MGI0YzFhYWM4ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.M473jEjC-53dpwLvmNgC49RYz4v0bXQTJsr-hxWgh2o"

        const val MY_LOG = "myLog"
        val fullDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val shortFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())

    }
}