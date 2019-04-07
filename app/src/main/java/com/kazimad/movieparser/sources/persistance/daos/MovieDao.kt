package com.kazimad.movieparser.sources.persistance.daos

import androidx.room.*
import com.kazimad.movieparser.entities.MovieEntity


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieData(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovieDatas(vararg movieEntities: MovieEntity)

    @Update
    fun updateMovieData(movieEntity: MovieEntity)

    @Delete
    fun deleteMovieData(movieEntity: MovieEntity)

    @Query("DELETE FROM MovieEntity")
    fun deleteAllMovieData()

    @Query("SELECT * FROM MovieEntity WHERE id ==:id")
    fun getMovieDataById(id: Int): MovieEntity

    @Query("SELECT * FROM MovieEntity")
    fun getMovieDatas(): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE isFavorite == 1")
    fun loadOnlyFavorite(): List<MovieEntity>
}