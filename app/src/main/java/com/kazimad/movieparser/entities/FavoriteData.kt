package com.kazimad.movieparser.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteData(
    @PrimaryKey
    val id: Int
)