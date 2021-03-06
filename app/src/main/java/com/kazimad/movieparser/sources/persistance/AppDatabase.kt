package com.kazimad.movieparser.sources.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kazimad.movieparser.entities.FavoriteEntity
import com.kazimad.movieparser.entities.MovieEntity

import com.kazimad.movieparser.sources.persistance.daos.FavoriteDao
import com.kazimad.movieparser.sources.persistance.daos.MovieDao

@Database(entities = [MovieEntity::class, FavoriteEntity::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        val VERSION = "1"
    }

    abstract fun getMovieDao(): MovieDao

    abstract fun getFavoriteDao(): FavoriteDao
}