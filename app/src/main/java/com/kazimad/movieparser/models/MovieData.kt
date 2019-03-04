package com.kazimad.movieparser.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.kazimad.movieparser.persistance.DataConverter

@Entity
data class MovieData(
    @PrimaryKey
    val id: Int,

    val adult: Boolean?,
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @TypeConverters(DataConverter::class)
    @ColumnInfo(name = "genre_ids")
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    val originalLanguage: String?,

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    val originalTitle: String?,

    val overview: String?,

    val popularity: Double?,

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String?,

    val title: String?,

    val video: Boolean?,

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val voteAverage: Double?,

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    val voteCount: Int?,

    var isFavorite: Boolean = false
)