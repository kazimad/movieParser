package com.kazimad.movieparser

import com.kazimad.movieparser.entities.FavoriteEntity
import com.kazimad.movieparser.entities.MovieEntity
import com.kazimad.movieparser.entities.SectionedMovieItem
import com.kazimad.movieparser.enums.ListTypes

/**
 * Created by kazimad on 4/7/2019.
 */
object DummyData {
    var movieId = 0
    var adult = false
    var backdropPath = "/sdV2CyNro8RnFx0NFbe7OIWp1v0.jpg"
    var generIds = listOf(35, 12, 27)
    var originalLanguage = "en"
    var originalTitle = "Dave Made a Maze"
    var overview = "A frustrated artist gets lost inside the cardboard fort he builds in his living room."
    var popularity = 5.684
    var posterPath = "/7Z5sEOC8Y7bdrg7FmIfNGkNniyX.jpg"
    var releaseDate = "2019-05-16"
    var releaseDate2 = "2019-02-14"
    var releaseDate3 = "2019-03-10"
    var title = "Dave Made a Maze"
    var video = false
    var voteAverage = 0.0
    var voteCount = 82
    var isFavorite = false

    fun createDummyMovieEntity(): MovieEntity {
        return MovieEntity(
            movieId++,
            adult,
            backdropPath,
            generIds,
            originalLanguage,
            originalTitle,
            overview,
            popularity,
            posterPath,
            releaseDate,
            title,
            video,
            voteAverage,
            voteCount,
            isFavorite
        )
    }

    fun createDummyFavoriteEntity(): FavoriteEntity {
        return FavoriteEntity(id = 0)
    }

    fun createDummySectionedMovieItem(): SectionedMovieItem {
        return SectionedMovieItem(ListTypes.REGULAR, createDummyMovieEntity())
    }

    fun createDummySectionedMovieItem(movieEntity: MovieEntity): SectionedMovieItem {
        return SectionedMovieItem(ListTypes.REGULAR, movieEntity)
    }
}