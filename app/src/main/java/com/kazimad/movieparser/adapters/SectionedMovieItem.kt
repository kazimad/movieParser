package com.kazimad.movieparser.adapters

import com.kazimad.movieparser.enums.ListTypes
import com.kazimad.movieparser.models.MovieData

data class SectionedMovieItem(
    var type: ListTypes = ListTypes.REGULAR,
    var value: MovieData? = null,
    var headerText: String? = null
)