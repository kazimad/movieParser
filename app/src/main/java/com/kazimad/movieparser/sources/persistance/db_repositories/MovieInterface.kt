package com.kazimad.movieparser.sources.persistance.db_repositories

import com.kazimad.movieparser.entities.MovieEntity


interface MovieInterface {

    fun findById(id: Int): MovieEntity

    fun findAll(): List<MovieEntity>

    fun insert(movieEntity: MovieEntity)

    fun update(movieEntity: MovieEntity)

    fun insertAll(movieData: List<MovieEntity>)

    fun delete(movieEntity: MovieEntity)

    fun deleteAll()

    fun loadOnlyFavorite(): List<MovieEntity>

}