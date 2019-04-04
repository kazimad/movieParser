package com.kazimad.movieparser.persistance.daos

import androidx.room.*
import com.kazimad.movieparser.models.FavoriteData
import com.kazimad.movieparser.models.MovieData


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieData(movieData: MovieData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovieDatas(vararg movieDatas: MovieData)

    @Update
    fun updateMovieData(movieData: MovieData)

    @Delete
    fun deleteMovieData(movieData: MovieData)

    @Query("DELETE FROM MovieData")
    fun deleteAllMovieData()

    @Query("SELECT * FROM MovieData WHERE id ==:id")
    fun getMovieDataById(id: Int): MovieData

    @Query("SELECT * FROM MovieData")
    fun getMovieDatas(): List<MovieData>

    @Query("SELECT * FROM MovieData WHERE isFavorite == 1")
    fun loadOnlyFavorite(): List<MovieData>
}