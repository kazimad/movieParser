package com.kazimad.movieparser.models.error

import java.lang.Exception


class ResponseException(val errorMessage: String?) : Exception()

