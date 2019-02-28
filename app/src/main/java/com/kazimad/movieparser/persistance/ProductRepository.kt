package com.kazimad.movieparser.persistance

import com.kazimad.movieparser.models.response.MovieData


interface ProductRepository {

    fun findById(id: Int): MovieData

    fun findAll(): List<MovieData>

    fun insert(product: MovieData)

    fun delete(product: MovieData)

}