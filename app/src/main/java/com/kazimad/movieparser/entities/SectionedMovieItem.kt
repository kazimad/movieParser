package com.kazimad.movieparser.entities

import com.kazimad.movieparser.enums.ListTypes

data class SectionedMovieItem(
    var type: ListTypes = ListTypes.REGULAR,
    var value: MovieEntity? = null,
    var headerText: String? = null
)