package com.kazimad.movieparser.interfaces

import com.kazimad.movieparser.enums.ClickVariants
import com.kazimad.movieparser.entities.MovieData

interface CustomClickListener {
    fun onCustomClick(variants: ClickVariants, movieData: MovieData)
}