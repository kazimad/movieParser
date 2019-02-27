package com.kazimad.movieparser.models.response

data class TopResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<MovieData>
)