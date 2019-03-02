package com.kazimad.movieparser.persistance

import com.kazimad.movieparser.models.response.MovieData


interface DbRepository {

    fun findById(id: Int): MovieData

    fun findAll(): List<MovieData>

    fun insert(movieData: MovieData)

    fun insertAll(movieData: List<MovieData>)

    fun delete(movieData: MovieData)

    fun deleteAll()


}