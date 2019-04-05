package com.kazimad.movieparser.entities.error


class ResponseException(val errorMessage: String?, val code: Int?) : Exception()

