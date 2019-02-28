package com.kazimad.movieparser.persistance

import androidx.lifecycle.LiveData
import com.kazimad.movieparser.models.response.MovieData


interface DbRepository {

    fun findById(id: Int): LiveData<MovieData>

    fun findAll(): LiveData<List<MovieData>>

    fun insert(movieData: MovieData)

    fun delete(movieData: MovieData)

}