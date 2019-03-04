package com.kazimad.movieparser.interfaces

import com.kazimad.movieparser.enums.ClickVariants
import com.kazimad.movieparser.models.MovieData

interface CustomClickListener {
    fun onCustomClick(variants: ClickVariants, moviewData: MovieData)
}