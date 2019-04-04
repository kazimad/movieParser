package com.kazimad.movieparser.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kazimad.movieparser.models.FavoriteData
import com.kazimad.movieparser.models.MovieData
import com.kazimad.movieparser.persistance.daos.FavoriteDao
import com.kazimad.movieparser.persistance.daos.MovieDao

@Database(entities = [MovieData::class, FavoriteData::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        val VERSION = "1"
    }

    abstract fun getMovieDao(): MovieDao

    abstract fun getFavoriteDao(): FavoriteDao
}