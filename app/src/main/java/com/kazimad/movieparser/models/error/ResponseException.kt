package com.kazimad.movieparser.models.error


class ResponseException(val errorMessage: String?, val code: Int?) : Exception()

