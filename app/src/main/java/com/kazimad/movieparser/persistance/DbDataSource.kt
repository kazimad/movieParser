package com.kazimad.movieparser.persistance

import com.kazimad.movieparser.models.response.MovieData
import javax.inject.Inject


class DbDataSource @Inject
constructor(private val productDao: MovieDao) : DbRepository {


    override fun deleteAll() {
        productDao.deleteAllMovieData()
    }
    override fun delete(movieData: MovieData) {
        productDao.deleteMovieData(movieData)
    }

    override fun findAll(): List<MovieData> {
        return productDao.getMovieDatas()
    }

    override fun findById(id: Int): MovieData {
        return productDao.getMovieDataById(id)
    }

    override fun insert(movieData: MovieData) {
        productDao.insertMovieData(movieData)
    }

    override fun insertAll(movieData: List<MovieData>) {
        productDao.insertAllMovieDatas(movieData)
    }
}