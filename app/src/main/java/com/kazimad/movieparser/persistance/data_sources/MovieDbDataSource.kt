package com.kazimad.movieparser.persistance.data_sources

import com.kazimad.movieparser.entities.MovieData
import com.kazimad.movieparser.persistance.db_repositories.MovieInterface
import com.kazimad.movieparser.persistance.daos.MovieDao
import javax.inject.Inject


class MovieDbDataSource @Inject
constructor(private val movieDao: MovieDao) : MovieInterface {

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