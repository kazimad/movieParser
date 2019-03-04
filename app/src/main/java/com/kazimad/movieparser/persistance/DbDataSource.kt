package com.kazimad.movieparser.persistance

import com.kazimad.movieparser.models.MovieData
import javax.inject.Inject


class DbDataSource @Inject
constructor(private val movieDao: MovieDao) : DbRepository {

    override fun update(movieData: MovieData) {
        return movieDao.updateMovieData(movieData)
    }

    override fun loadOnlyFavorite(): List<MovieData> {
        return movieDao.loadOnlyFavorite()
    }

    override fun deleteAll() {
        movieDao.deleteAllMovieData()
    }

    override fun delete(movieData: MovieData) {
        movieDao.deleteMovieData(movieData)
    }

    override fun findAll(): List<MovieData> {
        return movieDao.getMovieDatas()
    }

    override fun findById(id: Int): MovieData {
        return movieDao.getMovieDataById(id)
    }

    override fun insert(movieData: MovieData) {
        movieDao.insertMovieData(movieData)
    }

    override fun insertAll(movieData: List<MovieData>) {
        movieDao.insertAllMovieDatas(*movieData.toTypedArray())
    }
}