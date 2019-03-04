package com.kazimad.movieparser.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteData(
    @PrimaryKey
    var id: Int?
)