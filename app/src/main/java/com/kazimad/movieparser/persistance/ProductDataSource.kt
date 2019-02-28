package com.kazimad.movieparser.persistance

import androidx.lifecycle.LiveData
import com.kazimad.movieparser.models.response.MovieData
import javax.inject.Inject


class ProductDataSource @Inject
constructor(private val productDao: MovieDao) : ProductRepository {
    override fun findAll(): List<MovieData> {
        return productDao.getMovieDatas()
    }

    override fun findById(id: Int): MovieData {
        return productDao.getMovieDataById(id)
    }

    override fun insert(movieData: MovieData) {
        productDao.insertMovieData(movieData)
    }

    override fun delete(movieData: MovieData) {
        productDao.deleteMovieData(movieData)
    }
}