package com.kazimad.movieparser.persistance.db_repositories

import com.kazimad.movieparser.models.MovieData


interface MovieInterface {

    fun findById(id: Int): MovieData

    fun findAll(): List<MovieData>

    fun insert(movieData: MovieData)

    fun update(movieData: MovieData)

    fun insertAll(movieData: List<MovieData>)

    fun delete(movieData: MovieData)

    fun deleteAll()

    fun loadOnlyFavorite(): List<MovieData>

}