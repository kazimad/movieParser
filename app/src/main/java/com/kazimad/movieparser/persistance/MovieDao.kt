package com.kazimad.movieparser.persistance

import androidx.room.*
import com.kazimad.movieparser.models.response.MovieData
import androidx.room.FtsOptions.Order
import androidx.room.OnConflictStrategy



@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieData(movieData: MovieData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovieDatas(ListMovieData: List<MovieData>)

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
}