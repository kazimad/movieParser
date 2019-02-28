package com.kazimad.movieparser.persistance

import androidx.lifecycle.LiveData
import com.kazimad.movieparser.models.response.MovieData
import javax.inject.Inject


class DbDataSource @Inject
constructor(private val productDao: MovieDao) : DbRepository {

    override fun findAll(): LiveData<List<MovieData>> {
        return productDao.getMovieDatas()
    }

    override fun findById(id: Int): LiveData<MovieData> {
        return productDao.getMovieDataById(id)
    }

    override fun insert(movieData: MovieData) {
        productDao.insertMovieData(movieData)
    }

    override fun delete(movieData: MovieData) {
        productDao.deleteMovieData(movieData)
    }
}