package com.kazimad.movieparser.interfaces

import com.kazimad.movieparser.enums.ClickVariants
import com.kazimad.movieparser.entities.MovieEntity

interface CustomClickListener {
    fun onCustomClick(variants: ClickVariants, movieEntity: MovieEntity)
}