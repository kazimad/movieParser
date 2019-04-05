package com.kazimad.movieparser.sources.persistance.db_repositories

import com.kazimad.movieparser.entities.MovieData


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