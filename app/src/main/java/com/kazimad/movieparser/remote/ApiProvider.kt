package com.kazimad.movieparser.remote

import com.kazimad.movieparser.App.Companion.mainComponent


class ApiProvider {
    companion object {
        const val baseUrl = "https://api.themoviedb.org/"
    }


    fun create(url: String): ApiInterface {
        return mainComponent.getApi()
    }
}