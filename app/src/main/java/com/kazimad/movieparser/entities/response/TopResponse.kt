package com.kazimad.movieparser.entities.response

import com.kazimad.movieparser.entities.MovieEntity

data class TopResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<MovieEntity>
)