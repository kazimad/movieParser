package com.kazimad.movieparser.sources.persistance.data_sources

import com.kazimad.movieparser.entities.MovieEntity
import com.kazimad.movieparser.sources.persistance.db_repositories.MovieInterface
import com.kazimad.movieparser.sources.persistance.daos.MovieDao
import javax.inject.Inject


class MovieDbDataSource @Inject
constructor(private val movieDao: MovieDao) : MovieInterface {

    override fun update(movieEntity: MovieEntity) {
        return movieDao.updateMovieData(movieEntity)
    }

    override fun loadOnlyFavorite(): List<MovieEntity> {
        return movieDao.loadOnlyFavorite()
    }

    override fun deleteAll() {
        movieDao.deleteAllMovieData()
    }

    override fun delete(movieEntity: MovieEntity) {
        movieDao.deleteMovieData(movieEntity)
    }

    override fun findAll(): List<MovieEntity> {
        return movieDao.getMovieDatas()
    }

    override fun findById(id: Int): MovieEntity {
        return movieDao.getMovieDataById(id)
    }

    override fun insert(movieEntity: MovieEntity) {
        movieDao.insertMovieData(movieEntity)
    }

    override fun insertAll(movieData: List<MovieEntity>) {
        movieDao.insertAllMovieDatas(*movieData.toTypedArray())
    }
}